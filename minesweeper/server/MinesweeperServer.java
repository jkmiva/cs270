package minesweeper.server;

import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

import minesweeper.Board;

import java.io.*;

public class MinesweeperServer {

    /** Default server port. */
    private static final int DEFAULT_PORT = 4444;
    /** Maximum port number as defined by ServerSocket. */
    private static final int MAXIMUM_PORT = 65535;
    /** Default square board size. */
    private static final int DEFAULT_SIZE = 10;
    
    // board used by the server
    private final Board board;
    
    // server socket accepting connection
	private final ServerSocket serverSocket;
	
	// debug flag, if set true , server will disconnect a player who dig out a bomb.
	private final boolean debug;
	
	// number of current active players
	private AtomicInteger playerNum;
	
    /**
     * Make a MinesweeperServer that listens for connections on port.
     * @param port port number, requires 0 <= port <= 65535.
     */
    public MinesweeperServer(int port, boolean debug, Board board) throws IOException {
        serverSocket = new ServerSocket(port);
        this.debug = debug;
        this.board = board;
        playerNum = new AtomicInteger(0);
        checkRep();
    }
    
    /*
     * check Rep Invirant
     */
    private void checkRep() {
        assert (this.board.getHeight() > 0 && this.board.getWidth() > 0);
        assert playerNum.get() >= 0;
    }
    /**
     * Run the server, listening for client connections and handling them.  
     * Never returns unless an exception is thrown.
     * @throws IOException if the main server socket is broken
     * (IOExceptions from individual clients do *not* terminate serve()).
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();
            
            this.playerNum.incrementAndGet(); // player++
            
            // handle the client        
            new Thread(new Runnable() { 
                public void run() {
                    try {
                        handleConnection(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                            playerNum.decrementAndGet(); // player--
                            checkRep();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
           
        }
    }
    
    /**
     * Handle a single client connection.  Returns when client disconnects.
     * @param socket  socket where client is connected
     * @throws IOException if connection has an error or terminates unexpectedly
     */
    private void handleConnection(Socket socket) throws IOException {
        
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        /*
         * HELLO msg
         * this msg shoudl be sent to the user exactly as defined and only once, immediately after the 
         * server connects to the user. Again the msg should end with a NEWLINE.
         */
        out.println("Welcome to Minesweeper. Board: "
                    + this.board.getWidth() + " columns by "
                    + this.board.getHeight() + " rows. Players: "
                    + this.playerNum + " including you. Type 'help' for help.");
        
        
        try {
        	for (String line = in.readLine(); line != null; line = in.readLine()) {
        		String output = handleRequest(line);
        		if(output.equals("BOOM!\n")) {
        			out.println(output);
        			if (!debug) {
        			    break;
        			}
        		}
        		else if (output == "quit") {
        		    break;
        		}
        		else { 
        		    out.print(output);
        		}
        		out.flush();
        	}
        } finally {        
       	    out.close();
       	    in.close();
       	    checkRep();
       	    }
    }

	/**
	 * handler for client input
	 * 
	 * make requested mutations on game state if applicable, then return appropriate message to the user
	 * 
	 * @param input
	 * @return
	 */
	private String handleRequest(String input) {

		String regex = "(look)|(help)|(bye)|"
		             + "(dig -?\\d+ -?\\d+)|(flag -?\\d+ -?\\d+)|(deflag -?\\d+ -?\\d+)";
		
		final String help = "Valid commands: help  --get help info\n"
		                  + "look  --show current board\n"
		                  + "bye  --quit\n"
		                  + "dig x y --dig a positoin (x,y)\n"
		                  + "flag x y --flag a position (x,y)\n"
		                  + "deflag x y --defalg a position (x,y)\n";
		// invalid input
		if(!input.matches(regex)) {
			return help;
		}
		String[] tokens = input.split(" ");
		if(tokens[0].equals("look")) {
			// 'look' request
			return this.board.boardMsg();
			
		} else if(tokens[0].equals("help")) {
			// 'help' request
			return help;
			
		} else if(tokens[0].equals("bye")) {
			// 'bye' request
			return "quit";
			
		} else {
			int x = Integer.parseInt(tokens[1]);
			int y = Integer.parseInt(tokens[2]);
			if(tokens[0].equals("dig")) {
				// 'dig x y' request
				return this.board.dig(x, y);
			} else if(tokens[0].equals("flag")) {
				// 'flag x y' request
				return this.board.flag(x, y);
			} else if(tokens[0].equals("deflag")) {
				// 'deflag x y' request
				return this.board.deflag(x, y);
			} else {
			    // should never get here
			    return help;
			}
		}
		
	}
    
    /**
     * Start a MinesweeperServer running on the default port.
     * CommandLine arguments to the server.
        ARGS :== DEBUG SPACE ( SIZE | FILE )?
        DEBUG :== "true" | "false"
        SIZE :== SIZE_FLAG SPACE X
        SIZE_FLAG :== "-s"
        X :== INT
        FILE :== FILE_FLAG SPACE PATH
        FILE_FLAG :== "-f"
        PATH :== .+
        INT :== [0-9]+
        SPACE :== " "
     *
     */
    public static void main(String[] args) {
        try {
            Board board = new Board(DEFAULT_SIZE, DEFAULT_SIZE);
            MinesweeperServer server = new MinesweeperServer(DEFAULT_PORT, false, board);
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}