import java.io.*;
import java.util.*;

/**
   ACM World Finals 2005 Problem B
*/
class GSM {
    static final double EPSILON = 1e-8;
    static final File INPUTFILE = new File("gsm.in");

    /*
      Return the square of the distance between the specified points.
     */
    static double squareDistance(Point a, Point b) {
        return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
    }

    /*
      Return the midpoint of the segment with the specified end points.
    */
    static Point midpoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    /*
      Return the location of the closest tower to the query point.
    */
    static Point closestTower(Point[] towers, Point query) {
        Point result = towers[0];
        double minDistance = Double.MAX_VALUE;
        for (Point p : towers) {
            if (squareDistance(p, query) < minDistance) {
                minDistance = squareDistance(p, query);
                result = p;
            }
        }
        return result;
    }

    /*
      Return the number of GSM switches on the segment with the specified end
      points.
    */
    static int cost(Point[] towers, Point a, Point b) {
        if (closestTower(towers, a) == closestTower(towers, b))
            // Base case: the two points are in the same cell
            return 0;
        else if (squareDistance(a, b) < EPSILON)
            // Base case: the two points are infinitesimally close to opposite
            // sides of a cell boundary
            return 1;
        else {
            // Recursive case
            Point mid = midpoint(a, b);
            return cost(towers, a, mid) + cost(towers, mid, b);
        }
    }

    /*
      Dijkstra's algorithm: single source shortest path
    */
    static void dijkstra(Node[] graph, int start) {
        for (Node n : graph)
            n.dist = Integer.MAX_VALUE;
        graph[start].dist = 0;
        PriorityQueue<Node> queue = 
            new PriorityQueue<Node>(Arrays.asList(graph));
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            for (int i = 0; i < curr.edges.size(); i++) {
                Node other = curr.edges.get(i);
                int weight = curr.weights.get(i);
                if (queue.contains(other) && 
                    curr.dist + weight < other.dist) {
                    // Java's PriorityQueue doesn't update keys in place
                    queue.remove(other);
                    other.dist = curr.dist + weight;
                    queue.add(other);
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
            // Shouldn't get here
            return;
        }

        int testCase = 0;
        while (in.hasNext()) {
            // Input
            int B = in.nextInt();
            int C = in.nextInt();
            int R = in.nextInt();
            int Q = in.nextInt();

            if ((B | C | R | Q) == 0)
                // Termination condition
                return;

            System.out.printf("Case %d:\n", ++testCase);

            // Get the locations of the towers
            Point[] towers = new Point[B];
            for (int i = 0; i < B; i++)
                towers[i] = new Point(in.nextDouble(), in.nextDouble());

            // Get the locations of the cities
            Point[] cities = new Point[C];
            for (int i = 0; i < C; i++)
                cities[i] = new Point(in.nextDouble(), in.nextDouble());

            // The network of cities/roads is an undirected graph, which we
            // store as an adjacency list
            Node[] graph = new Node[C];
            for (int i = 0; i < C; i++)
                graph[i] = new Node();

            // Populate the graph
            for (int i = 0; i < R; i++) {
                int a = in.nextInt() - 1;
                int b = in.nextInt() - 1;
                int weight = cost(towers, cities[a], cities[b]);
                graph[a].edges.add(graph[b]);
                graph[a].weights.add(weight);
                graph[b].edges.add(graph[a]);
                graph[b].weights.add(weight);
            }

            // Answer each of the queries
            for (int i = 0; i < Q; i++) {
                int start = in.nextInt() - 1;
                int end = in.nextInt() - 1;
                dijkstra(graph, start);
                if (graph[end].dist == Integer.MAX_VALUE)
                    System.out.println("Impossible");
                else
                    System.out.println(graph[end].dist);
            }
        }
    }
}

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Node implements Comparable<Node> {
    List<Node> edges;
    List<Integer> weights;

    // Needed for Dijkstra's algorithm
    int dist;

    public Node() {
        this.edges = new ArrayList<Node>();
        this.weights = new ArrayList<Integer>();
        this.dist = Integer.MAX_VALUE;
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