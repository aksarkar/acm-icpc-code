import java.util.*;

/**
   ACM Midatlantic Regional 2002 Problem D

   Given a compressed encoding of an image, decode it and display the image.
 */
class Compress {
    /**
       Euclidean algorithm: greatest common divisor
     */
    static int gcd(int a, int b) {
        if (b == 0)
            return a;
        else
            return gcd(b, a % b);
    }

    /**
       Return the LCM of the specified integers.
     */
    static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    /**
       Return a node which is the root of the tree given by the specified
       encoded string.
     */
    static Node parse(Deque<Character> expr) {
        char c1 = expr.removeFirst(), c2 = expr.removeFirst();
        if (c1 == '0' && c2 == '0') {
            return new Leaf(true);
        }
        else if (c1 == '1' && c2 == '1') {
            return new Leaf(false);
        }
        else if (c1 == '1' && c2 == '0') {
            Node left = parse(expr);
            Node right = parse(expr);
            return new Horiz(left, right);
        }
        else {
            Node top = parse(expr);
            Node bottom = parse(expr);
            return new Vert(top, bottom);
        }
    }

    /**
       Output the image.
     */
    static void output(char[][] img) {
        for (int i = 0; i < img[0].length + 2; i++)
            System.out.print('-');
        System.out.println();
        for (int i = 0; i < img.length; i++) {
            System.out.print('|');
            System.out.print(img[i]);
            System.out.println('|');
        }                
        for (int i = 0; i < img[0].length + 2; i++)
            System.out.print('-');
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String s = in.next();
            Deque<Character> expr = new LinkedList<Character>();
            for (int i = 0; i < s.length(); i++) {
                expr.add(s.charAt(i));
            }
            Node root = parse(expr);
            root.scale(1, 0, 0);
            char[][] img = new char[root.h][root.w];
            for (int i = 0; i < root.h; i++) {
                Arrays.fill(img[i], ' ');
            }
            root.repr(img);
            output(img);
        }
    }
}

abstract class Node {
    int w, h, x, y;

    /**
       Scale this node and its children, correcting their positions as needed.
     */
    void scale(int scale, int x, int y) {
        this.x = x;
        this.y = y;
        this.w *= scale;
        this.h *= scale;
    }

    /**
       Write this node's "pixels" into the array.
     */
    abstract void repr(char[][] img);
}

class Leaf extends Node {
    boolean black;  // Avoid code duplication

    public Leaf(boolean black) {
        this.w = 1;
        this.h = 1;
        this.x = 0;
        this.y = 0;
        this.black = black;
    }

    void scale(int scale, int x, int y) {
        super.scale(scale, x, y);
    }

    void repr(char[][] img) {
        for (int i = this.y; i < this.y + this.h; i++) {
            for (int j = this.x; j < this.x + this.w; j++) {
                img[i][j] = black ? 'X' : ' ';
            }
        }
    }
}

class Horiz extends Node {
    Node left, right;

    public Horiz(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.h = Compress.lcm(left.h, right.h);
        this.w = left.w * this.h / left.h + right.w * this.h / right.h;
    }

    void scale(int scale, int x, int y) {
        super.scale(scale, x, y);
        this.left.scale(this.h / left.h, x, y);
        this.right.scale(this.h / right.h, x + left.w, y);
    }

    void repr(char[][] img) {
        this.left.repr(img);
        this.right.repr(img);
    }
}

class Vert extends Node {
    Node top, bottom;

    public Vert(Node top, Node bottom) {
        this.top = top;
        this.bottom = bottom;
        this.w = Compress.lcm(top.w, bottom.w);
        this.h = top.h * this.w / top.w + bottom.h * this.w / bottom.w;
    }

    void scale(int scale, int x, int y) {
        super.scale(scale, x, y);
        this.top.scale(this.w / top.w, x, y);
        this.bottom.scale(this.w / bottom.w, x, y + top.h);
    }

    void repr(char[][] img) {
        this.top.repr(img);
        this.bottom.repr(img);
    }
}
