
public class DarkRobot extends Robot
{
    /**
     * Operation
     *
     * @param pos -
     * @return Robot
     */
    public DarkRobot(int pos) {
        super(pos);
        this.represent = 'D';
    }

    @Override
    protected boolean findFood(World world) {
        if(!super.findFood(world)) return false;

        int[] lScore = new int[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            lScore[i] = Util.lightScore(world.getLight(), paths.get(i));
        }
        int[] lowest = paths.get(0);
        for (int i = 1; i < paths.size(); i++) {
            if (lScore[i] < lScore[paths.indexOf(lowest)])
                lowest = paths.get(i);
        }
        cFood = lowest[lowest.length - 1];
        pathIndex = 0;
        path = lowest;
        targetPos = path[++pathIndex];
        return true;
    }

    @Override
    protected void idle() {

    }
}

