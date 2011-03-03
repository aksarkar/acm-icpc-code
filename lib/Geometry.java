import java.util.*;

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if (o.getClass() != Point.class)
            return false;
        Point p = (Point)o;
        if (Math.abs(this.x - p.x) < Geometry.EPS &&
            Math.abs(this.y - p.y) < Geometry.EPS)
            return true;
        else
            return false;
    }
}

class Line {
    double a, b, c;  // ax + by + c = 0

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Line(Point p, Point q) {
        double slope = (p.y - q.y) / (p.x - q.x);
        if (Double.isInfinite(slope)) {
            this.a = 1;
            this.b = 0;
            this.c = p.x;
        }
        else {
            this.a = -slope;
            this.b = 1;
            this.c = p.y - slope * p.x;
        }
    }

    public boolean equals(Object o) {
        if (o.getClass() != Line.class)
            return false;
        Line l = (Line)o;
        double[] ratios = {this.a / l.a, this.b / l.b, this.c / l.c};
        double r = 0;
        for (int i = 0; i < ratios.length; i++) {
            if (Double.isNaN(ratios[i]))
                continue;
            else if (r == 0)
                r = ratios[i];
            else if (Math.abs(r - ratios[i]) > Geometry.EPS)
                return false;
        }
        return true;
    }
}

class Segment {
    Point p, q;

