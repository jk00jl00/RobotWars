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
        double dAC = Math.round(Math.sqrt((a[0] - c[0]) * (a[0] - c[0]) + (a[1] - c[1]) * (a[1] - c[1])));
        double dBC = Math.round(Math.sqrt((b[0] - c[0]) * (b[0] - c[0]) + (b[1] - c[1]) * (b[1] - c[1])));
        double dAB = Math.round(Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1])));
        return dAB == dAC + dBC;
    }

    private static int[] queueNodesToArray(QueueNode p, int columns) {
        ArrayList<Integer> i = new ArrayList<>();
        while (p.hasLast()){
            QueueNode qn = p.getLast();
            if(qn == null)break;
            i.add(qn.x + qn.y *columns);
        }
        i.add(p.x + p.y *columns);
        int[] ints = new int[i.size()];
        for(int a = 0; a < i.size(); a++)
            ints[a] = i.get(a);
        return ints;
    }

    public static int[] shortestPath(int sc, int tc, char[] board,int collums, int rows){
        boolean[] visited = new boolean[board.length];

        int sx = sc%collums;
        int sy = (sc - sx)/collums;
        int tx = tc%collums;
        int ty = (tc - tx)/collums;

        visited[sc] = true;
        ArrayList<Layer> layers = new ArrayList<>();
        layers.add(new Layer(0, new QueueNode[]{new QueueNode(sx, sy, 0, null)}));
        for(int i = 0; i < layers.size(); i++) {
            Layer newLayer = checkLayer(layers.get(i), tx , ty, board, collums, rows, visited);
            if(newLayer.queueNodes.length > 0)
                layers.add(newLayer);
        }
        ArrayList<Layer> finalLayers = new ArrayList<>();
        for(Layer l: layers){
            if(l.queueNodes.length == 1 && l.layer != 0){
                finalLayers.add(l);
            }
        }
        if(finalLayers.size() == 0) return new int[0];
        QueueNode fin = finalLayers.get(0).get(0);
        for(Layer l: finalLayers){
            if(l.get(0).dist < fin.dist) fin = l.get(0);
        }


        return queueNodesToArray(fin, collums);
    }

    private static Layer checkLayer(Layer layer, int tx, int ty, char[] board, int collums, int rows, boolean[] visited) {
        ArrayList<QueueNode> queueNodes = new ArrayList<>();

        for(int i = layer.checked; i < layer.queueNodes.length; i++){
            QueueNode p = layer.get(layer.checked++);

            if(p.x == tx && p.y == ty){
                layer.checked = layer.queueNodes.length;
                Layer l = new Layer(layer.layer + 1, new QueueNode[]{p});
                l.checked = 1;
                visited[tx + ty* collums] = false;
                return l;
            }

            int[] ymods = new int[]{0, 1, -1, 0, 1, -1, 1, -1};
            int[] xmods = new int[]{1, 0, 0, -1, 1, -1, -1, 1};

            for (int a = 0; a < 8; a++)
            {
                int nx = p.x + xmods[a];
                if(nx < 0 || nx >= collums) continue;
                int ny = p.y + ymods[a];
                if(ny < 0 || ny >= rows) continue;


                if (isValidPath(visited, nx + ny *collums, board)) {
                    visited[nx + ny*collums] = true;
                    queueNodes.add(new QueueNode(nx, ny, p.dist + 1, p));
                }
            }
        }
        return new Layer(layer.layer + 1, queueNodes.toArray(new QueueNode[queueNodes.size()]));

    }

    private static boolean isValidPath(boolean[] bools, int coord, char[] board){
        if(coord < 0 || !(coord < board.length)) return false;
        if(bools[coord]) return false;
        return board[coord] == ' ' || board[coord] == 'B';
    }

    public static int lightScore(int[] light, int[] ints) {
        int score = 0;
        for(int i: ints){
            score += light[i];
        }
        score /= ints.length;
        score += light[ints[ints.length - 1]];
        return score;
    }

    static class Layer{
        QueueNode[] queueNodes;
        int checked;
        int layer;
        public Layer(int l,QueueNode[] a){
            queueNodes = a;
            layer = l;
            checked = 0;
        }

        public QueueNode get(int i) {
            return queueNodes[i];
        }
    }

    static class QueueNode {
        int x, y, dist;
        QueueNode from;

        QueueNode(int x, int y, int dist, QueueNode from) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.from = from;
        }

        public boolean hasLast() {
            return from != null;
        }

        public QueueNode getLast() {
            if(from.from == null){
                QueueNode temp = from;
                from = null;
                return temp;
            }
            return from.getLast();
        }
    }
}
