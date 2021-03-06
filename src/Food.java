public class Food extends SimObject
{
    /**
    /**
     * 
     */
    private int value;

    /**
     * Operation
     * @param value - the food value the food will give when picked up.
     * @param pos - the position the object will start .
     * @return Object
     */
    public static final char DEFAULT_REPRESENT = 'B';

    public Food(int pos, int value) {
        super(pos);
        this.value = value;
        this.represent = 'B';
    }

    public Food(int pos) {
        this(pos, 20);
    }

    public int getValue(){
        return value;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}

