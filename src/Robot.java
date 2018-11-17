import java.util.ArrayList;

public abstract class Robot

{
    /** Attributes */
    /**
     * 
     */
    private int energy;
    /**
     * 
     */
    private int cFood;
    /**
     * 
     */
    private int targetPos;
    /**
     * 
     */
    private int[] path;
    private int pathIndex = 0;
    private int pos;
    /**
     * Operation 
     *
     * @param pos - 
     * @return Robot
     */
    public Robot ( int pos ){
        this.pos = pos;
        this.energy = 50;
        this.cFood = pos;
        this.targetPos = pos;
    }

    /**
     * Operation findFood
     *
     * @param world
     */
    private void findFood(World world){
        if(cFood == this.pos) {
            ArrayList<Food> foods = new ArrayList<>();
            for (SimObject o : world.getSimObjects()) {
                if (o instanceof Food) foods.add((Food) o);
            }

            ArrayList<int[]> paths = new ArrayList<>();

            for (Food f : foods) {
                paths.add(Util.shortestPath(pos, f.getPos(), world.getBoard(), world.getColumns(), world.getRows()));
            }

            for (int i = 0; i < paths.size(); i++) {
                if (paths.get(i).length <= 1 || paths.get(i).length > this.energy)
                    paths.remove(i--);
            }

            if (paths.size() == 0) {
                this.cFood = this.pos;
                return;
            }

            int[] lScore = new int[paths.size()];
            for (int i = 0; i < paths.size(); i++) {
                lScore[i] = Util.lightScore(world.getLight(), paths.get(i));
            }
            int[] highest = paths.get(0);
            for (int i = 1; i < paths.size(); i++) {
                if (lScore[i] > lScore[paths.indexOf(highest)])
                    highest = paths.get(i);
            }
            System.out.println("");
            for(int a: highest){
                System.out.print(" " + a);
            }
            System.out.println("");
            cFood = highest[highest.length - 1];
            pathIndex = 0;
            path = highest;
            targetPos = path[++pathIndex];
        }
    }

    /**
     * Operation move
     *
     * @param world
     */
    private void move(World world){
        if(targetPos == this.pos){
            targetPos = path[pathIndex++];
        }
        world.getBoard()[pos] = ' ';
        world.getBoard()[targetPos] = 'R';
        this.pos = targetPos;
    }

    /**
     * Operation update
     *
     * @param world
     */
    protected void update(World world){
        findFood(world);
        move(world);
    }

    /**
     * Operation idle
     *
     */
    private void idle (  ){

    }

}

