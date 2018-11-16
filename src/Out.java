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
}

