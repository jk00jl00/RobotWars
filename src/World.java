import World;
import Controller;
import Robot;

public class World {
    /** Attributes */
    /**
     * 
     */

    /**
     * 
     */
    private int size;
    /**
     * 
     */
    private int[] light;
    /**
     * size stor
     */
    private char[] places;
    /**
     * 
     */
    private Robot[] robots;
    /**
     * 
     */
    private Object[] objects;
    /**
     * 
     */
    private int rows;
    /**
     * 
     */
    private int collumns;
    /** Associations */
    private Robot unnamed_1;
    /**
     * Operation tick
     *
     */
    public void tick (  ){}
    /**
     * Operation getBoard
     *
     * @return char[]
     */
    public char[] getBoard (  ){}
    /**
     * Operation 
     *
     * @param collumns - 
     * @param rows - 
     * @return World
     */
    public World  ( int collumns, int rows ){
        this.collumns = collumns;
        this.rows = rows;

        generateWorld();
    }
    /**
     * Operation generateWorld
     *
     */
    private void generateWorld (  ){}
    /**
     * Operation checkFood
     *
     * @return boolean 
     */
    private boolean  checkFood (  ){}
    /**
     * Operation placeFood
     *
     */
    private void placeFood (  ){}
}

