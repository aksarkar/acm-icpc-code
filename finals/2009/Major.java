import java.io.*;
import java.util.*;

/**
   ACM World Finals 2009 Problem H

   Given votes on multiple bills by multiple ministers, find an assignment of
   outcomes to each bill which satisfies at least half of each minister's
   votes. This problem reduces to 2-SAT, which we solve using the well-known
   algorithm.
 */
class Major {
    final static File INPUTFILE = new File("major.in");

    /**
       Populate the implication graph with edges corresponding to the
       implications for a single minister's votes.
     */
    static void populate(Node[] graph, int[] bills, String[] votes) {
        int B = graph.length / 2;
        if (bills.length < 3) {
            // Special cases. If a minister votes on only one bill, it must be
            // satisfied; if a minister votes on two bills, both must be
            // satisfied. (This is the only way we can satisfy more than half
            // their votes.)

            // In the implication graph, for each decision x_i, we add an edge
            // from ~x_i to x_i. The only way to satisfy such an implication is
            // to assign ~x_i = false, i.e. x_i = true.
            for (int i = 0; i < bills.length; i++) {
                int start = (votes[i].equals("y")) ? bills[i] + B : bills[i];
                graph[start].edges.add(graph[start].complement);
            }
        }
        else {
            // If the ith vote is not satisfied, then for every j != i, the jth
            // vote must be satisfied.
            for (int i = 0; i < bills.length; i++) {
                for (int j = 0; j < bills.length; j++) {
                    if (i == j)
                        continue;
                    int start = votes[i].equals("y") ?
                        B + bills[i] : bills[i];
                    int end = votes[j].equals("y") ? 
                        bills[j] : B + bills[j];
                    graph[start].edges.add(graph[end]);
                }
            }
        }
    }

    /**
       Strongly connected components: Tarjan's algorithm
     */
    static List<List<Node>> tarjan(Node[] graph) {
        Deque<Node> stack = new LinkedList<Node>();
        int index = 0;
        List<List<Node>> sc_components = new LinkedList<List<Node>>();

        for (int i = 0; i < graph.length; i++) {
            if (graph[i].index == -1) {
                visit(graph[i], stack, index, sc_components);
            }
        }
        return sc_components;
    }
    
    static void visit(Node n, Deque<Node> stack, int index, 
                      List<List<Node>> sc_components) {
        n.index = index;
        n.lowlink = index;
        stack.push(n);

        for (Node other : n.edges) {
            if (other.index == -1) {
                visit(other, stack, index + 1, sc_components);
                n.lowlink = Math.min(n.lowlink, other.lowlink);
            }
            else if (stack.contains(other)) {
                n.lowlink = Math.min(n.lowlink, other.lowlink);
            }
        }

        if (n.lowlink == n.index) {
            List<Node> component = new LinkedList<Node>();
            while (!component.contains(n)) {
                Node curr = stack.pop();
                component.add(curr);
                curr.component = component;                
            }
            sc_components.add(component);
        }
    }

    /**
       A 2-SAT instance is satisfiable iff each variable is in a different
       strongly connected component from its complement.
     */
    static boolean isSatisfiable(Node[] graph) {
        for (Node n : graph) {
            if (n.component == n.complement.component) {
                return false;
            }
        }
        return true;
    }

    /**
       Assign values to each variable in the 2-SAT instance. Process
       strongly-connected components in reverse topological order. If the
       current component's complement has not been marked, mark it true;
       otherwise, mark it false. If the complement is not reachable from the
       current component, mark it and its complement "?", as they can take
       either value.
     */
    static void label(Node[] graph, List<List<Node>> sc_components) {
        for (List<Node> component : sc_components) {
            String mark = "";
            boolean reachable = isReachable(graph, component.get(0), 
                                            component.get(0).complement);
            if (component.get(0).complement.isMarked) {
                if (!reachable) {
                    mark = "?";
                }
                else {
                    mark = "n";
                }
            }
            else {
                mark = "y";
            }
            for (Node n : component) {
                n.isMarked = true;
                n.mark = mark;
                if (!reachable)
                    n.complement.mark = mark;
            }
        }
    }

    /**
       Reachability: breadth-first search.
     */
    static boolean isReachable(Node[] graph, Node start, Node end) {
        for (Node n : graph)
            n.visited = false;
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Node curr = queue.remove();
            curr.visited = true;
            if (curr == end) {
                return true;
            }
            for (Node other : curr.edges) {
                if (!other.visited) {
                    queue.add(other);
                }
            }
        }
        return false;
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
            int B = in.nextInt();
            int M = in.nextInt();

            if ((B | M) == 0) {
                // Termination condition
                return;
            }

            // Implication graph. We store all nodes x_1, x_2, ..., x_n
            // followed by ~x_1, ~x_2, ..., ~x_n.
            Node[] graph = new Node[B * 2];
            for (int i = 0; i < B * 2; i++) {
                graph[i] = new Node();
            }

            // Treat the adjacency list as if it were circular to easily
            // compute the index of the complement of each node.
            for (int i = 0; i < B * 2; i++) {
                graph[i].complement = graph[(i + B) % (B * 2)];
            }

            // Read the minister's votes
            for (int i = 0; i < M; i++) {
                int numVotes = in.nextInt();

                int[] bills = new int[numVotes];
                String[] votes = new String[numVotes];

                for (int j = 0; j < numVotes; j++) {
                    bills[j] = in.nextInt() - 1;
                    votes[j] = in.next();
                }
                
                // Add edges to the implication graph
                populate(graph, bills, votes);
            }

            System.out.printf("Case %d: ", currCase++);

            // Get the strongly connected components in the implication graph
            List<List<Node>> sc_components = tarjan(graph);

            if (!isSatisfiable(graph)) {
                // There is no solution to this instance
                System.out.println("impossible");
            }
            else {
                // Assign marks to the variables ("y", "n", "?")
                label(graph, sc_components);

                // Print out the mark for each of the variables x_1, x_2, ...,
                // x_n (not their complements)
                for (int i = 0; i < B; i++) {
                    System.out.print(graph[i].mark);
                }
                System.out.println();
            }
        }
    }
}

class Node {
    List<Node> edges;

    // Needed for Tarjan's algorithm
    int index;
    int lowlink;

    // Needed for checking 2-SAT
    Node complement;
    List<Node> component;

    // True iff this variable's truth value has been assigned
    boolean isMarked;

    // The decision on this variable's value ("y", "n", "?")
    String mark;

    // Needed for BFS
    boolean visited;

    public Node() {
        this.edges = new LinkedList<Node>();

        this.index = -1;
        this.lowlink = -1;

        this.isMarked = false;
        this.mark = "";

        this.visited = false;
    }
}
