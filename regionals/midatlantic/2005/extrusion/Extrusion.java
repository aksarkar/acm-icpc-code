import java.util.*;

/**
   ACM Midatlantic Regional 2005 Problem C
 */
class Extrusion {
    final static double EPS = 1e-8;
            
    static public double area(Triangle t) {
        return .5 * (-t.q.x * t.p.y + t.r.x * t.p.y + t.p.x * t.q.y - t.r.x * t.q.y - t.p.x * t.r.y + t.q.x * t.r.y);
    }

    static boolean pointInPoly(Point[] poly, Point p) {
        boolean result = false;
        for (int i = 1; i < poly.length; i++) {
            Point p1 = poly[i];
            Point p2 = poly[i - 1];
            if (p1.x < p.x && p2.x < p.x)
                // the segment is strictly to the left of the test point, so it
                // can't intersect the ray cast in the positive x direction
                continue;
            else if (p.equals(p2))
                // the point is one of the vertices
                return true;
            else if (Math.abs(p1.y - p.y) < EPS &&
                     Math.abs(p2.y - p.y) < EPS) {
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
                if (Math.abs(det) < EPS)
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

    static List<Triangle> triangulate(List<Point> points) {
        if (points.size() < 3)
            return null;
        List<Point> copy = new ArrayList<Point>(points);
        List<Triangle> result = new ArrayList<Triangle>();
        while (copy.size() > 2) {
            Triangle t = new Triangle(copy.get(0), copy.get(1), copy.get(2));
            boolean isEar = (area(t) >= 0);
            for (int i = 3; isEar && i < copy.size(); i++) {
                if (pointInPoly(new Point[] {t.p, t.q, t.r, t.p}, copy.get(i)))
                    isEar = false;
            }
            if (isEar) {
                result.add(t);
                copy.remove(1);
            }
            else {
                Point p = copy.remove(0);
                copy.add(p);
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
            double a = 0;
            List<Point> poly = new ArrayList<Point>();
            for (int i = 0; i < n; i++) {
                Point next = new Point(in.nextDouble(), in.nextDouble());
                if (poly.contains(next)) {
                    Collections.reverse(poly);
                    List<Triangle> tri = triangulate(poly);
                    for (Triangle t : tri)
                        a += area(t);
                    while (poly.size() > 1)
                        poly.remove(0);
                }
                else
                    poly.add(next);
            }
            Collections.reverse(poly);
            List<Triangle> tri = triangulate(poly);
            for (Triangle t : tri)
                a += area(t);
            double v = in.nextDouble();
            double l = v / a;
            System.out.format("BAR LENGTH: %.2f\n", l);
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

class Triangle {
    Point p, q, r;

        public Triangle(Point p, Point q, Point r) {
        this.p = p;
        this.q = q;
        this.r = r;
    }
}
