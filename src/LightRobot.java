
public class LightRobot extends Robot
{
    /**
     * Operation
     *
     * @param pos -
     * @return Robot
     */
    public LightRobot(int pos) {
        super(pos);
        this.represent = 'R';
    }

    @Override
    protected boolean findFood(World world) {
        if(!super.findFood(world)) return false;

        int[] lScore = new int[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            lScore[i] = Util.lightScore(world.getLight(), paths.get(i));
        }
        int[] highest = paths.get(0);
        for (int i = 1; i < paths.size(); i++) {
            if (lScore[i] > lScore[paths.indexOf(highest)])
                highest = paths.get(i);
        }
        cFood = highest[highest.length - 1];
        pathIndex = 0;
        path = highest;
        targetPos = path[++pathIndex];
        return true;
    }

    @Override
    protected void idle() {

    }
}

