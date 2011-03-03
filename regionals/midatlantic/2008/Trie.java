import java.util.*;

class Trie {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            String line = in.next();
            if (line.equals("END"))
                return;

            Node root = new Node(line.charAt(0), null);
            Node curr = root;
            boolean left = true;
            for (int i = 1; i < line.length(); i++) {
                char next = line.charAt(i);
                if (next == '#') {
                    if (!left) {
                        if (curr == root)
                            break;
                        do
                            curr = curr.predecessor;
                        while (curr.right != null && curr.predecessor != null);
                    }
                    left = false;
                }
                else if (left) {
                    curr.left = new Node(next, curr);
                    System.err.format("Adding %c (%s) to left of %c (%s)\n", next, curr.left, curr.label, curr);
                    curr = curr.left;
                }
                else {
                    curr.right = new Node(next, curr);
                    System.err.format("Adding %c (%s) to right of %c (%s)\n", next, curr.right, curr.label, curr);
                    curr = curr.right;
                    left = true;
                }
            }

            HashMap<Node, Integer> count = new HashMap<Node, Integer>();
            for (Node n : new Preorder(root)) {
                System.err.format("Counting %s (%c)\n", n, n.label);
                if (!count.containsKey(n))
                    count.put(n, 0);
                count.put(n, count.get(n) + 1);
            }

            Node maxShared = root;
            int maxSaving = 0;
            int maxCount = 1;
            for (Node n : new Preorder(root)) {
                System.err.format("Visiting %s (%c)\n", n, n.label);
                int saving = n.numNodes() * (count.get(n) - 1);
                if (saving > maxSaving || saving == maxSaving && n.numNodes() < maxShared.numNodes()) {
                    maxSaving = saving;
                    maxShared = n;
                }
            }

            System.out.format("%s %d\n", maxShared.repr(), maxSaving);
        }
    }
}

class Node {
    Node predecessor;
    char label;
    Node left, right;

    public Node(char label, Node predecessor) {
        this.label = label;
        this.predecessor = predecessor;
        this.left = null;
        this.right = null;
    }

    public int hashCode() {
        int h = 59;
        h += (int)label;
        h += (left == null) ? 0 : left.hashCode();
        h += (right == null) ? 0 : right.hashCode();
        return h;
    }

    public boolean equals(Object o) {
        if (o.getClass() != Node.class)
            return false;
        Node other = (Node)o;
        if (other.label != this.label)
            return false;
        if (this.left == null && other.left != null ||
            this.left != null && other.left == null)
            return false;
        if (this.left != null && other.left != null && 
            !this.left.equals(other.left))
            return false;
        if (this.right == null && other.right != null ||
            this.right != null && other.right == null)
            return false;
        if (this.right != null && other.right != null && 
            !this.right.equals(other.right))
            return false;
        return true;
    }

    public String repr() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.label);
        if (this.left != null)
            sb.append(this.left.repr());
        else
            sb.append("#");
        if (this.right != null)
            sb.append(this.right.repr());
        else
            sb.append("#");
        return sb.toString();
    }

    public int numNodes() {
        int result = 1;
        result += (this.left == null) ? 0 : this.left.numNodes();
        result += (this.right == null) ? 0 : this.right.numNodes();
        return result;
    }
}

class Preorder implements Iterator<Node>, Iterable<Node> {
    private LinkedList<Node> stack;

    public Preorder(Node root) {
        this.stack = new LinkedList<Node>();
        stack.add(root);
    }

    public Iterator<Node> iterator() {
        return this;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public Node next() {
        Node result = stack.removeLast();
        if (result.right != null)
            stack.add(result.right);
        if (result.left != null)
            stack.add(result.left);
        return result;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
