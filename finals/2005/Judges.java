import java.io.*;
import java.util.*;

/*
  ACM World Finals 2005 Problem C
*/
class Judges {
    final static String INPUTFILE = "judges.in";

    /*
      Generate the next subset (as a bit vector), given a set of elements which
      it must contain (as a bit vector).  Return true if there is such a
      subset; otherwise, return false.
    */
    static boolean next(boolean[] subset, boolean[] fixed) {
        // Index of the next bit to change
        int i = 0;
        while (i < subset.length && subset[i]) {
            // Flip 1's to 0's
            if (!fixed[i])
                subset[i] = false;
            i++;
        }
        if (i == subset.length) {
            // The previous subset was the last one
            return false;
        }
        else {
            // Flip a 0 to 1
            subset[i] = true;
            return true;
        }
    }

    /*
      Prim's algorithm: minimum spanning tree (Modified to only use a subset of
      the vertices, specified as a bit vector).  Return an array of backtrack
      pointers.
    */
    static int[] prims(Node[] graph, boolean[] subset, int start) {
        for (int i = 0; i < graph.length; i++) {
            graph[i].dist = Integer.MAX_VALUE;
            graph[i].prev = null;
        }
        graph[start].dist = 0;

        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        for (int i = 0; i < graph.length; i++) {
            if (subset[i]) {
                queue.add(graph[i]);
            }
        }

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            for (int i = 0; i < curr.edges.size(); i++) {
                if (queue.contains(curr.edges.get(i)) &&
                    curr.edges.get(i).dist > curr.weights.get(i)) {
                    // Java's PriorityQueue doesn't update keys in place
                    queue.remove(curr.edges.get(i));
                    curr.edges.get(i).dist = curr.weights.get(i);
                    curr.edges.get(i).prev = curr;
                    queue.add(curr.edges.get(i));
                }
            }
        }

        int[] result = new int[graph.length];
        for (int i = 0; i < graph.length; i++)
            result[i] = (graph[i].prev == null) ? -1 : 
                index(graph, graph[i].prev);
        return result;
    }

    /*
      Return the cost of the MST, or infinity if it does not connect all of the
      required vertices (specified as a bit vector).
    */
    static int cost(Node[] graph, boolean[] fixed, int start) {
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(graph[start]);

        boolean[] found = new boolean[fixed.length];
        for (int i = 0; i < fixed.length; i++)
            found[i] = false;
        found[start] = true;

        int cost = 0;
        while (!queue.isEmpty()) {
            Node curr = queue.removeFirst();
            for (int i = 0; i < curr.edges.size(); i++) {
                if (curr.edges.get(i).prev == curr) {
                    found[index(graph, curr.edges.get(i))] = true;
                    queue.add(curr.edges.get(i));
                    cost += curr.weights.get(i);
                }
            }
        }

        for (int i = 0; i < fixed.length; i++) {
            if (!found[i] && fixed[i]) {
                return Integer.MAX_VALUE;
            }
        }
        return cost;
    }

    /*
      Return the index of the query node in the graph.
    */
    static int index(Node[] graph, Node query) {
        for (int i = 0; i < graph.length; i++)
            if (query == graph[i])
                return i;
        return -1;
    }

    public static void main(String[] args) {
        // Input
        Scanner in;
        try {
            in = new Scanner(new File(INPUTFILE));
        }
        catch (FileNotFoundException e) {
            // Shouldn't get here
            return;
        }
        
        // Current test case
        int currCase = 1;
        while (in.hasNext()) {
            int numCities = in.nextInt();

            if (numCities == -1)
                // Termination condition
                return;

            // Adjacency list
            Node[] graph = new Node[numCities];
            for (int i = 0; i < numCities; i++)
                graph[i] = new Node();

            // Current subset of nodes
            boolean[] subset = new boolean[numCities];
            Arrays.fill(subset, false);

            // Subset of nodes which must be present
            boolean[] fixed = new boolean[numCities];
            Arrays.fill(fixed, false);

            int destCity = in.nextInt() - 1;
            subset[destCity] = true;
            fixed[destCity] = true;

            int numRoads = in.nextInt();
            for (int i = 0; i < numRoads; i++) {
                int start = in.nextInt() - 1;
                int end = in.nextInt() - 1;
                int dist = in.nextInt();
                graph[start].edges.add(graph[end]);
                graph[start].weights.add(dist);
                graph[end].edges.add(graph[start]);
                graph[end].weights.add(dist);
            }
            

            int numJudges = in.nextInt();
            // The starting cities for each of the judges.
            int[] start = new int[numJudges];
            for (int i = 0; i < numJudges; i++) {
                int city = in.nextInt() - 1;
                subset[city] = true;
                fixed[city] = true;
                start[i] = city;
            }

            // The answer to this problem is an MST which connects all of the
            // required nodes (each of the judges' start cities and the
            // destination city). However, we don't know which other nodes must
            // be included, so we try all subgraphs which contain all the
            // required nodes, taking the lowest cost MST we find.

            // The implementation of Prim's algorithm destructively modifies
            // the adjacency list, so we need to store the best MST found so
            // far as an array of backtrack pointers.
            int minCost = Integer.MAX_VALUE;
            int[] bestMST = new int[graph.length];
            Arrays.fill(bestMST, -1);

            do {
                int[] mst = prims(graph, subset, destCity);
                int cost = cost(graph, fixed, destCity);
                if (cost < minCost) {
                    minCost = cost;
                    bestMST = mst;
                }
            }
            while (next(subset, fixed));
            
            if (currCase > 1)
                System.out.println();
            System.out.printf("Case %d: distance = %d\n", currCase++, minCost);
            for (int i = 0; i < numJudges; i++) {
                System.out.printf("   %d", start[i] + 1);
                // To get the path, follow the series of backtrack pointers
                // from each of the judges' start cities.
                int curr = start[i];
                while (bestMST[curr] != -1) {
                    System.out.printf("-%d", bestMST[curr] + 1);
                    curr = bestMST[curr];
                }
                System.out.println();
            }
        }
    }
}

class Node implements Comparable<Node> {
    List<Node> edges;
    List<Integer> weights;
    int dist;
    Node prev;

    public Node() {
        this.edges = new LinkedList<Node>();
        this.weights = new LinkedList<Integer>();
        this.dist = Integer.MAX_VALUE;
        this.prev = null;
    }

    @Override public int compareTo(Node other) {
        if (this.dist < other.dist)
            return -1;
        else if (this.dist > other.dist)
            return 1;
        else
            return 0;
    }
}