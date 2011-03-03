import java.io.*;
import java.util.*;

/**
   ACM World Finals 2000 Problem A
 */
class Abbott {
    static final File INPUTFILE = new File("abbott.in");
    static final int MAX_DIM = 9;
    static final int NUM_DIRS = 4;
    static final int NUM_INTS = 10;

    /*
      Breadth-first search: single-source shortest paths in a graph, assuming
      all edge weights are 1
     */
    static void bfs(Node[][][] graph, Node start) {
        Deque<Node> queue = new LinkedList<Node>();
        queue.add(start);
        start.dist = 0;
        while (!queue.isEmpty()) {
            Node curr = queue.remove();
            for (Node n : curr.adj) {
                if (n.pred == null) {
                    n.pred = curr;
                    n.dist = curr.dist + 1;
                    queue.add(n);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(INPUTFILE);
        }
        catch (FileNotFoundException e) {
            return;
        }

        // Mapping of integers to directions
        List<String> dirs = Arrays.asList(new String[] {"N", "E", "S", "W"});

        while (in.hasNext()) {
            String name = in.next();

            if (name.equals("END"))
                return;

            System.out.println(name);

            // We store the maze as a directed graph. For each intersection (i,
            // j), we have 4 nodes (one for each direction).
            Node[][][] maze = new Node[MAX_DIM][MAX_DIM][NUM_DIRS];
            for (int i = 0; i < MAX_DIM; i++) {
                for (int j = 0; j < MAX_DIM; j++) {
                    for (int k = 0; k < NUM_DIRS; k++) {
                        maze[i][j][k] = new Node(String.format("(%d, %d)", 
                                                               i + 1, j + 1));
                    }
                }
            }

            // Simplify processing the input by adding pointers to "left",
            // "right", and "forward" for all directions
            for (int i = 0; i < MAX_DIM; i++) {
                for (int j = 0; j < MAX_DIM; j++) {
                    if (j > 0) {
                        // Move from (i, j) to (i, j - 1)
                        maze[i][j][0].left = maze[i][j - 1][3];
                        maze[i][j][2].right = maze[i][j - 1][3];
                        maze[i][j][3].forward = maze[i][j - 1][3];
                    }
                    if (j < MAX_DIM - 1) {
                        // Move from (i, j) to (i, j + 1)
                        maze[i][j][2].left = maze[i][j + 1][1];
                        maze[i][j][0].right = maze[i][j + 1][1];
                        maze[i][j][1].forward = maze[i][j + 1][1];
                    }
                    if (i > 0) {
                        // Move from (i, j) to (i - 1, j)
                        maze[i][j][1].left = maze[i - 1][j][0];
                        maze[i][j][3].right = maze[i - 1][j][0];
                        maze[i][j][0].forward = maze[i - 1][j][0];
                    }
                    if (i < MAX_DIM - 1) {
                        // Move from (i, j) to (i + 1, j)
                        maze[i][j][3].left = maze[i + 1][j][2];
                        maze[i][j][1].right = maze[i + 1][j][2];
                        maze[i][j][2].forward = maze[i + 1][j][2];
                    }
                }
            }

            // Start node
            int sx = in.nextInt();
            int sy = in.nextInt();
            // Starting direction
            int sdir = dirs.indexOf(in.next());
            // Ending node
            int ex = in.nextInt();
            int ey = in.nextInt();

            // Add the edge from the starting node into the maze
            maze[sx - 1][sy - 1][sdir].adj.add(maze[sx - 1][sy - 1][sdir].forward);

            // Add the rest of the edges
            int row = in.nextInt();
            while (row != 0) {
                int col = in.nextInt();

                String sign = in.next();
                while (!sign.equals("*")) {
                    int dir = dirs.indexOf(String.valueOf(sign.charAt(0)));
                    for (int i = 1; i < sign.length(); i++) {
                        if (sign.charAt(i) == 'L') {
                            maze[row - 1][col - 1][dir].adj.add(maze[row - 1][col - 1][dir].left);
                        }
                        else if (sign.charAt(i) == 'R') {
                            maze[row - 1][col - 1][dir].adj.add(maze[row - 1][col - 1][dir].right);
                        }
                        else {
                            maze[row - 1][col - 1][dir].adj.add(maze[row - 1][col - 1][dir].forward);
                        }
                    }
                    sign = in.next();
                }
                row = in.nextInt();
            }

            // Single-source shortest paths
            bfs(maze, maze[sx - 1][sy - 1][sdir]);

            // Find the closest end node (out of the four with the specified
            // position i, j)
            int minDist = Integer.MAX_VALUE;
            Node closest = null;
            for (int k = 0; k < NUM_DIRS; k++) {
                if (maze[ex - 1][ey - 1][k].dist < minDist) {
                    minDist = maze[ex - 1][ey - 1][k].dist;
                    closest = maze[ex - 1][ey - 1][k];
                }
            }

            if (closest == null)
                System.out.println("  No Solution Possible");
            else {
                // Build the path
                Deque<String> stack = new LinkedList<String>();
                Node curr = closest;
                while (curr != null) {
                    stack.push(curr.label);
                    curr = curr.pred;
                }

                // Print the path
                while (!stack.isEmpty()) {
                    for (int i = 0; !stack.isEmpty() && i < NUM_INTS; i++) {
                        System.out.printf("  %s", stack.pop());
                    }
                    System.out.println();
                }
            }
        }
    }
}

class Node {
    List<Node> adj;
    Node pred, left, right, forward;
    String label;
    int dist;

    public Node(String label) {
        this.adj = new LinkedList<Node>();
        this.pred = null;
        this.left = null;
        this.right = null;
        this.forward = null;
        this.label = label;
        this.dist = Integer.MAX_VALUE;
    }
}