import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;

import java.util.Scanner;
/*Controller class*/
public class Controller

{
    /**
     * Keeps tab on if the simulation is running
     */
    //private boolean running = false;
    private boolean running;
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
    private int framesToRun = 1;
    int fps = 0;
    /**
     * The thread checking for loop breaks;
     */
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
       if(!running){
           running = true;
           run();
       }
    }
    /**
     * Method stop
     * Closes the simulation loop and joins the simulation thread with the main thread.
     */
    public void stop (  ){
        if(running) {
            running = false;
        }
    }
    /**
     * Method run
     * Contains the simulationloop that runs the simulation.
     */
    public void run (  ){
        draw();
        while(running){
            int ticks =  0;
            out.setUpLoadingbar(framesToRun);
            while(ticks < framesToRun){
                update();
                ticks++;
                if(fps != 0){
                    draw();
                    try {
                        Thread.sleep(1000/fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    out.updateLoadingBar();
                    out.printLoadingBar();
                }
            }
            draw();
            System.out.print(System.lineSeparator());
            System.out.println("Ticks run = " + ticks);
            System.out.println("Robots Alive = " + world.getRobots().size());
            System.out.print(System.lineSeparator());

            /*
            for(Robot r: world.getRobots()){
                System.out.print(r.toString() + " at x: " + r.getPos()%collumns+ ", y:" + r.getPos()/collumns
                        + ", with energy: " + r.getEnergy() + ", Path : ");
                System.out.print("[");
                for(int i = 0; i < r.getPath().length; i++){
                    System.out.print("X: " + r.getPath()[i]%collumns + "| Y: " + r.getPath()[i]/collumns + ", ");
                }
                System.out.println("]");
            }*/
            //Util.drawLight(world, collumns, rows);
            framesToRun = 0;
            do{
               try{
                   framesToRun = calcTicks();
               } catch (NumberFormatException e) {
                   System.out.println("Please enter a positive integer");
                   framesToRun = 0;
               }
            }while(framesToRun == 0);
            if(fps < 0){
                switch (fps){
                    case -1:
                        System.exit(0);
                    case -2:
                        running = false;
                        break;
                }
            }
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
        Out.clrscr();
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
        int i = 0;
        if(s.matches("[0-9]+/[0-9]+")){
            String ticks = "";
            String framerate = "";
            int a = 0;
            while(!s.substring(a, a+ 1).contains("/")){
                ticks += s.substring(a,a+1);
                a++;
            }
            a++;
            while(a < s.length()){
                framerate += s.substring(a, a+1);
                a++;
            }
            s = ticks;
            fps = Integer.parseInt(framerate);
        }
        else if(s.matches("/[0-9]{1}")){
            fps = -(Integer.parseInt(s.substring(1, s.length())));
            return 1;
        }
        else{
            fps = 0;
        }
        i = Integer.parseInt(s);
        sc.reset();
        if(i <= 0) i = 1;
        return i;
    }
}

