public class Light extends SimObject
{
    /**
     * The raidus of light given by the light.
     */
    private int radius;

    /**
     * Operation
     * @param radius - the radius of light the light will provide.
     * @param pos - the position the object will start in
     * @return Object
     */
    public Light(int pos, int radius) {
        super(pos);
        this.radius = radius;
        this.represent = 'L';
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    public int getRadius() {
        return radius;
    }
}

