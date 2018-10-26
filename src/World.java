import World;
import Controller;
import Robot;

import java.util.concurrent.ThreadLocalRandom;

public class World
{
    /** Attributes */
    /**
     * 
     */
    public  ;
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
    private int columns;
    /**
     * Used when genearing the world to
     */
    private int boxHeight = 9;
    /**
     *
     */
    private int boxWidth = 9;
    /** Associations */
    private Robot unnamed_1;
    /**
     * Operation
     *
     * @param collumns -
     * @param rows -
     * @return World
     */
    public World  ( int collumns, int rows ){}
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
     * Operation generateWorld
     *
     */
    private void generateWorld (  ){
        this.places = new char[size];
        this.light = new int[size];


        for(int r = 0; r < rows/boxHeight; r++) {
            for (int c = 0; c < columns/boxWidth; c++) {
                generateCell(r, c);
            }
        }
    }

    private void generateCell(int r, int c) {
        int walls = ThreadLocalRandom.current().nextInt(0,11);
        int lights = ThreadLocalRandom.current().nextInt(1,2);
        int food = ThreadLocalRandom.current().nextInt(1,2);
        int robots = ThreadLocalRandom.current().nextInt(0,1);

        do {
            for (int y = 0; y < boxHeight; y++) {
                for (int x = 0; x < boxWidth; x++) {
                    int rnd = ThreadLocalRandom.current().nextInt(0, 101);




                }
            }
        }while ((walls + lights + food + robots ) > 0);
    }

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

