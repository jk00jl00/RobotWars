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

    private static int[] queueToArray(Queue q, int columns, Queue.QueueNode p) {
        ArrayList<Integer> i = new ArrayList<>();
        while (p.hasFirst()){
            Queue.QueueNode qn = q.getLastFromP(p);
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

        Queue q = new Queue();
        q.push(new Queue.QueueNode(sx, sy, 0, null));
        visited[sc] = true;
        int level = 1;

        while(q.hasNext()){
            Queue.QueueNode p = q.getFirst();
            if(p == null) continue;

            if(p.x == tx && p.y == ty){
                return queueToArray(q, collums, p);
            }

            int[] ymods = new int[]{0, 1, -1, 0};
            int[] xmods = new int[]{1, 0, 0, -1};

            for (int i = 0; i < 4; i++)
            {
                int nx = p.x + xmods[i];
                if(nx < 0 || nx >= collums) continue;
                int ny = p.y + ymods[i];
                if(ny < 0 || ny >= rows) continue;


                if (isValidPath(visited, nx + ny *collums, board)) {
                    visited[nx + ny*collums] = true;
                    q.push(new Queue.QueueNode(nx, ny, p.dist + 1, p));
                }
            }

        }
        return new int[0];
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

    static class Queue{
        QueueNode q;
        void push(QueueNode q){
            q.n = this.q;
            this.q = q;
        }
        QueueNode pop(){
            QueueNode temp = q;
            q = q.n;
            return temp;
        }

        QueueNode getLast(){
            if(q == null){
                return null;
            }
            QueueNode temp = q.getLast();
            if(q.n == null) q = null;
            else
                q.removeLast();
            return temp;
        }

        QueueNode getLastFromP(QueueNode p) {
            QueueNode temp = p.getFirst();
            if(temp == p) q = null;
            return temp;
        }

        public boolean hasNext() {
            return q != null;
        }

        public int size(){
            int i = 0;
            QueueNode qn = q;
            if(qn != null) {
               while(qn.n != null){
                   i++;
                   qn = qn.n;
               }
               i++;
            }
            return i;
        }

        public QueueNode getFirst() {
            return q;
        }

        static class QueueNode{
            int x, y, dist;
            QueueNode n;
            QueueNode first;

            QueueNode(int x, int y, int dist, QueueNode first){
                this.x = x;
                this.y = y;
                this.dist = dist;
                this.first = first;
            }

            public QueueNode getLast() {
                if(n == null) return this;
                return n.getLast();
            }

            public void removeLast() {
                if(n.n == null) n = null;
                else n.removeLast();
            }

            public QueueNode getFirst() {
                if(first == null)
                    return this;
                if(first.first == null){
                    QueueNode temp = first;
                    first = null;
                    return temp;
                }
                return first.getFirst();
            }

            public boolean hasFirst() {
                return first != null;
            }
        }
    }
}
