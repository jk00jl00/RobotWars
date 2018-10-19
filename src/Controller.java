import Out;
import World;
import Controller;

public class Controller

{
    /** Attributes */
    /**
     * 
     */
    private Out out;
    /**
     * 
     */
    private World world;
    /**
     * 
     */
    private int fps = 1;
    /**
     * 
     */
    private Thread thread;
    /**
     * Operation 
     *
     * @param collumns - 
     * @param rows - 
     * @return Controller
     */
    public Controller  ( int collumns, int rows ){}
    /**
     * Operation start
     *
     */
    public void start (  ){}
    /**
     * Operation stop
     *
     */
    public void stop (  ){}
    /**
     * Operation run
     *
     */
    private void run (  ){}
    /**
     * Operation update
     *
     */
    private void update (  ){}
    /**
     * Operation draw
     *
     */
    private void draw (  ){}
    /**
     * Operation calcTicks
     *
     * @param in - 
     * @return int
     */
    private int calcTicks ( int in ){}
}

