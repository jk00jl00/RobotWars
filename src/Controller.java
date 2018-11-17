import java.util.InputMismatchException;
import java.util.Scanner;
/*Controller class*/
public class Controller

{
    /**
     * Keeps tab on if the simulation is running
     */
    //private boolean running = false;
    /**
     * Keeps track on the amount of collumns in the world
     */
    private int collumns;
    /**
     * Keeps track on the amount of rows in the world
     */
    private int rows;
    /**
     *  The out objekt that handles the output of the simulation.
     */
    private Out out;
    /**
     * The objekt that contains all thie informatino on the world and
     * updates itself.
     */
    private World world;
    /**
     * The amount of ticks to be done before the next step of the simulation.
     */
    private int fps = 1;
    /**
     * The thread running the simulation
     */
   // private Thread thread;
    /**
     * Constructor
     *
     * @param collumns - The amount of columns the simulations runds with
     * @param rows - The amount of rows the simulation runs with.
     */
    public Controller  ( int collumns, int rows ){
        this.collumns = collumns;
        this.rows = rows;
        this.out = new Out(collumns, rows);
        this.world = new World(collumns, rows);
    }
    /**
     * Method start
     *  Creates a new Thread and starts the simulation loop.
     */
    public synchronized void start (  ){
       /* if(!running){
            running = true;
            thread = new Thread(this);
            thread.start();
        }*/
       run();
    }
    /**
     * Method stop
     * Closes the simulation loop and joins the simulation thread with the main thread.
     */
    /*public void stop (  ){
        if(running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/
    /**
     * Method run
     * Contains the simulationloop that runs the simulation.
     */
    public void run (  ){
        while(true){
            int ticks =  0;
            while(ticks < fps){
                update();
                ticks++;
            }
            draw();
            System.out.print(System.lineSeparator());
            for(Robot r: world.getRobots()){
                System.out.print(r.toString() + " at x: " + r.getPos()%collumns+ ", y:" + r.getPos()/collumns
                        + ", with energy: " + r.getEnergy() + ", Path : ");
                System.out.print("[");
                for(int i = 0; i < r.getPath().length; i++){
                    System.out.print("X: " + r.getPath()[i]%collumns + "| Y: " + r.getPath()[i]/collumns + ", ");
                }
                System.out.println("]");
            }
            //Util.drawLight(world, collumns, rows);
            fps = 0;
            do{
               try{
                   fps = calcTicks();
               } catch (NumberFormatException e) {
                   System.out.println("Please enter a positive integer");
                   fps = 0;
               }
            }while(fps == 0);
        }
    }
    /**
     * Method update
     * Calls the world tick function in order to update the world.
     */
    private void update (  ){
        world.tick();
    }
    /**
     * Method draw
     * Gives the Out objekt the simulation world and tells it to print.
     */
    private void draw (  ){
        out.output(world.getBoard());
    }
    /**
     * Operation calcTicks
     * Gets the amount of ticks the simulation will run for before the next request.
     * @return int The amount of ticks to run the simulation for.
     */
    private int calcTicks () throws NumberFormatException{
        System.out.println("");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ticks: ");
        String s = sc.nextLine();
        if(s.length() == 0) return 1;
        int i = Integer.parseInt(s);
        sc.reset();
        if(i <= 0) i = 1;
        return i;
    }

    public static void main(String[] args) {
        new Controller(20, 9).start();
    }
}

