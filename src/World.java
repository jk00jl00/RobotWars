import java.sql.DriverPropertyInfo;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class World
{
    /** Attributes */
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

    public static final char empty = ' ';

    /**
     * Used when genearing the world to
     */
    private static int boxHeight = 9;
    /**
     *
     */
    private static int boxWidth = 9;
    public static int getBoxHeight() {
        return boxHeight;
    }

    public static int getBoxWidth() {
        return boxWidth;
    }

    public static int getFuelPerCell() {
        return fuelPerCell;
    }

    public static int getMaxFuelValue() {
        return maxFuelValue;
    }

    public static int getMinFuelValue() {
        return minFuelValue;
    }

    public static int getMaxLightRadius() {
        return maxLightRadius;
    }

    public static double getMaxwalls() {
        return maxwalls;
    }

    public static int getMinLightRadius() {
        return minLightRadius;
    }

    public static void setBoxHeight(int boxHeight) {
        World.boxHeight = boxHeight;
    }

    public static void setBoxWidth(int boxWidth) {
        World.boxWidth = boxWidth;
    }

    public static void setFuelPerCell(int fuelPerCell) {
        World.fuelPerCell = fuelPerCell;
    }

    public static void setMaxFuelValue(int maxFuelValue) {
        World.maxFuelValue = maxFuelValue;
    }

    public static void setMinFuelValue(int minFuelValue) {
        World.minFuelValue = minFuelValue;
    }

    public static void setMaxLightRadius(int maxLightRadius) {
        World.maxLightRadius = maxLightRadius;
    }

    public static void setMaxwalls(double maxwalls) {
        World.maxwalls = maxwalls;
    }

    public static void setMinLightRadius(int minLightRadius) {
        World.minLightRadius = minLightRadius;
    }

    /**
     *
     */
    private static int fuelPerCell =  5;
    private static int maxFuelValue = 20;
    private static int minFuelValue = 20;
    private static int maxLightRadius = 9;
    private static double maxwalls = 150;
    private static int minLightRadius = 5;

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
    public void tick (  ){
        for(int i  = 0; i < robots.size(); i++){
            Robot r = robots.get(i);
            r.update(this);
            if(i == robots.size()) break;
            if(r != robots.get(i))
                i--;
        }
    }
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
                if(c < columns/boxWidth - 1)
                    generateCell(r, c);
                else if(!(r < rows/boxHeight - 1) ^ !(c < columns/boxWidth - 1))
                    generateCell(r, c, (!(r < rows/boxHeight - 1))?boxHeight + rows%boxHeight:boxHeight, (!(c < columns/boxWidth - 1))?boxWidth + columns%boxWidth:boxWidth);
                else
                    generateCell(r, c,boxHeight + rows%boxHeight , boxWidth + columns%boxWidth);

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
                            if(places[x + dx + (dy + y) * columns] == Wall.DEFAULT_REPRESENT) blocked.add(new int[]{x+ dx, y+ dy});
                            if(!Util.blocked(this, blocked, x, y, rows, columns, dx, dy))
                                light[x + dx + (dy + y) * columns] += li.getRadius() - i;
                        }
                    else {
                        for (int dx = -i; dx <= i; dx += i * 2) {
                            if((x + dx >= columns || dx+ x < 0) ||(y + dy >= rows || y + dy < 0)) continue;
                            if(places[x + dx + (dy + y) * columns] == Wall.DEFAULT_REPRESENT) blocked.add(new int[]{x+ dx, y+ dy});
                            if(!Util.blocked(this, blocked, x, y, rows, columns, dx, dy))
                                light[x + dx + (dy + y) * columns] += li.getRadius() - i;
                        }
                    }
                }
            }
        }
    }


    private void generateCell(int r, int c) {generateCell(r, c, boxHeight, boxWidth);}
    private void generateCell(int r, int c, int rLimit, int cLimit) {
        int lPosY = ThreadLocalRandom.current().nextInt(0, rLimit);
        int lPosX = ThreadLocalRandom.current().nextInt(0, cLimit);

        places[lPosX + c * boxWidth + ((lPosY + r * boxHeight) * columns)] = Light.DEFAULT_REPRESENT;
        this.objects.add(new Light(lPosX + c * boxWidth + (lPosY + r *boxHeight) * columns, ThreadLocalRandom.current().nextInt(minLightRadius, maxLightRadius + 1)));

        for(int i = ThreadLocalRandom.current().nextInt(0, fuelPerCell); i < fuelPerCell; i++){
            Food f = null;
            int x;
            int y;
            do{
                x = ThreadLocalRandom .current().nextInt(0, cLimit);
                y = ThreadLocalRandom.current().nextInt(0, rLimit);
            } while (places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != World.empty);
            objects.add(new Food((y + r * boxHeight) * columns + x + c * boxWidth, ThreadLocalRandom.current().nextInt(minFuelValue, maxFuelValue +1)));
            places[objects.get(objects.size()- 1).position] = objects.get(objects.size()- 1).represent;
        }
        int x;
        int y;
        do {
            x = ThreadLocalRandom.current().nextInt(1, cLimit-1);
            y = ThreadLocalRandom.current().nextInt(1, rLimit-1);
        } while(places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != World.empty);
        objects.add(new Wall(y * columns + x));
        places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] = Wall.DEFAULT_REPRESENT;
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

            places[indexes.get(rndm)] = Wall.DEFAULT_REPRESENT;
            objects.add(new Wall(indexes.get(rndm)));

            x = indexes.get(rndm) % columns - c * boxWidth;
            y = (indexes.get(rndm) - x )/ columns - r * boxHeight;

            walls++;
        }

        do {
            x = ThreadLocalRandom.current().nextInt(0, cLimit);
            y = ThreadLocalRandom.current().nextInt(0, rLimit);
        } while(places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != empty);

        int randomInt = ThreadLocalRandom.current().nextInt(0,2);
        Robot toAdd = (randomInt == 0)?
                new LightRobot((x+ c* boxWidth) + (y + r * boxHeight) * columns):
                new DarkRobot((x+ c* boxWidth) + (y + r * boxHeight) * columns);
        places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] = toAdd.represent;
        robots.add(toAdd);

        do {
            x = ThreadLocalRandom.current().nextInt(0, cLimit);
            y = ThreadLocalRandom.current().nextInt(0, rLimit);
        } while(places[(x+ c* boxWidth) + (y + r * boxHeight) * columns] != empty);
        robots.add(new CleanerBot((x+ c* boxWidth) + (y + r * boxHeight) * columns));
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
    private void placeFood (  ){
        Food f = new Food(ThreadLocalRandom.current().nextInt(0, columns*rows -1), 20);
        while (places[f.position] != World.empty){
            f.position = ThreadLocalRandom.current().nextInt(0, columns*rows -1);
        }
        places[f.position] = f.represent;
        objects.add(f);
    }

    public int[] getLight() {
        return light;
    }

    public ArrayList<SimObject> getSimObjects() {
        return objects;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void removeFood(int targetPos) {
        for(int i = 0; i < objects.size(); i++){
            SimObject o = objects.get(i);
            if(o instanceof Food && o.position == targetPos) {
                objects.remove(o);
                placeFood();
                return;
            }
        }
    }

    public ArrayList<Robot> getRobots() {
        return robots;
    }

    public Food getFood(int targetPos) {
        Food f = new Food(-10, 0);
        for(SimObject o : objects){
            if(o instanceof Food && o.position == targetPos) f = (Food)o;
        }
        return f;
    }

    public void removeRobot(int targetPos) {
        for(int i = 0; i < robots.size(); i++){
            Robot r = robots.get(i);
            if(r.pos == targetPos) {
                robots.remove(r);
                return;
            }
        }
    }
}

