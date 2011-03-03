import java.util.*;

/**
   ACM Midatlantic Regional 2010 Problem H
 */
class Cables {
  static double dist(Node a, Node b) {
    return Math.hypot(a.x - b.x, a.y - b.y);
  }
  
  static void prims(Node[] graph, int start) {
    graph[start].dist = 0;
    PriorityQueue<Node> queue = new PriorityQueue<Node>();
    for (int i = 0; i < graph.length; i++) {
      queue.add(graph[i]);
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
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) {
      int n = in.nextInt();
      if (n == 0)
        return;
      Node[] graph = new Node[n];
      for (int i = 0; i < n; i++) {
        graph[i] = new Node(in.nextDouble(), in.nextDouble());
      }
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i != j) {
            double d = dist(graph[i], graph[j]);
            graph[i].edges.add(graph[j]);
            graph[i].weights.add(d);
            graph[j].edges.add(graph[i]);
            graph[j].weights.add(d);
          }
        }
      }
      prims(graph, 0);
      double d = 0;
      for (Node node : graph) {
        if (node.prev != null)
          d += dist(node, node.prev);
      }
      System.out.printf("%.2f\n", d);
    }
  }
}

class Node implements Comparable<Node> {
  double x, y, dist;
  List<Node> edges;
  List<Double> weights;
  Node prev;

  public Node(double x, double y) {
    this.x = x;
    this.y = y;
    edges = new ArrayList<Node>();
    weights = new ArrayList<Double>();
    dist = Double.MAX_VALUE;
    prev = null;
  }

  public int compareTo(Node other) {
    if (Math.abs(this.dist - other.dist) < 1e-8)
      return 0;
    else if (this.dist < other.dist)
      return -1;
    else
      return 1;
  }
}