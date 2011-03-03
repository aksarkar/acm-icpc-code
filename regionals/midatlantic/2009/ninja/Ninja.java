import java.util.*;

class Ninja {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int N = in.nextInt();
            int D = in.nextInt();
            if (N == 0 && D == 0)
                return;
            else if (N == 1) {
                in.nextInt();
                System.out.println("0");
                continue;
            }

            Tree[] trees = new Tree[N];
            for (int i = 0; i < N; i++)
                trees[i] = new Tree(in.nextInt(), i);

            Tree[] sorted = new Tree[N + 2];
            sorted[0] = null;
            sorted[N + 1] = null;
            for (int i = 1; i < N + 1; i++)
                sorted[i] = trees[i - 1];
            Arrays.sort(sorted, 1, N + 1);

            for (int i = 1; i < N + 1; i++) {
                sorted[i].next = sorted[i + 1];
                sorted[i].prev = sorted[i - 1];
            }

            Tree min = sorted[1];
            Tree max = sorted[N];
            boolean left = min.pos < max.pos;

            boolean valid = true;
            for (int i = 0; valid && i < N; i++)
                valid = trees[i].next == null || Math.abs(trees[i].next.pos - trees[i].pos) < D;
            if (!valid) {
                System.out.println("-1");
                continue;
            }

            boolean changed;
            do {
                System.err.println(Arrays.deepToString(trees));
                changed = false;
                for (int i = 0; !changed && i < N; i++) {
                    if (left && trees[i] == max || !left && trees[i] == min) {
                        break;
                    }
                    if (i == 0) {
                        int newPos = Math.max(trees[i].next != null ? trees[i].next.pos - D : Integer.MIN_VALUE,
                                              trees[i].prev != null ? trees[i].prev.pos - D : Integer.MIN_VALUE);
                        changed = newPos != trees[i].pos;
                        trees[i].pos = newPos;
                    }
                    else {
                        int bound1 = trees[i - 1].pos + 1;
                        int bound2 = trees[i].next != null && trees[i].next.pos > trees[i].pos ? 
                            trees[i].next.pos - D : trees[i].pos;
                        int bound3 = trees[i].prev != null && trees[i].prev.pos > trees[i].pos ? 
                            trees[i].prev.pos - D : trees[i].pos;
                        int newPos = Math.min(bound1, Math.max(bound2, bound3));
                        changed = newPos != trees[i].pos;
                        trees[i].pos = newPos;
                        
                    }
                }
            } 
            while (changed);
            System.out.println(Math.abs(sorted[1].pos - sorted[N].pos));
        }
    }
}

class Tree implements Comparable<Tree> {
    int height;
    int pos;
    Tree prev;
    Tree next;

    public Tree(int height, int pos) {
        this.height = height;
        this.pos = pos;
        this.next = null;
        this.prev = null;
    }

    @Override public int compareTo(Tree other) {
        if (this.height > other.height)
            return 1;
        else if (this.height < other.height)
            return -1;
        else
            return 0;
    }

    @Override public String toString() {
        return String.format("(%d, %d)", this.pos, this.height);
    }
}