import java.util.*;

class Signal {
    static class Node {
        double input;
        double strength;

        public Node() {
            this.input = 0;
            this.strength = 0;
        }
    }

    static class Edge {
        double weight;
        Node start, end;

        public Edge(Node start, Node end, double weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int counter = 0;
        while (true) {
            counter++;
            int n = in.nextInt();
            if (n <= 0)
                return;
            
            Node[] nodes = new Node[n];
            for (int i = 0; i < n; i++)
                nodes[i] = new Node();
            List<Edge> edges = new LinkedList<Edge>();
            nodes[0].input = -1;

            for (int i = 0; i < n; i++) {
                nodes[i].strength = in.nextDouble();
                int m = in.nextInt();
                for (int j = 0; j < m; j++) {
                    edges.add(new Edge(nodes[i], nodes[in.nextInt()], 
                                       in.nextDouble()));
                }
            }

            for (int i = 0; i < n; i++) {
                for (Edge e : edges) {
                    e.end.input = Math.min(e.end.input, e.start.input * e.start.strength * e.weight);
                }
            }

            System.out.format("Network %d: %.2f\n", counter, Math.abs(nodes[n - 1].input * nodes[n - 1].strength));
        }
    }
}
