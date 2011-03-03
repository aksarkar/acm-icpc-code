/** 
    ACM Mid-Atlantic 2003 Problem D
*/
import java.util.*;

class Holes {
    final static double EPSILON = 1e-8;

    static boolean pointInPoly(List<Point> poly, Point p) {
        boolean result = false;
        for (int i = 1; i < poly.size(); i++) {
            Point p1 = poly.get(i);
            Point p2 = poly.get(i - 1);
            if (p1.x < p.x && p2.x < p.x)
                // the segment is strictly to the left of the test point, so it
                // can't intersect the ray cast in the positive x direction
                continue;
            else if (p.equals(p2))
                // the point is one of the vertices
                return true;
            else if (Math.abs(p1.y - p.y) < EPSILON &&
                     Math.abs(p2.y - p.y) < EPSILON) {
                // the segment is horizontal
                if (p.x >= Math.min(p1.x, p2.x) && p.x <= Math.max(p1.x, p2.x))
                    // the point is on the segment
                    return true;
                // otherwise, don't count the segment
            }
            else if (p1.y > p.y && p2.y <= p.y ||
                     p2.y > p.y && p1.y <= p.y) {
                // non-horizontal upward edges include start, exclude end;
                // non-horizontal downward edges exclude start, include end
                double det = (p1.x - p.x) * (p2.y - p.y) - (p1.y - p.y) * (p2.x - p.x);
                if (Math.abs(det) < EPSILON)
                    // point is on the translated segment
                    return true;
                if (p2.y < p1.y)
                    det = -det;
                if (det > 0)
                    // segment crosses if the determinant is positive
                    result = !result;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            if (n < 3)
                return;
            double r = in.nextDouble();
            Point center = new Point(in.nextDouble(), in.nextDouble());

            ArrayList<Point> hole = new ArrayList<Point>();
            for (int i = 0; i < n; i++) {
                hole.add(new Point(in.nextDouble(), in.nextDouble()));
            }
            hole.add(hole.get(0));
            hole.add(hole.get(1));

            ArrayList<Point> rev = new ArrayList<Point>(hole);
            Collections.reverse(rev);

            boolean wellFormed = true;
            for (int i = 0; wellFormed && i < n; i++)
                if (Triangle.area(hole.get(i), hole.get(i + 1), hole.get(i + 2)) < 0)
                    wellFormed = false;
            boolean wellFormedRev = true;
            for (int i = 0; wellFormedRev && i < n; i++)
                if (Triangle.area(rev.get(i), rev.get(i + 1), rev.get(i + 2)) < 0)
                    wellFormedRev = false;
            if (!wellFormed && !wellFormedRev) {
                System.out.println("HOLE IS ILL-FORMED");
                continue;
            }
            boolean fits = pointInPoly(hole, center);
            for (int i = 0; fits && i < n; i++)
                if (center.distance(new Segment(hole.get(i), hole.get(i + 1))) < r)
                    fits = false;
            if (fits)
                System.out.println("PEG WILL FIT");
            else
                System.out.println("PEG WILL NOT FIT");
        }
    }
}

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public double distance(Segment s) {
        return Math.abs((s.p.x - s.q.x) * (s.q.y - this.y) - (s.q.x - this.x) * (s.p.y - s.q.y)) / s.length();
    }
}

class Segment {
    Point p, q;

    public Segment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }

    public double length() {
        return this.p.distance(this.q);
    }
}

class Triangle {
    Point p, q, r;

    public Triangle(Point p, Point q, Point r) {
        this.p = p;
        this.q = q;
        this.r = r;
    }

    static double area(Point p, Point q, Point r) {
        return (new Triangle(p, q, r)).area();
    }

    /**
       Calculate the signed area of this triangle.  The signed area is positive
       if the vertices are in counter-clockwise order, negative if they are in
       clockwise order, and 0 if they are colinear.
    */
    public double area() {
        return .5 * (-q.x * p.y + r.x * p.y + p.x * q.y - r.x * q.y - p.x * r.y + q.x * r.y);
    }
}
