import java.io.*;
import java.util.*;

/*
  ACM World Finals 2005 Problem F
*/
class Streets {
    final static String INPUTFILE = "streets.in";

    /*
      Return the index of the cell in which the query point belongs (according
      to either the x or y axis).
    */
    static int cell(List<Integer> coords, int query) {
        int result = 0;
        while (result < coords.size() && query > coords.get(result))
            result++;
        return result;
    }

    /*
      Return true iff there is a street containing the corners common to both
      cells.
    */
    static boolean isVertCrossing(List<Street> horiz, int y, int x1, int x2) {
        for (int i = 0; i < horiz.size() && y >= horiz.get(i).y1; i++) {
            // Optimization: assume streets are sorted by increasing y
            // coordinate, reject streets with y coordinate greater than the
            // query
            if (y == horiz.get(i).y1 && 
                Math.max(horiz.get(i).x1, horiz.get(i).x2) >= Math.max(x1, x2) &&
                Math.min(horiz.get(i).x1, horiz.get(i).x2) <= Math.min(x1, x2)) {
                // Due to the way we partition the space, a street covers the
                // whole boundary iff it completely contains the segment
                // between the two common corners.  We don't need to do
                // anything special for overlapping streets because we
                // partition the space at each end point.
                return true;
            }
        }
        return false;
    }
    
    /*
      Analagous procedure for horizontal crossing.
    */
    static boolean isHorizCrossing(List<Street> vert, int x, int y1, int y2) {
        for (int i = 0; i < vert.size() && x >= vert.get(i).x1; i++) {
            if (x == vert.get(i).x1 && 
                Math.max(vert.get(i).y1, vert.get(i).y2) >= Math.max(y1, y2) &&
                Math.min(vert.get(i).y1, vert.get(i).y2) <= Math.min(y1, y2)) {
                return true;
            }
        }
        return false;
    }

