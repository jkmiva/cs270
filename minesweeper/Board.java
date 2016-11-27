package minesweeper;

import minesweeper.Cell.State;

public class Board {

    private final Cell[][] board;
    private final int height;
    private final int width;

    /*
     * Abstraction Function: Represents a Minesweeper board, a 2D array of
     * Cell @board.
     * 
     * @height is the row number
     * 
     * @width is the column number
     * 
     * Rep Invariant: board != null height > 0 width > 0
     * 
     * Rep Exposure: all fields are private and final
     * 
     * Thread Safety:
     */

    /*
     * check Rep Invariant:
     */
    private void checkRep() {
        assert (this.height > 0 && this.width > 0 && this.board != null);
    }

    /*
     * Constructor given height and width, build a board, each cell is UNTOUCHED
     * and may has a bomb.
     * 
     * @x number of columns
     * 
     * @y number of rows
     */
    public Board(int x, int y) {

        this.height = y;
        this.width = x;
        this.board = new Cell[this.width][this.height];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.board[i][j] = new Cell();
            }
        }

        checkRep();
    }

    /*
     * Observe height of the board
     */
    public synchronized int getHeight() {
        return this.height;
    }
    
    /*
     * Observe width of the board
     */
    public synchronized int getWidth() {
        return this.width;
    }

    /*
     * Observe state of a cell specified by position index x and y
     */
    public synchronized State getState(int x, int y) {
        assert validCoordinate(x, y);
        return this.board[x][y].getState();
    }

    /*
     * Observe whether cell(x,y) has bomb or not 
     */
    public synchronized boolean hasBomb(int x, int y) {
        assert validCoordinate(x, y);
        return this.board[x][y].hasBomb();
    }
    
    /*
     * Mutator! 
     * The dig message has the following properties: 
     * If either x or y is less than 0, or either x or y is equal to or greater than the board
     *      size, or square x,y is not in the untouched state, do nothing and return a BOARD message. 
     * If square x,y’s state is untouched, change square x,y’s state to dug.
     * If square x,y contains a bomb, change it so that it contains no bomb and send a BOOM message to the user. 
     * If the debug flag is missing (see Question 4), terminate the user’s connection. 
     *      Note: When modifying a square from containing a bomb to no
     *      longer containing a bomb, make sure that subsequent BOARD messages show
     *      updated bomb counts in the adjacent squares. After removing the bomb
     *      continue to the next step. 
     * If the square x,y has no neighbor squares with bombs, then for each of x,y’s untouched neighbor squares, change said
     *      square to dug and repeat this step (not the entire DIG procedure)
     *      recursively for said neighbor square unless said neighbor square was
     *      already dug before said change. 
     * For any DIG message where a BOOM message
     *      is not returned, return a BOARD message.
     */
    public synchronized String dig(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            checkRep();
            return this.boardMsg();
        }
        else if (this.getState(x, y) != State.UNTOUCHED) {
            checkRep();
            return this.boardMsg();
        }
        else {
            boolean hasBomb = this.hasBomb(x, y);
            recursiveDig(x, y);
            
            if (hasBomb) {
                /*
                 * if dig out a bomb, return BOOM msg
                 * without debug msg, server terminate the user client
                 * 
                 */
                checkRep();
                return "BOOM!\n";
            }
            else {
                checkRep();
                return this.boardMsg();
            }
        }
    }
    
    /*
     * Mutator
     * if cell (x,y) has no neighbors with bombs, then for each of x,y’s untouched neighbor squares,
     *  change said square to dug and repeat this step recursively for said neighbor cell unless
     *  said neighbor cell was already dug before said change.
     */
    private void recursiveDig(int x, int y) {
        if (this.getState(x, y) != State.UNTOUCHED) {
            return; //return if already touched
        }
        else {
         // change (x,y) to DUG, if it has bomb, clear it and update total bomb count.
            this.board[x][y] = new Cell(State.DUG, false);
            if (bombNumOfNeighbor(x, y) == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue; // skip cell(x,y)
                        }
                        else if (x + i >= 0 && x + i < this.width
                                && y + j >= 0 && y + j < this.height) {
                            if (this.getState(x+i, y+j) == State.UNTOUCHED) {
                                recursiveDig(x+i, y+j);
                            }
                        }
                    }
                }
            } // if there is no bomb around (x,y) ,return
        }
        checkRep();
    }
    
    /*
     * Observe the bomb numbers of all the (x,y)'s neighbors
     * @return count :represents the number of bombs of all the neighbors, 0 <= count < 9
     */
    private int bombNumOfNeighbor(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <=1; j++) {
                if (i == 0 && j == 0) {
                    continue; // skip cell(x,y)
                }
                else if (x + i >= 0 && x + i < this.width
                         && y + j >= 0 && y + j < this.height) { // check validation of coordinates
                    count += this.hasBomb(x+i, y+j) ? 1 : 0;
                }
            }
        }
        assert (count >= 0 && count < 9);
        checkRep();
        return count;
    }
    
    /*
     * Mutator
     * -If x and y are both greater than or equal to 0, and less than the board size, 
     *  and square x,y is in the untouched state, change it to be in the flagged state.
     * -Otherwise, do not mutate any state on the server.
     * -For any FLAG message, return a BOARD message.
     */
    public synchronized String flag(int x, int y) {
        if (validCoordinate(x, y) && this.getState(x, y) == State.UNTOUCHED) {
            boolean hasBomb = this.hasBomb(x, y);
            this.board[x][y] = new Cell(State.FLAGGED, hasBomb);
        }
        checkRep();
        return this.boardMsg();
    }
    
    /*
     * Mutator
     * -If x and y are both greater than or equal to 0, and less than the board size, 
     *  and square x,y is in the flagged state, change it to be in the untouched state.
     * -Otherwise, do not mutate any state on the server.
     * -For any DEFLAG message, return a BOARD message.
     */
    public synchronized String deflag(int x, int y) {
        if (validCoordinate(x, y) && this.getState(x, y) == State.FLAGGED) {
            boolean hasBomb = this.hasBomb(x, y);
            this.board[x][y] = new Cell(State.UNTOUCHED, hasBomb);
        }
        checkRep();
        return this.boardMsg();
    }
    
    /*
     * helper function to check if given (x,y) is valid coordinate
     */
    private boolean validCoordinate(int x, int y) {
        return (x >= 0 && x < this.width && y >= 0 && y < this.height);
    }
    
    public synchronized String boardMsg() {
        StringBuffer board = new StringBuffer();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.getState(i, j) == State.UNTOUCHED) {
                    board.append("- ");
                }
                else if (this.getState(i, j) == State.FLAGGED) {
                    board.append("F ");
                }
                else if (this.getState(i, j) == State.DUG && bombNumOfNeighbor(i, j) == 0) {
                    board.append("  ");
                }
                else {
                    board.append(bombNumOfNeighbor(i, j) + " ");
                }
            }
            board.deleteCharAt(board.length() - 1);
            board.append("\n");
        }
        checkRep();
        return board.toString();
    }
    

}
