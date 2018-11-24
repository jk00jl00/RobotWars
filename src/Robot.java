import java.util.ArrayList;

public abstract class Robot

{
    /** Attributes */
    /**
     *
     */
    protected char represent;
    protected int energy;
    /**
     * 
     */
    protected int cFood;
    /**
     * 
     */
    protected int targetPos;
    /**
     * 
     */
    protected int[] path;
    protected ArrayList<int[]> paths;
    protected int pathIndex = 0;
    protected int pos;
    /**
     * Operation 
     *
     * @param pos - 
     * @return Robot
     */
    public Robot ( int pos ){
        this.pos = pos;
        this.energy = 20;
        this.cFood = pos;
        this.targetPos = pos;
    }

    /**
     * Operation findFood
     *
     * @param world
     */
    protected boolean findFood(World world){
        if(cFood == this.pos || world.getBoard()[cFood] != Food.DEFAULT_REPRESENT || path.length == 0 || path == null) {
            ArrayList<Food> foods = new ArrayList<>();
            for (SimObject o : world.getSimObjects()) {
                if (o instanceof Food) foods.add((Food) o);
            }

            paths = new ArrayList<>();

            for (Food f : foods) {
                paths.add(Util.shortestPath(pos, f.getPos(), world.getBoard(), world.getColumns(), world.getRows(), energy));
            }

            for (int i = 0; i < paths.size(); i++) {
                if ((paths.get(i).length <= 1 )|| (paths.get(i).length > this.energy )|| (world.getBoard()[paths.get(i)[paths.get(i).length - 1]] != Food.DEFAULT_REPRESENT))
                    paths.remove(i--);
            }

            if (paths.size() == 0) {
                this.cFood = this.pos;
                this.path = new int[0];
                return false;
            }
            return true;
        }
        else
            return false;
    }

    /**
     * Operation move
     *
     * @param world
     */
    protected void move(World world){
        if(path == null)  return;

        if(targetPos == this.pos){
            if(pathIndex >= path.length) return;
            targetPos = path[pathIndex++];
        }
        world.getBoard()[pos] = World.empty;
        if(world.getBoard()[targetPos] == Food.DEFAULT_REPRESENT){
            this.energy += world.getFood(targetPos).getValue();
            world.removeFood(targetPos);
        }
        world.getBoard()[targetPos] = this.represent;
        this.pos = targetPos;
    }

    /**
     * Operation update
     *
     * @param world
     */
    protected void update(World world){
        if(energy == 0) return;
        findFood(world);
        move(world);
        energy--;
    }

    /**
     * Operation idle
     *
     */
    protected abstract void idle ();

    public int getPos() {
        return pos;
    }

    public int getEnergy() {
        return energy;
    }

    public int[] getPath() {
        return path;
    }
}

