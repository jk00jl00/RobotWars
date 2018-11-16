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
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++){
                System.out.print(" " + p[x + y*columns] + " ");
            }
            System.out.print(System.lineSeparator());
        }
    }
}

