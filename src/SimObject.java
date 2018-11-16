
public abstract class SimObject

{
    /**
     * The position of the simulation object
     */
    protected int position;
    /**
     * The character used to represent the object.
     */
    protected char represent;
    /**
     * Operation
     *
     * @param pos - the position the object will start in
     * @return Object
     */
    public SimObject(int pos ){
        this.position = pos;
    }

    /**
     * Operation isSolid
     *
     * @return boolean
     */
    public abstract boolean isSolid ();



}

