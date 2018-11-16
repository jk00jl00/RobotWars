import java.util.ArrayList;

/**
 * Created by isjo16 on 2018-11-16.
 */
public class Util {
    public static boolean isWall(char[] a, int x, int y, int width){
        return (a[x + y * width] == 'X');
    }

    public static void drawLight(World world, int collumns, int rows) {
        System.out.print(System.lineSeparator());
        System.out.print(System.lineSeparator());
        for(int y = 0; y < rows; y++) {
            for (int x = 0; x < collumns; x++) {
                System.out.print(" " + world.getLight()[x + y * collumns] + " ");
            }
            System.out.print(System.lineSeparator());

        }
        System.out.print(System.lineSeparator());
        System.out.print(System.lineSeparator());

    }

    public static boolean blocked(World world, ArrayList<int[]> blocked, int x, int y, int rows, int cols, int dx, int dy) {
        if(world.getBoard()[x + dx + (y + dy) * cols] == 'X') return true;
        for(int[] a : blocked){
            if(inLine(new int[]{x, y},new int[]{x +dx, y + dy} , a )) return true;
        }

        return false;
    }

    public static boolean notEmpty(char empty, int x, int y, int columns, char[] places) {
        return places[x + y * columns] != empty;
    }

    public static boolean inLine(int[] a, int[] b, int[] c) {
        double dAC = Math.floor(Math.sqrt((a[0] - c[0]) * (a[0] - c[0]) + (a[1] - c[1]) * (a[1] - c[1])));
        double dBC = Math.floor(Math.sqrt((b[0] - c[0]) * (b[0] - c[0]) + (b[1] - c[1]) * (b[1] - c[1])));
        double dAB = Math.floor(Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1])));
        return dAB == dAC + dBC;
    }
}
