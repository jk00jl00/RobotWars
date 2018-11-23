import java.io.IOException;
import java.io.InterruptedIOException;

public class Out
{
    /**
     * The rows to take into account when printing.
     */
    private int rows;
    /**
     * The columns to take into account when printing.
     */
    private int columns;
    private double step;
    private double current;
    private double max;

    /**
     * Operation
     *
     * @param collumns - The columns.
     * @param rows -  The rows
     * @return Out
     */
    public Out  ( int collumns, int rows ){
        this.columns = collumns;
        this.rows = rows;
    }
    /**
     * Operation output
     *
     * @param p -
     */
    public void output ( char[] p ){
        System.out.print(System.lineSeparator());
        for(int y = 0; y < rows; y++) {
            System.out.print(" X ");
            if (y == 0) {
                for (int i = 0; i < columns; i++) {
                    System.out.print(" X ");
                }
                System.out.print(" X ");
                System.out.print(System.lineSeparator());
                System.out.print(" X ");
            }
            for (int x = 0; x < columns; x++) {
                System.out.print(" " + p[x + y * columns] + " ");
            }
            System.out.print(" X ");
            System.out.print(System.lineSeparator());
            if (y == rows - 1) {
                System.out.print(" X ");

                for (int i = 0; i < columns; i++) {
                    System.out.print(" X ");
                }
                System.out.print(" X ");

            }
        }
    }

    public void setUpLoadingbar(int upLoadingbar) {
        this.step =  100d /(double)upLoadingbar;
        this.max = (double) 100;
        this.current = 0;
    }

    public void updateLoadingBar() {
        this.current += step;
    }

    public void printLoadingBar() {
        StringBuilder sb = new StringBuilder();
        sb.append("\r");
        sb.append("[");
        for(double i = 0; i <= current; i++){
            sb.append("|");
        }
        for(double i = current; i <  this.max;i++){
            sb.append(" ");
        }
        double toDisplay = current;
        toDisplay = Math.round(toDisplay * 10)/10d;
        sb.append("]");
        sb.append(" - ").append(toDisplay).append("%");
        System.out.print(sb.toString());
    }
    public static void clrscr(){
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
}

