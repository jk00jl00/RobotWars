import java.util.ArrayList;

/**
 * Created by isjo16 on 2018-11-23.
 */
public class CleanerBot extends Robot{
    @Override
    protected void idle() {

    }
    public CleanerBot(int pos) {
        super(pos);
        this.represent = 'C';
        this.energy = 2000;
    }

    @Override
    protected boolean findFood(World world) {
        if(cFood == this.pos || world.getBoard()[cFood] != Food.DEFAULT_REPRESENT || path.length == 0 || path == null) {
            ArrayList<Robot> foods = new ArrayList<>();
            for (Robot r : world.getRobots()) {
                if(r.energy == 0 || energy == 0)
                    foods.add(r);
            }

            paths = new ArrayList<>();

            for (Robot r : foods) {
                paths.add(Util.shortestPath(pos, r.getPos(), world.getBoard(), world.getColumns(), world.getRows(), 2000));
            }

            for (int i = 0; i < paths.size(); i++) {
                if ((paths.get(i).length <= 1 ))
                    paths.remove(i--);
            }

            if (paths.size() == 0) {
                this.cFood = this.pos;
                this.path = new int[0];
                return false;
            }

            int[] lowest = paths.get(0);
            for (int i = 0; i < paths.size(); i++) {
                if(paths.get(i).length < lowest.length) lowest = paths.get(i);
            }

            cFood = lowest[lowest.length - 1];
            pathIndex = 0;
            path = lowest;
            targetPos = path[++pathIndex];
            return true;
        }

        return false;
    }

    @Override
    protected void update(World world) {
        findFood(world);
        move(world);
        if(energy > 0) energy--;
    }

    @Override
    protected void move(World world) {
        if(path == null)  return;

        if(targetPos == this.pos){
            if(pathIndex >= path.length) return;
            targetPos = path[pathIndex++];
        }
        world.getBoard()[pos] = World.empty;
        if(world.getBoard()[targetPos] == 'R' || world.getBoard()[targetPos] == 'D'){
            world.removeRobot(targetPos);
            energy += 1000;
        }
        world.getBoard()[targetPos] = this.represent;
        this.pos = targetPos;
    }
}
