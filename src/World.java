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
    private ArrayList<Robot> robots;
    /**
     * 
     */
    private ArrayList<SimObject>  objects;
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
    private int maxLightRadius = 9;
    private int minLightRadius = 5;

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
        this.objects = new ArrayList<>();
        robots = new ArrayList<>();

        for(int i = 0; i < places.length; i++){
            places[i] = empty;
        }


        for(int r = 0; r < rows/boxHeight; r++) {
            for (int c = 0; c < columns/boxWidth ; c++) {
                generateCell(r, c);
            }
        }

       generateLight();
    }

    private void generateLight() {
        ArrayList<Light> l = new ArrayList<>();
        for(SimObject simObject: objects)
            if(simObject instanceof Light) l.add((Light)simObject);

        for(int i = 0 ; i < rows * columns ; i++){
            light[i] = 0;
        }

        for(Light li : l) {
            int x = li.getPos() % columns;
            int y = (li.getPos() - x) / columns;
            ArrayList<int[]> blocked = new ArrayList<>();


            light[li.getPos()] += li.getRadius();

            for (int i = 1; i < li.getRadius(); i++) {
                for (int dy = -i; dy <= i; dy++) {
                    if (dy == -i || dy == i)
                        for (int dx = -i; dx <= i; dx++) {
                            if((x + dx >= columns || dx+ x < 0) ||(y + dy >= rows || y + dy < 0)) continue;
                            if(places[x + dx + (dy + y) * columns] == 'X') blocked.add(new int[]{x+ dx, y+ dy});
                                if(!Util.blocked(this, blocked, x, y, rows, columns, dx, dy))
                            light[x + dx + (dy + y) * columns] += li.getRadius() - i;
                        }
                    else {
                        for (int dx = -i; dx <= i; dx += i * 2) {
                            if((x + dx >= columns || dx+ x < 0) ||(y + dy >= rows || y + dy < 0)) continue;
                            if(places[x + dx + (dy + y) * columns] == 'X') blocked.add(new int[]{x+ dx, y+ dy});
                            if(!Util.blocked(this, blocked, x, y, rows, columns, dx, dy))
                                light[x + dx + (dy + y) * columns] += li.getRadius() - i;
                        }
                    }
                }
            }
        }
    }

    private void generateCell(int r, int c) {
        int lPosY = ThreadLocalRandom.current().nextInt(0, 9);
        int lPosX = ThreadLocalRandom.current().nextInt(0, 9);

        places[lPosX + c * boxWidth + ((lPosY + r * boxHeight) * columns)] = 'L';
        this.objects.add(new Light(lPosX + c * boxWidth + (lPosY + r *boxHeight) * columns, ThreadLocalRandom.current().nextInt(minLightRadius, maxLightRadius + 1)));

        for(int i = ThreadLocalRandom.current().nextInt(0, fuelPerCell); i < fuelPerCell; i++){
            Food f = null;
            int x;
            int y;
            do{
                x = ThreadLocalRandom .current().nextInt(0, 9);
                y = ThreadLocalRandom.current().nextInt(0, 9);
            } while (places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != empty);
            objects.add(new Food(y * columns + x, 50));
            places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] = objects.get(objects.size()- 1).represent;
        }
        int x;
        int y;
        do {
            x = ThreadLocalRandom.current().nextInt(1, 8);
            y = ThreadLocalRandom.current().nextInt(1, 8);
        } while(places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != empty);
        objects.add(new Wall(y * columns + x));
        places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] = 'X';
        double maxwalls = 50;
        double walls = 1;
        double rnd = ThreadLocalRandom.current().nextDouble(0,1);
        wallLoop:
        while(walls/maxwalls < rnd) {

            ArrayList<Integer> indexes = new ArrayList<>();

            for(int dx = -1;dx < 2; dx += 2 ) {
                if((x + dx >= boxWidth) || (x + dx < 0) || Util.notEmpty(empty, x + dx + c * boxWidth,y + r * boxHeight, columns, places)) continue;
                if((x + dx + c * boxWidth >= columns - 1) || (x + dx + c * boxWidth < 1)) continue;
                int foundwalls = 0;

                for(int wdx = -1; wdx < 2; wdx += 2){
                    if((x + dx + wdx + c * boxWidth >= columns - 1) || (x+ dx + wdx + c * boxWidth < 1)) continue;
                    if(Util.isWall(places, x + dx + wdx + c * boxWidth, y + r * boxHeight, columns)) foundwalls++;
                }
                for(int wdy = -1; wdy < 2; wdy += 2){
                    if((y + wdy + r * boxHeight >= rows - 1)|| (y + wdy + r * boxHeight < 1)) continue;
                    if(Util.isWall(places, x + dx +  c * boxWidth, y + wdy + r * boxHeight, columns)) foundwalls++;
                }
                if(foundwalls < 2) indexes.add((x + dx + c* boxWidth) + (y + r * boxHeight) * columns);
            }
            for(int dy = -1;dy < 2; dy += 2 ) {
                if((y + dy + r * boxHeight>= rows - 1)|| (y + dy + r * boxHeight < 1) || Util.notEmpty(empty, x  + c * boxWidth,y + dy+ r * boxHeight, columns, places)) continue;
                int foundwalls = 0;

                for(int wdx = -1; wdx < 2; wdx += 2){
                    if((wdx + x + c * boxWidth >= columns - 1)|| (x + wdx + c * boxWidth < 1)) continue;
                    if(Util.isWall(places, x + wdx + c * boxWidth, y + dy+ r * boxHeight, columns)) foundwalls++;
                }
                for(int wdy = -1; wdy < 2; wdy += 2){
                    if((y + dy + wdy + r * boxHeight >= rows - 1)|| (y + dy + wdy + r* boxHeight< 1)) continue;
                    if(Util.isWall(places, x+ c * boxWidth , y + dy + wdy + r * boxHeight, columns)) foundwalls++;
                }

                if(foundwalls < 2) indexes.add((x + c* boxWidth) + ((y + dy + r * boxHeight) * columns));
            }
            if(indexes.size() == 0) break wallLoop;

            int rndm = ThreadLocalRandom.current().nextInt(0, indexes.size());

            places[indexes.get(rndm)] = 'X';
            objects.add(new Wall(indexes.get(rndm)));

            x = indexes.get(rndm) % columns - c * boxWidth;
            y = (indexes.get(rndm) - x )/ columns - r * boxHeight;

            walls++;
        }

        do {
            x = ThreadLocalRandom.current().nextInt(0, 9);
            y = ThreadLocalRandom.current().nextInt(0, 9);
        } while(places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != empty);

        places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] = 'R';
        robots.add(new LightRobot((x+ c* boxWidth) + (y + r * boxHeight) * columns));
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

    public int[] getLight() {
        return light;
    }
}

