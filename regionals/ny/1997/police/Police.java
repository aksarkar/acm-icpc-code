import java.util.*;

/**
   ACM Greater New York Regional 1997 Problem B
   
   Given the locations of pieces of evidence, calculate the minimum length of
   police tape to enclose all of them. This is equivalent to finding the
   perimeter of the convex hull of the set of points.
 */
class Police {
    // Threshold for floating-point comparisons
    final static double EPS = 1e-8;

    /**
       Return the signed area of the triangle with specified vertices.

       This is equivalent to 1/2 (p - q) x (r - q), where "-" denotes vector
       subtraction and "x" denotes the cross product.
     */
    static double area(Point p, Point q, Point r) {
        return .5 * (-q.x * p.y + r.x * p.y + p.x * q.y - r.x * q.y - p.x * r.y + q.x * r.y);
    }

    /**
       Return the distance between the specified points
     */
    static double dist(Point p, Point q) {
        // Useful library function
        return Math.hypot(p.x - q.x, p.y - q.y);
    }

    /**
       Convex hull: Graham scan.

       The convex hull of a set of points is the smallest convex polygon which
       contains all the points. Its vertices must be a subset of the points
       (otherwise, it wouldn't be the smallest such polygon). The hull is
       stored as a list of points in counter-clockwise order (omitting the last
       point, which is the same as the first).
     */
    static List<Point> convexHull(List<Point> points) {
        if (points.size() < 4)
            return points;

        // Find the point with lowest y-coordinate
        Point min = points.get(0);
        for (Point p : points) {
            // Don't test that two floating-point numbers are equal (you won't
            // always get the right answer). Instead, compare their absolute
            // difference to a threshold. We need to do this test first because
            // it's possible p.y is infinitesimally smaller than min.y but p.x
            // > min.x, in which case we don't want to use p as the min point.
            if (Math.abs(p.y - min.y) < EPS && p.x < min.x)
                // Break ties using lowest x coordinate
                min = p;
            else if (p.y < min.y)
                min = p;
        }

        // Copy the list (because we will destructively modify it)
        List<Point> copy = new ArrayList<Point>(points);
        copy.remove(min);

        // Sort the points according their polar angle from the min
        Collections.sort(copy, new HullComparator(min));

        // Construct the hull
        List<Point> hull = new LinkedList<Point>();
        hull.add(0, min);          // u is always index 1
        hull.add(0, copy.get(0));  // v is always index 0
        int i = 1;                 // w (index in copy)
        while (i < copy.size()) {
            if (hull.size() > 1) {
                if (area(hull.get(1), hull.get(0), copy.get(i)) >= 0)
                    // Positive area means left turn; zero area means colinear.
                    // In either case, add w to the hull, set u = v, v = w
                    hull.add(0, copy.get(i++));
                else
                    // Backtrack: remove v, set v = u, u = predecessor of u
                    hull.remove(0);
            }
            else {
                // Account for backtracking all the way to the first vertex
                hull.add(copy.get(i++));
            }
        }
        return hull;
    }

    /**
       Return the perimeter of the convex polygon with specified vertices.
     */
    static double perimeter(List<Point> points) {
        double result = 0;
        for (int i = 0; i < points.size(); i++) {
            // By using (i + 1) % points.size(), we avoid having to special
            // case the last segment (from the last point to the first point).
            result += dist(points.get(i), points.get((i + 1) % points.size()));
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            int M = in.nextInt();
            List<Point> points = new ArrayList<Point>(M);
            for (int j = 0; j < M; j++) {
                points.add(new Point(in.nextInt(), in.nextInt()));
            }
            System.out.printf("CRIME SCENE %d:   %.3f feet\n", i + 1, 
                              perimeter(convexHull(points)));
        }
        System.out.println("END OF OUTPUT");
    }
}

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class HullComparator implements Comparator<Point> {
    Point min;

    public HullComparator(Point min) {
        this.min = min;
    }

    public int compare(Point p, Point q) {
        // Trigonometry trick
        double cotanP = (p.x - min.x) / (p.y - min.y);
        double cotanQ = (q.x - min.x) / (q.y - min.y);
        // Test floating-point equality by comparing the absolute difference to
        // a threshold.
        if (Math.abs(cotanP - cotanQ) < Police.EPS)
            return 0;
        else if (cotanP > cotanQ)
            return -1;
        else
            return 1;
    }
}
