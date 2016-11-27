package minesweeper;

import java.util.Random;

public class Cell {
    
    public static enum State {
        UNTOUCHED, FLAGGED, DUG
    }
    private final boolean hasBomb;
    private final State state;
    private final static double BOMB_PROBABILITY = 0.25;
    
    /*
     * Abstraction function:
     * Representing a single cell in a board
     * @hasBomb :whether this cell has a bomb or not
     * @state :the state of this cell
     * 
     * Rep Invariant:
     * state must be one element in {UNTOUCHED, FLAGGED, DUG}
     * 
     * Rep Exposure:
     * all fields are private , final and immutable
     * 
     * Thread Safety:
     * This is an immutable class, it is thread safe
     * 
     */
    
    /*
     * check Rep Invariant
     */
    private void checkRep() {
        assert  (   this.state == State.DUG
                 || this.state == State.FLAGGED
                 || this.state == State.UNTOUCHED );
    }
    
    /*
     * Constructor
     * the probability for a cell to have a bomb is 0.25
     */
    public Cell() {
        // random generator
        Random random = new Random();
        
        // set if this cell has a bomb
        if(random.nextDouble() <= BOMB_PROBABILITY) {
            this.hasBomb = true;
        }
        else {
            this.hasBomb = false;
        }
        
        // the initial state of this cell is UNTOUCHED
        this.state = State.UNTOUCHED;
        
        // check Rep Invariant
        checkRep();
    }
    
    /*
     * Constructor with state and bomb info
     * @param s specified state for the cell
     * @param b bomb information for the cell
     */
    public Cell(State s, boolean b) {
        this.state = s;
        this.hasBomb = b;
        checkRep();
    }
    
    /*
     * observe if this cell has a bomb
     * return true if it has a bomb, flase otherwise
     */
    public boolean hasBomb() {
        return this.hasBomb;
    }
    
    /*
     * observe the state of this cell
     * return the state of the cell
     */
    public State getState() {
        return this.state;
    }
    
    @Override
    public String toString() {
        switch (this.state) {
        case UNTOUCHED:
            return "-";
        case FLAGGED:
            return "F";
        case DUG:
            return " ";
        default:
            return "unexpected error";
                
        }
    }
}
