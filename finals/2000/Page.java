import java.io.*;
import java.util.*;

/**
   ACM World Finals 2000 Problem F
 */
class Page {
    static final File INPUTFILE = new File("page.in");
    static final int NUM_PAGES = 100;

    /**
       Breadth-first search: single-source shortest paths in an unweighted
       graph
     */
    static void bfs(Node[] graph, Node start) {
        for (Node n : graph)
            n.visited = false;
        start.dist = 0;

        Deque<Node> queue = new LinkedList<Node>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Node curr = queue.remove();
            curr.visited = true;
            for (Node n : curr.adj) {
                if (!n.visited) {
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

        int currCase = 1;
        while (in.hasNext()) {
            Node[] graph = new Node[NUM_PAGES];
            for (int i = 0; i < NUM_PAGES; i++)
                graph[i] = new Node();

            int numEdges = 0;
            int start = in.nextInt();
            int end = in.nextInt();
            while ((start | end) != 0) {
                numEdges++;
                graph[start - 1].adj.add(graph[end - 1]);
                start = in.nextInt();
                end = in.nextInt();
            }

            if (numEdges == 0)
                // Termination condition
                return;

            // We need all-pairs shortest paths, but 100 nodes is too many for
            // Floyd-Warshall. Instead, do single-source shortest paths
            // starting from each vertex.
            int totalLength = 0;
            int numPaths = 0;
            for (Node n : graph) {
                bfs(graph, n);
                for (Node m : graph) {
                    if (m != n && m.visited) {
                        // m is reachable from n
                        numPaths++;
                        totalLength += m.dist;
                    }
                }
            }

            System.out.printf("Case %d: average length between pages = %.3f clicks\n", currCase++, (float)totalLength / numPaths);
        }
    }
}

class Node {
    List<Node> adj;
    int dist;
    boolean visited;

    public Node() {
        this.adj = new LinkedList<Node>();
        this.visited = false;
        this.dist = Integer.MAX_VALUE;
    }
}