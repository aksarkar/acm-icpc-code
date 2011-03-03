import java.io.*;
import java.util.*;

/**
   ACM World Finals 2000 Problem H
 */
class Stopper {
    final static File INPUTFILE = new File("stopper.in");
    final static double EPS = 1e-8;

    /**
       Return the distance between the specified points.
     */
    static double distance(Point p, Point q) {
        return Math.sqrt(Math.pow(p.x - q.x, 2) + Math.pow(p.y - q.y, 2));
    }

    /**
       Return the length of the specified segment.
     */
    static double length(Segment s) {
        return distance(s.p, s.q);
    }

    /**
       Return true iff the specified intersects the specified circle more than
       once.
     */
    static boolean intersection(Circle c, Segment s) {
        return Math.pow(c.r, 2) * length(s) * 
            (s.p.x * s.q.y - s.p.y * s.q.x) > 0;
    }

    /**
       Return the intersection point(s) of the specified circles.
     */
    static List<Point> intersection(Circle c, Circle d) {
        List<Point> result = new LinkedList<Point>();
        double distance = distance(c.c, d.c);
        if (Math.abs(c.r + d.r - distance) > EPS) {
            if (distance > c.r + d.r || distance < Math.abs(c.r - d.r) ||
                distance == 0 && c.r == d.r) {
                return result;
            }
        }

        double a = (Math.pow(c.r, 2) - Math.pow(d.r, 2) + 
                    Math.pow(distance, 2)) / (2 * distance);
        Point mid = new Point(c.c.x + a * (d.c.x - c.c.x) / distance,
                              c.c.y + a * (d.c.y - c.c.y) / distance);
        double h1 = Math.sqrt(Math.pow(c.r, 2) - Math.pow(a, 2));
        double h2 = -h1;
        result.add(new Point(mid.x + -h1 * (d.c.y - c.c.x) / distance,
                             mid.y - h1 * (d.c.x - c.c.x) / distance));
        if (Math.abs(a - c.r) > EPS) {
            result.add(new Point(mid.x + -h2 * (d.c.y - c.c.x) / distance,
                                 mid.y - h2 * (d.c.x - c.c.x) / distance));
        }
        return result;
    }

    /**
       Try all possible ways of laying out the three stoppers. Return true if
       at least one of those ways is valid.
     */
    static boolean layout(Point[] triangle, double[] inner, double[] outer) {
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
            int l1 = in.nextInt();
            int l2 = in.nextInt();
            int l3 = in.nextInt();

            if ((l1 | l2 | l3) == 0)
                return;

            double[] inner = new double[3];
            double[] outer = new double[3];
            for (int i = 0; i < 3; i++) {
                inner[i] = in.nextDouble();
                outer[i] = in.nextDouble();
            }

            // Construct the triangle
            Point[] triangle = new Point[3];
            triangle[0] = new Point(0, 0);
            triangle[1] = new Point(0, l1);
            Circle c1 = new Circle(triangle[0], l2);
            Circle c2 = new Circle(triangle[1], l3);
            // This is guaranteed to work
            triangle.add(intersection(c1, c2).get(0));

            boolean fits = layout(triangle, inner, outer);
            if (currCase > 1)
                System.out.println();
            System.out.printf("Triangle number %d:\n", currCase++);
            if (fits) {
                System.out.println("All three stoppers will fit in the triangular space");
            }
            else {
                System.out.println("Stoppers will not fit in the triangular space");
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

class Segment {
    Point p, q;

    public Segment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }
}

class Circle {
    Point c;
    double r;

    public Circle(Point c, double r) {
        this.c = c;
        this.r = r;
    }
}