    /*
      Dijkstra's algorithm: single source shortest path
    */
    static void dijkstra(Node[] graph, int start) {
        graph[start].dist = 0;
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(graph[start]);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            for (int i = 0; i < curr.edges.size(); i++) {
                Node other = curr.edges.get(i);
                int weight = curr.weights.get(i);
                if (curr.dist + weight < other.dist) {
                    if (queue.contains(other))
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
            in = new Scanner(new File(INPUTFILE));
        }
        catch (FileNotFoundException e) {
            // Shouldn't get here
            return;
        }

        // Current test case
        int curr = 1;
        while (in.hasNext()) {
            int numStreets = in.nextInt();

            if (numStreets == 0)
                // Termination condition
                return;

            System.out.printf("City %d\n", curr++);

            // Store the unique x coordinates; partition the space vertically
            Set<Integer> x = new TreeSet<Integer>();

            // Store the unique y coordinates; partition the space horizontally
            Set<Integer> y = new TreeSet<Integer>();

            // As an optimization, store the horizontal and vertical streets
            // separately to facilitate checking whether we must cross a street
            // to move in a particular direction (only consider streets in the
            // perpendicular direction).

            // Store the horizontal streets
            List<Street> horiz = new LinkedList<Street>();

            // Store the vertical streets
            List<Street> vert = new LinkedList<Street>();

            for (int i = 0; i < numStreets; i++) {
                int x1 = in.nextInt();
                int y1 = in.nextInt();
                int x2 = in.nextInt();
                int y2 = in.nextInt();

                if (x1 == x2) {
                    vert.add(new Street(x1, y1, x2, y2));
                    x.add(x1);
                }
                else {
                    horiz.add(new Street(x1, y1, x2, y2));
                    y.add(y1);
                }                
            }

            // Sort vertical streets by increasing x coordinate
            Collections.sort(vert, new VertComparator());

            // Sort horizontal streets by increasing y coordinate
            Collections.sort(horiz, new HorizComparator());

            // Convert the sets of coordinates to lists so we can access by
            // index.
            List<Integer> x_ = new LinkedList<Integer>(x);
            List<Integer> y_ = new LinkedList<Integer>(y);

            int xh = in.nextInt();
            int yh = in.nextInt();
            int xu = in.nextInt();
            int yu = in.nextInt();

            if (vert.size() < 2 || horiz.size() < 2) {
                // Special cases
                System.out.println("Peter must cross 0 streets");
                continue;
            }

            // One node for each partition (cell).  They will be stored in
            // row-major order in a flat array.
            int numNodes = (x.size() + 1) * (y.size() + 1);

            // Find the nodes corresponding to the start and end points
            int nodeH = cell(y_, yh) * (x.size() + 1) + cell(x_, xh);
            int nodeU = cell(y_, yu) * (x.size() + 1) + cell(x_, xu);

            // Initialize the graph
            Node[] graph = new Node[numNodes];
            for (int i = 0; i < numNodes; i++)
                graph[i] = new Node();

            for (int i = 0; i < y.size() + 1; i++) {
                for (int j = 0; j < x.size() + 1; j++) {
                    // Get the corners of the current cell
                    int nwx = (j == 0) ? Integer.MIN_VALUE : x_.get(j - 1);
                    int nwy = (i == y.size()) ? Integer.MAX_VALUE : y_.get(i);
                    int nex = (j == x.size()) ? Integer.MAX_VALUE : x_.get(j);
                    int ney = (i == y.size()) ? Integer.MAX_VALUE : y_.get(i);
                    int swx = (j == 0) ? Integer.MIN_VALUE : x_.get(j - 1);
                    int swy = (i == 0) ? Integer.MIN_VALUE : y_.get(i - 1);
                    int sex = (j == x.size()) ? Integer.MAX_VALUE : x_.get(j);
                    int sey = (i == 0) ? Integer.MIN_VALUE : y_.get(i - 1);

                    // Get the indices of the nodes for adjacent cells
                    int currNode = i * (x.size() + 1) + j;
                    int n = (i + 1) * (x.size() + 1) + j;
                    int e = i * (x.size() + 1) + j + 1;
                    int s = (i - 1) * (x.size() + 1) + j;
                    int w = i * (x.size() +  1) + j - 1;

                    // Add edges to adjacent cells
                    if (i > 0) {
                        graph[currNode].edges.add(graph[s]);
                        graph[currNode].weights.
                            add(isVertCrossing(horiz, sey, sex, swx) ? 1 : 0);
                    }
                    if (i < y.size()) {
                        graph[currNode].edges.add(graph[n]);
                        graph[currNode].weights.
                            add(isVertCrossing(horiz, ney, nex, nwx) ? 1 : 0);
                    }
                    if (j > 0) {
                        graph[currNode].edges.add(graph[w]);
                        graph[currNode].weights.
                            add(isHorizCrossing(vert, swx, nwy, swy) ? 1 : 0);
                    }
                    if (j < x.size()) {
                        graph[currNode].edges.add(graph[e]);
                        graph[currNode].weights.
                            add(isHorizCrossing(vert, sex, ney, sey) ? 1 : 0);
                    }
                }
            }

            // Shortest path starting from the start node
            dijkstra(graph, nodeH);
            System.out.printf("Peter has to cross %d streets\n", 
                              graph[nodeU].dist);
        }
    }
}

class Street {
    int x1, y1, x2, y2;

    public Street(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}

class HorizComparator implements Comparator<Street> {
    public int compare(Street s, Street t) {
        if (s.y1 < t.y1)
            return -1;
        else if (s.y1 > t.y1)
            return 1;
        else
            return 0;
    }
}

class VertComparator implements Comparator<Street> {
    public int compare(Street s, Street t) {
        if (s.x1 < t.x1)
            return -1;
        else if (s.x1 > t.x1)
            return 1;
        else
            return 0;
    }
}

class Node implements Comparable<Node> {
    List<Node> edges;
    List<Integer> weights;
    int dist;

    public Node() {
        this.edges = new LinkedList<Node>();
        this.weights = new LinkedList<Integer>();
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