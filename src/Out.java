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
    public void output ( char[] p ){}
}

