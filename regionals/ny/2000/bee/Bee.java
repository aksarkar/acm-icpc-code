import java.util.*;

/**
   ACM ICPC Greater New York Regional 2000 Problem B

   Given two arbitrary points and rules for navigating a hexagonal grid, find
   the length of a shortest path between the points.

   UNSOLVED
 */
class Bee {
    /**
       Convert the specified point from Cartesian coordinates to hexagonal
       coordinates.

       Multiply by the inverse of the hexToCart change of basis matrix:
       1/r[[1/3 -1/sqrt(3)][0 2/sqrt(3)]]
     */
    static Point cartToHex(Point p, double r) {
        return new Point((p.x / 3 - p.y / Math.sqrt(3)) / r,
                         2 * p.y / (Math.sqrt(3) * r));
    }

    /**
       Convert the specified point from hexagonal coordinates to Cartesian
       coordinates.

       Multiply by the change of basis matrix: r[[3 3/2][0 sqrt(3)/2]]
     */
    static Point hexToCart(Point p, double r) {
        return new Point(r * (3 * p.x + 1.5 * p.y),
                         r * Math.sqrt(3) * p.y / 2);
    }

    /**
       Return the distance between the specified points.
     */
    static double dist(Point p, Point q) {
        return Math.hypot(p.x - q.x, p.y - q.y);
    }

    /**
       Return the Cartesian coordinates of the nearest hexagon center to the
       specified point.
     */
    static Point nearestHex(Point p, double r) {
        Point q = cartToHex(p, r);
        Point[] query = new Point[4];
        // Just try all the neighboring hexagons for simplicity
        query[0] = hexToCart(new Point(Math.floor(q.x), Math.floor(q.y)), r);
        query[1] = hexToCart(new Point(Math.ceil(q.x), Math.floor(q.y)), r);
        query[2] = hexToCart(new Point(Math.floor(q.x), Math.ceil(q.y)), r);
        query[3] = hexToCart(new Point(Math.ceil(q.x), Math.ceil(q.y)), r);
        Point result = query[0];
        double dist = dist(p, result);
        for (int i = 1; i < 3; i++) {
            if (dist(p, query[i]) < dist) {
                dist = dist(p, query[i]);
                result = query[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            double r = in.nextDouble();
            Point a = new Point(in.nextDouble(), in.nextDouble());
            Point b = new Point(in.nextDouble(), in.nextDouble());
            if (r == 0) {
                return;
            }
            Point s = nearestHex(a, r);  // Cartesian
            Point e = nearestHex(b, r);
            Point sh = cartToHex(s, r);  // Hexagonal
            Point eh = cartToHex(e, r);
            int dx = (int)Math.abs(sh.x - eh.x);
            int dy = (int)Math.abs(sh.y - eh.y);
            double dist = 0;
            if ((dx | dy) == 0) {
                // Special case: start and end in the same hexagon
                dist = dist(a, b);
            }
            else if (Math.abs(dy - dx) % 2 == 0) {
                dist = dist(a, s) + dist(b, e) +
                        r * Math.sqrt(3) * (dx + Math.abs(dy - dx) / 2);
            }
            else {
                // TODO
                dist = dist(a, s) + dist(b, e) +
                        r * Math.sqrt(3) * (dx + Math.abs(dy - dx));
            }
            System.out.printf("%.3f\n", dist);
        }
    }
}

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
}