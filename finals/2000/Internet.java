import java.io.*;
import java.util.*;

/**
   ACM World Finals 2000 Problem E
 */
class Internet {
    final static File INPUTFILE = new File("internet.in");

    /**
       Ford-Fulkerson algorithm: max-flow min-cut
     */
    static int fordFulkerson(int[][] capacities, int source, int sink) {
        int maxFlow = 0;
        int[][] flows = new int[capacities.length][capacities.length];
        for (int i = 0; i < flows.length; i++) 
            Arrays.fill(flows[i], 0);

        int[] prev = new int[capacities.length];

        while (augmentingPath(capacities, flows, prev, source, sink)) {
            int bottleneck = Integer.MAX_VALUE;
            for (int v = sink, u = prev[v]; u != -2; v = u, u = prev[v]) {
                bottleneck = Math.min(bottleneck, capacities[u][v] - 
                                      flows[u][v] + flows[v][u]);
            }

            for (int v = sink, u = prev[v]; u != -2; v = u, u = prev[v]) {
                flows[u][v] += bottleneck;
            }
            maxFlow += bottleneck;
        }
        return maxFlow;
    }

    /**
       Returns true iff there exists an augmenting path.
     */
    static boolean augmentingPath(int[][] capacities, int[][] flows,
                                  int[] prev, int source, int sink) {
        Deque<Integer> queue = new LinkedList<Integer>();
        queue.add(source);
        Arrays.fill(prev, -1);
        prev[source] = -2;
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int v = 0; v < capacities.length; v++) {
                if (prev[v] == -1 && 
                    flows[u][v] - flows[v][u] < capacities[u][v]) {
                    prev[v] = u;
                    queue.add(v);
                }
            }
        }
        return prev[sink] != -1;
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
            int numNodes = in.nextInt();

            if (numNodes == 0)
                return;

            int source = in.nextInt() - 1;
            int sink = in.nextInt() - 1;
            int numConnections = in.nextInt();

            int[][] capacities = new int[numNodes][numNodes];
            
            for (int i = 0; i < numConnections; i++) {
                int start = in.nextInt();
                int end = in.nextInt();
                int cap = in.nextInt();

                capacities[start - 1][end - 1] = cap;
                capacities[end - 1][start - 1] = cap;
            }

            int bandwidth = fordFulkerson(capacities, source, sink);
            System.out.printf("Network %d\nThe bandwith is %d\n\n", 
                              currCase++, bandwidth);
        }
    }
}
