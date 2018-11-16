import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class World
{
    /** Attributes */
    /**
     * 
     */
    private int fuelPerCell =  3;
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
    
    private char empty = ' ';
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
    public World ( int collumns, int rows ){
        this.columns = collumns;
        this.rows = rows;
        generateWorld();
    }
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
    public char[] getBoard (  ){ return places;}
    /**
     * Operation generateWorld
     *
     */
    private void generateWorld ( ){
        size = columns * rows;
        this.places = new char[size];
        this.light = new int[size];

        for(int i = 0; i < places.length; i++){
            places[i] = empty;
        }


        for(int r = 0; r < rows/boxHeight; r++) {
            for (int c = 0; c < columns/boxWidth ; c++) {
                generateCell(r, c);
            }
        }
    }

    private void generateCell(int r, int c) {
        int lPosY = ThreadLocalRandom.current().nextInt(0, 9);
        int lPosX = ThreadLocalRandom.current().nextInt(0, 9);

        lPosX = 8;
        lPosY = 8;

        places[lPosX + c * boxWidth + ((lPosY + r * boxHeight) * columns)] = 'L';

        for(int i = ThreadLocalRandom.current().nextInt(0, fuelPerCell); i < fuelPerCell; i++){
            Food f = null;
            int x;
            int y;
            do{
                x = ThreadLocalRandom .current().nextInt(0, 9);
                y = ThreadLocalRandom.current().nextInt(0, 9);
            } while (places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != empty);
            places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] = new Food(x, y).represent;
        }
        int x;
        int y;
        do {
            x = ThreadLocalRandom.current().nextInt(0, 9);
            y = ThreadLocalRandom.current().nextInt(0, 9);
        } while(places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != empty);

        places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] = 'X';
        double maxwalls = 50;
        double walls = 1;
        double rnd = ThreadLocalRandom.current().nextDouble(0,1);
        wallLoop:
        while(walls/maxwalls < rnd) {

            ArrayList<Integer> indexes = new ArrayList<>();

            for(int dx = -1;dx < 2; dx += 2 ) {
                if((x + dx >= boxWidth) || (x + dx < 0)) continue;
                int foundwalls = 0;

                for(int wdx = -1; wdx < 2; wdx += 2){
                    if((x + dx + wdx >= boxWidth) || (x+ dx + wdx < 0)) continue;
                    if(places[(x + wdx + dx + (c * boxWidth)) + ((y + r) * columns)] == 'X') foundwalls++;
                }
                for(int wdy = -1; wdy < 2; wdy += 2){
                    if((y + wdy >= boxHeight)|| (y + wdy < 0)) continue;
                    if(places[(x + dx + c* boxWidth) + (y + wdy + c * boxHeight) * columns] == 'X') foundwalls++;
                }

                if(foundwalls < 2) indexes.add((x + dx + c* boxWidth) + (y + r * boxHeight) * columns);
            }
            for(int dy = -1;dy < 2; dy += 2 ) {
                if((y + dy >= boxHeight)|| (y + dy < 0)) continue;
                int foundwalls = 0;

                for(int wdx = -1; wdx < 2; wdx += 2){
                    if((wdx + x >= boxHeight)|| (x + wdx < 0)) continue;
                    if(places[(x + wdx + c* boxWidth) + ((y + dy + r * boxHeight) * columns)] == 'X') foundwalls++;
                }
                for(int wdy = -1; wdy < 2; wdy += 2){
                    if((y + dy + wdy >= boxHeight)|| (y + dy + wdy< 0)) continue;
                    if(places[(x  + c* boxWidth) + ((y + wdy +dy + r * boxHeight) * columns)] == 'X') foundwalls++;
                }

                if(foundwalls < 2) indexes.add((x + c* boxWidth) + ((y + dy + r * boxHeight) * columns));
            }
            if(indexes.size() == 0) break wallLoop;

            int rndm = ThreadLocalRandom.current().nextInt(0, indexes.size());

            places[indexes.get(rndm)] = 'X';

            x = indexes.get(rndm) % columns - c * boxWidth;
            y = (indexes.get(rndm) - x )/ columns - r * boxHeight;

            walls++;
        }

        do {
            x = ThreadLocalRandom.current().nextInt(0, 9);
            y = ThreadLocalRandom.current().nextInt(0, 9);
        } while(places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != empty);

        places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] = 'R';
    }

    /**
     * Operation checkFood
     *
     * @return boolean 
     */
    private boolean  checkFood (  ){return false;}
    /**
     * Operation placeFood
     *
     */
    private void placeFood (  ){}
}

