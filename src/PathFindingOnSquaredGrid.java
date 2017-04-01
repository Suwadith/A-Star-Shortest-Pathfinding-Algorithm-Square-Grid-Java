import java.awt.*;
import java.util.*;

/************************************************************************
 *  Author: Dr E Kapetanios
 *  Last update: 22-02-2017
 *
 ************************************************************************/


public class PathFindingOnSquaredGrid {

    static Node[][] cell;
    static Scanner in = new Scanner(System.in);
    static ArrayList<Node> pathList = new ArrayList<>();
    static ArrayList<Node> closedList = new ArrayList<>();
    static boolean additionalPath = false;

    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;

        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }

        return full;
    }

    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i + 1, j);   // down
        flow(open, full, i, j + 1);   // right
        flow(open, full, i, j - 1);   // left
        flow(open, full, i - 1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) return true;
        }

        return false;
    }

    // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                // StdOut.println("Hello");
                directPerc = 1;
                int rowabove = N - 2;
                for (int i = rowabove; i >= 0; i--) {
                    if (full[i][j]) {
                        // StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
                        directPerc++;
                    } else break;
                }
            }
        }

        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true;
        else return false;
    }

    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        ;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        ;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.circle(j, N - i - 1, .5);
                    } else StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }


    public static void generateHValue(boolean matrix[][], int Ai, int Aj, int Bi, int Bj, int n, int v, int d, boolean additionalPath) {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                cell[y][x] = new Node(y, x);
                if (matrix[y][x]) {
                    cell[y][x].hValue = Math.abs(y - Bi) + Math.abs(x - Bj);
                } else {
                    cell[y][x].hValue = -1;
                }
            }
        }

        generatePath(cell, Ai, Aj, Bi, Bj, n, v, d, additionalPath);

    }

    public static void menu() {
        System.out.println("Please choose N: ");
        int n = in.nextInt();
        System.out.println("Please choose Percolation: ");
        double p = in.nextDouble();
        int cost = 0;

        boolean[][] randomlyGenMatrix = random(n, p);

        StdArrayIO.print(randomlyGenMatrix);
        show(randomlyGenMatrix, true);

        System.out.println();
        System.out.println("The system percolates: " + percolates(randomlyGenMatrix));

        System.out.println();
        System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
        System.out.println();

        cell = new Node[randomlyGenMatrix.length][randomlyGenMatrix.length];

        System.out.println("Enter y1: ");
        int Ai = in.nextInt();
        System.out.println("Enter x1: ");
        int Aj = in.nextInt();
        System.out.println("Enter y2: ");
        int Bi = in.nextInt();
        System.out.println("Enter x2: ");
        int Bj = in.nextInt();



        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

        Stopwatch timerFlow = new Stopwatch();

        StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
        for(int j=0; j<3; j++) {
            if (j == 0) {

                generateHValue(randomlyGenMatrix, Ai, Aj, Bi, Bj, n, 10, 10, true);

                StdDraw.setPenColor(Color.RED);

                for (int i = 0; i < pathList.size(); i++) {
                    /*System.out.println(pathList.get(i).x + " " + pathList.get(i).y);*/
                    StdDraw.filledSquare(pathList.get(i).y, n - pathList.get(i).x - 1, .5);
                    cost+=pathList.get(i).fCellValue;
                }
                StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
                System.out.println(cost);
                cost = 0;


                if(pathList.contains(cell[Bi][Bj])){
                    System.out.println("found");
                }else{
                    System.out.println("not found");
                }
                pathList.clear();
                closedList.clear();
            }
            if (j == 1) {
                generateHValue(randomlyGenMatrix, Ai, Aj, Bi, Bj, n, 10, 14, true);

                StdDraw.setPenColor(Color.BLACK);

                for (int i = 0; i < pathList.size(); i++) {
                   /* System.out.println(pathList.get(i).x + " " + pathList.get(i).y);*/
                    StdDraw.circle(pathList.get(i).y, n - pathList.get(i).x - 1, .4);
                    cost+=pathList.get(i).fCellValue;
                }
                System.out.println(cost);
                cost =0;
                StdOut.println("Elapsed time = " + timerFlow.elapsedTime());

                if(pathList.contains(cell[Bi][Bj])){
                    System.out.println("found");
                }else{
                    System.out.println("not found");
                }
                pathList.clear();
                closedList.clear();
            }
            if (j == 2) {
                generateHValue(randomlyGenMatrix, Ai, Aj, Bi, Bj, n, 10, 10, false);

                StdDraw.setPenColor(Color.GREEN);

                for (int i = 0; i < pathList.size(); i++) {
                    /*System.out.println(pathList.get(i).x + " " + pathList.get(i).y);*/
                    StdDraw.filledCircle(pathList.get(i).y, n - pathList.get(i).x - 1, .2);
                    cost+=pathList.get(i).fCellValue;
                }
                System.out.println(cost);
                cost =0;
                StdOut.println("Elapsed time = " + timerFlow.elapsedTime());


                if(pathList.contains(cell[Bi][Bj])){
                    System.out.println("found");
                }else{
                    System.out.println("not found");
                }
                pathList.clear();
                closedList.clear();
            }
        }

    }


    public static void generatePath(Node hValue[][], int Ai, int Aj, int Bi, int Bj, int n, int v, int d, boolean additionalPath) {

        PriorityQueue<Node> openList = new PriorityQueue<Node>(100, new Comparator() {
            @Override
            public int compare(Object cell1, Object cell2) {
                return ((Node) cell1).fValue < ((Node) cell2).fValue ? -1 :
                        ((Node) cell1).fValue > ((Node) cell2).fValue ? 1 : 0;
            }
        });

        openList.add(cell[Ai][Aj]);

        while (true) {

            Node node = openList.poll();

            if (node == null) {
                break;
            }

            if (node == cell[Bi][Bj]) {
                closedList.add(node);
                break;
            }

            closedList.add(node);

            //Left
            try {
                if (cell[node.x][node.y - 1].hValue != -1
                        && !openList.contains(cell[node.x][node.y - 1])
                        && !closedList.contains(cell[node.x][node.y - 1])) {
                    int tCost = node.fValue + v;
                    cell[node.x][node.y - 1].fCellValue=v;
                    int cost = cell[node.x][node.y - 1].hValue + tCost;
                    if (cell[node.x][node.y - 1].fValue > cost || !openList.contains(cell[node.x][node.y - 1]))
                        cell[node.x][node.y - 1].fValue = cost;

                    openList.add(cell[node.x][node.y - 1]);
                    cell[node.x][node.y - 1].parent = node;
                }
            } catch (IndexOutOfBoundsException e) {
            }

            //Right
            try {
                if (cell[node.x][node.y + 1].hValue != -1
                        && !openList.contains(cell[node.x][node.y + 1])
                        && !closedList.contains(cell[node.x][node.y + 1])) {
                    int tCost = node.fValue + v;
                    cell[node.x][node.y + 1].fCellValue=v;
                    int cost = cell[node.x][node.y + 1].hValue + tCost;
                    if (cell[node.x][node.y + 1].fValue > cost || !openList.contains(cell[node.x][node.y + 1]))
                        cell[node.x][node.y + 1].fValue = cost;

                    openList.add(cell[node.x][node.y + 1]);
                    cell[node.x][node.y + 1].parent = node;
                }
            } catch (IndexOutOfBoundsException e) {
            }

            //Bottom
            try {
                if (cell[node.x + 1][node.y].hValue != -1
                        && !openList.contains(cell[node.x + 1][node.y])
                        && !closedList.contains(cell[node.x + 1][node.y])) {
                    int tCost = node.fValue + v;
                    cell[node.x + 1][node.y].fCellValue=v;
                    int cost = cell[node.x + 1][node.y].hValue + tCost;
                    if (cell[node.x + 1][node.y].fValue > cost || !openList.contains(cell[node.x + 1][node.y]))
                        cell[node.x + 1][node.y].fValue = cost;

                    openList.add(cell[node.x + 1][node.y]);
                    cell[node.x + 1][node.y].parent = node;
                }
            } catch (IndexOutOfBoundsException e) {
            }

            //Top
            try {
                if (cell[node.x - 1][node.y].hValue != -1
                        && !openList.contains(cell[node.x - 1][node.y])
                        && !closedList.contains(cell[node.x - 1][node.y])) {
                    int tCost = node.fValue + v;
                    cell[node.x - 1][node.y].fCellValue=v;
                    int cost = cell[node.x - 1][node.y].hValue + tCost;
                    if (cell[node.x - 1][node.y].fValue > cost || !openList.contains(cell[node.x - 1][node.y]))
                        cell[node.x - 1][node.y].fValue = cost;

                    openList.add(cell[node.x - 1][node.y]);
                    cell[node.x - 1][node.y].parent = node;
                }
            } catch (IndexOutOfBoundsException e) {
            }

            if (additionalPath) {
                //TopLeft
                try {
                    if (cell[node.x - 1][node.y - 1].hValue != -1
                            && !openList.contains(cell[node.x - 1][node.y - 1])
                            && !closedList.contains(cell[node.x - 1][node.y - 1])) {
                        int tCost = node.fValue + d;
                        cell[node.x - 1][node.y - 1].fCellValue=d;
                        int cost = cell[node.x - 1][node.y - 1].hValue + tCost;
                        if (cell[node.x - 1][node.y - 1].fValue > cost || !openList.contains(cell[node.x - 1][node.y - 1]))
                            cell[node.x - 1][node.y - 1].fValue = cost;

                        openList.add(cell[node.x - 1][node.y - 1]);
                        cell[node.x - 1][node.y - 1].parent = node;
                    }
                } catch (IndexOutOfBoundsException e) {
                }

                //TopRight
                try {
                    if (cell[node.x - 1][node.y + 1].hValue != -1
                            && !openList.contains(cell[node.x - 1][node.y + 1])
                            && !closedList.contains(cell[node.x - 1][node.y + 1])) {
                        int tCost = node.fValue + d;
                        cell[node.x - 1][node.y + 1].fCellValue=d;
                        int cost = cell[node.x - 1][node.y + 1].hValue + tCost;
                        if (cell[node.x - 1][node.y + 1].fValue > cost || !openList.contains(cell[node.x - 1][node.y + 1]))
                            cell[node.x - 1][node.y + 1].fValue = cost;

                        openList.add(cell[node.x - 1][node.y + 1]);
                        cell[node.x - 1][node.y + 1].parent = node;
                    }
                } catch (IndexOutOfBoundsException e) {
                }

                //BottomLeft
                try {
                    if (cell[node.x + 1][node.y - 1].hValue != -1
                            && !openList.contains(cell[node.x + 1][node.y - 1])
                            && !closedList.contains(cell[node.x + 1][node.y - 1])) {
                        int tCost = node.fValue + d;
                        cell[node.x + 1][node.y - 1].fCellValue=d;
                        int cost = cell[node.x + 1][node.y - 1].hValue + tCost;
                        if (cell[node.x + 1][node.y - 1].fValue > cost || !openList.contains(cell[node.x + 1][node.y - 1]))
                            cell[node.x + 1][node.y - 1].fValue = cost;

                        openList.add(cell[node.x + 1][node.y - 1]);
                        cell[node.x + 1][node.y - 1].parent = node;
                    }
                } catch (IndexOutOfBoundsException e) {
                }

                //BottomRight
                try {
                    if (cell[node.x + 1][node.y + 1].hValue != -1
                            && !openList.contains(cell[node.x + 1][node.y + 1])
                            && !closedList.contains(cell[node.x + 1][node.y + 1])) {
                        int tCost = node.fValue + d;
                        cell[node.x + 1][node.y + 1].fCellValue=d;
                        int cost = cell[node.x + 1][node.y + 1].hValue + tCost;
                        if (cell[node.x + 1][node.y + 1].fValue > cost || !openList.contains(cell[node.x + 1][node.y + 1]))
                            cell[node.x + 1][node.y + 1].fValue = cost;

                        openList.add(cell[node.x + 1][node.y + 1]);
                        cell[node.x + 1][node.y + 1].parent = node;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }

        /*for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(cell[i][j].fValue + "    ");
            }
            System.out.println();
        }*/

        Node endNode = closedList.get(closedList.size() - 1);

        while (endNode.parent != null) {
            Node currentNode = endNode;
            pathList.add(currentNode);
            endNode = endNode.parent;
            /*if(Collections.frequency(pathList, currentNode)>1){
                System.out.println("Path not found!");
                break;
            }*/
        }

        /*pathList.add(closedList.get(0));*/

        openList.clear();

        System.out.println();

    }


    public static void main(String[] args) {

        menu();

    }
}