    public Segment(Point p, Point q) {
        this.p = p;
        this.q = q;
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

class Circle {
    Point center;
    double r;

    public Circle(Point center, double r) {
        this.center = center;
        this.r = r;
    }
}

/**
   Sort points according to their x coordinate.  (Used for closest points.)
 */
class XComparator implements Comparator<Point> {
    public int compare(Point p, Point q) {
        if (Math.abs(p.x - q.x) < Geometry.EPS)
            return 0;
        else if (p.x < q.x)
            return -1;
        else
            return 1;
    }
}

/**
   Sort points according to their y coordinate.  (Used for closest points.)
 */
class YComparator implements Comparator<Point> {
    public int compare(Point p, Point q) {
        if (Math.abs(p.y - q.y) < Geometry.EPS)
            return 0;
        else if (p.y < q.y)
            return -1;
        else
            return 1;
    }
}

/**
   Sort points according to their polar angle from a point with minimum
   y-coordinate.  We use the cotangent of the angle because we can do so easily
   and can guarantee it is monotonically increasing in the domain thanks to our
   choice of minimum point.  (Used for convex hull.)
*/
class HullComparator implements Comparator<Point> {
    Point min;

    public HullComparator(Point min) {
        this.min = min;
    }

    public int compare(Point p, Point q) {
        double cotanP = (p.x - min.x) / (p.y - min.y);
        double cotanQ = (q.x - min.x) / (q.y - min.y);
        if (Math.abs(cotanP - cotanQ) < Geometry.EPS)
            return 0;
        else if (cotanP > cotanQ)
            return -1;
        else
            return 1;
    }
}

class Geometry {
    final static double EPS = 1e-7;

    /**
       Point-point distance
     */
    static double distance(Point p, Point q) {
        return Math.hypot(p.x - q.x, p.y - q.y);
    }

    /**
       Point-line distance
     */
    static double distance(Point p, Segment s) {
        return Math.abs((s.p.x - s.q.x) * (s.q.y - p.y) -
                        (s.q.x - p.x) * (s.p.y - s.q.y)) / length(s);
    }

    static double slope(Line l) {
        return -l.a / l.b;
    }

    static double interceptX(Line l) {
        return -l.c / l.b;
    }

    static double interceptY(Line l) {
        return -l.c / l.a;
    }

    static double slope(Segment s) {
        return (s.p.y - s.q.y) / (s.p.x - s.q.x);
    }

    static double length(Segment s) {
        return distance(s.p, s.q);
    }

    static Point midpoint(Segment s) {
        return new Point((s.p.x + s.q.x) / 2, (s.p.y + s.q.y) / 2);
    }

    /**
       Calculate the signed area of this triangle.  The signed area is positive
       if the vertices are in counter-clockwise order, negative if they are in
       clockwise order, and 0 if they are colinear.
    */
    static public double area(Triangle t) {
        return .5 * (-t.q.x * t.p.y + t.r.x * t.p.y + t.p.x * t.q.y - t.r.x * t.q.y - t.p.x * t.r.y + t.q.x * t.r.y);
    }

    /**
       Line-line intersection
    */
    static public Point intersection(Line l1, Line l2) {
        if (l1.equals(l2))
            return null;
        else if (Math.abs(slope(l1) - slope(l2)) < Geometry.EPS)
            return null;
        else {
            double y = (l1.a * l2.c - l2.a * l1.c) / 
                       (l1.a * l2.b - l2.a * l1.b);
            double x = (l1.c - l1.b * y) / l1.a;
            return new Point(x, y);
        }
    }

    /**
       Return true iff test lies within the minimum bounding rectangle around s
     */
    static public boolean inBounds(Segment s, Point test) {
        return test.x >= Math.min(s.p.x, s.q.x) &&
                test.x <= Math.max(s.p.x, s.q.x) &&
                test.y >= Math.min(s.p.y, s.q.y) &&
                test.y <= Math.max(s.p.y, s.q.y);
    }

    /**
       Segment-segment intersection
     */
    static public Point intersection(Segment s, Segment t) {
        Line l1 = new Line(s.p, s.q);
        Line l2 = new Line(t.p, t.q);
        if (l1.equals(l2))
            return s.p;
        Point i = intersection(l1, l2);
        if (i == null)
            return null;
        else if (inBounds(s, i))
            return i;
        else
            return null;
    }


    /**
       Returns true iff the point is in the specified polygon.  The polygon is
       specified by an array of vertices (in either clockwise or
       counterclockwise order).  The first point should also appear at the end
       (the polygon is closed).  A point on the edge of the polygon is
       considered "in" the polygon.
    */
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
            else if (Math.abs(p1.y - p.y) < Geometry.EPS &&
                     Math.abs(p2.y - p.y) < Geometry.EPS) {
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
                if (Math.abs(det) < Geometry.EPS)
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

    /**
       Return the two closest points in the specified list of points as
       endpoints of a line segment.
     */
    static Segment closestPoints(List<Point> points) {
        if (points.size() < 2)
            return null;
        if (points.size() == 2)
            return new Segment(points.get(0), points.get(1));
        int mid = points.size() / 2;
        ArrayList<Point> copy = new ArrayList<Point>(points);
        Collections.sort(copy, new XComparator());
        ArrayList<Point> left = new ArrayList<Point>();
        for (int i = 0; i < mid; i++)
            left.add(copy.get(i));
        ArrayList<Point> right = new ArrayList<Point>();
        for (int i = mid; i < copy.size(); i++)
            right.add(copy.get(i));
        Segment L = closestPoints(left);
        Segment R = closestPoints(right);
        double dist = Math.min((L == null) ? Double.MAX_VALUE : length(L), 
                               (R == null) ? Double.MAX_VALUE : length(R));
        for (int i = 0; i < copy.size(); i++)
            if (Math.abs(copy.get(i).x - copy.get(mid).x) > dist)
                copy.remove(i--);
        Collections.sort(copy, new YComparator());
        Segment M = new Segment(copy.get(0), copy.get(1));
        for (int i = 0; i < copy.size(); i++) {
            for (int j = 1; j < 6; j++) {
                if (distance(copy.get(i), copy.get(j)) < dist)
                    M = new Segment(copy.get(i), copy.get(j));
            }
        }
        if (L == null && R == null)
            return M;
        else if (L == null && R != null) {
            if (length(R) < length(M))
                return R;
            else
                return M;
        }
        else if (L != null && R == null) {
            if (length(L) < length(M))
                return L;
            else
                return M;
        }
        else
            // shouldn't get here
            return null;
    }

    /**
       Calculate the convex hull of a set of points.  The convex hull is the
       subset of the points which form a convex polygon containing all the
       points.  Returns the hull as a list of points in counter-clockwise order
       (omitting the last point, which is the same as the first).
    */
    static List<Point> convexHull(List<Point> points) {
        if (points.size() < 4)
            return points;

        // find the point with lowest y-coordinate
        Point min = points.get(0);
        for (Point p : points) {
            if (Math.abs(p.y - min.y) < Geometry.EPS && p.x < min.x)
                // break ties using lowest x coordinate
                min = p;
            else if (p.y < min.y)
                min = p;
        }

        ArrayList<Point> copy = new ArrayList<Point>(points);
        copy.remove(min);
        // sort the points according their polar angle from the min
        Collections.sort(copy, new HullComparator(min));
        for (int i = 0; i < copy.size() - 1; i++) {
            double cotan1 = (copy.get(i).x - min.x) / (copy.get(i).y - min.y);
            double cotan2 = (copy.get(i + 1).x - min.x) / (copy.get(i + 1).y - min.y);
            if (Math.abs(cotan1 - cotan2) < Geometry.EPS) {
                // remove points which have the same polar angle but
                // lower distance from the min
                if (Math.abs(copy.get(i).x - copy.get(i + 1).x) < Geometry.EPS
                    && copy.get(i).y > copy.get(i + 1).y)
                    copy.remove(i + 1);
                else if (copy.get(i).x > copy.get(i + 1).x)
                    copy.remove(i + 1);
                else
                    copy.remove(i);
                i--;
            }
        }
        List<Point> hull = new LinkedList<Point>();
        hull.add(0, min);
        hull.add(0, copy.get(0));
        int i = 1;
        // construct the hull
        while (i < copy.size()) {
            if (hull.size() > 1) {
                if (area(new Triangle(hull.get(1), hull.get(0), copy.get(i))) >= 0)
                    hull.add(0, copy.get(i++));
                else
                    hull.remove(0);
            }
            else
                hull.add(copy.get(i++));
        }
        return hull;
    }

    /**
       Triangulate a simple polygon (non-self-intersecting) with no holes.  The
       polygon is specified by a list of vertices in counter-clockwise order.
       Returns a list of non-overlapping triangles whose union is equal to the
       original polygon.
    */
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
}
