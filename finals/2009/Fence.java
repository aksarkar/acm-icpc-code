import java.io.*;
import java.util.*;

/**
   ACM ICPC World Finals 2009 Problem F

   Given a set of locations of trees and a minimum required margin, compute the
   minimum length of fence required to enclose all the trees. We try all
   possible ways of partitioning the trees using dynamic programming, and then
   compute the minimum length of fence required for each partition. This length
   equals the length of the convex hull of the points in the partition plus the
   circumference of a circle with the required margin.
 */
class Fence {
    static final File INPUTFILE = new File("fence.in");
    static final double EPS = 1e-8;

    /**
       Return the subset of the specified list corresponding to the specified
       bit vector.
     */
    static List<Point> getSubset(int subset, List<Point> trees) {
        List<Point> result = new ArrayList<Point>();
        Iterator<Point> iter = trees.iterator();
        while (subset != 0 && iter.hasNext()) {
            Point p = iter.next();
            if ((subset & 1) == 1) {
                result.add(p);
            }
            subset >>>= 1;
        }
        return result;
    }

    /**
       Return the distance between the two specified points.
     */
    static double distance(Point p, Point q) {
        return Math.hypot(p.y - q.y, p.x - q.x);
    }

    /**
       Return the signed area of the triangle with the specified vertices.
     */
    static double area(Point p, Point q, Point r) {
        return .5 * (-q.x * p.y + r.x * p.y + p.x * q.y - r.x * q.y - 
                     p.x * r.y + q.x * r.y);
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
        hull.add(0, min);
        hull.add(0, copy.get(0));
        int i = 1;
        while (i < copy.size()) {
            if (hull.size() > 1) {
                if (area(hull.get(1), hull.get(0), copy.get(i)) >= 0)
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
       Return the length of a single fence which surrounds all trees in the
       specified list with the specified margin M.

       The fence has straight segments corresponding to the convex hull of the
       points and arcs which total to one full circle with radius M. Therefore,
       the length is just the length of segments in the convex hull plus the
       circumference of the circle.
     */
    static double length(List<Point> trees, int M) {
        List<Point> hull = convexHull(trees);
        double length = 2 * Math.PI * M;
        for (int i = 0; i < hull.size(); i++) {
            length += distance(trees.get(i), trees.get((i + 1) % hull.size()));
        }
        return length;
    }

    /**
       Return the minimum length of fence which will surround all trees (not
       necessarily all enclosed together) with the specified margin.
     */
    static double minCost(List<Point> trees, int M) {
        // The last subset to be examined is the one which contains all trees,
        // i.e. (1 << trees.size()) - 1
        int last = 1 << trees.size();

        // Dynamic programming table
        double[] T = new double[last];
        Arrays.fill(T, Double.MAX_VALUE);

        // Base case: zero trees require a zero length fence to enclose
        T[0] = 0;

        for (int k = 1; k <= trees.size(); k++) {
            // Consider subsets with exactly k elements
            for (int i = 0; i < last; i++) {
                // Try all ways of partitioning the set i into a subset j,
                // which will be contained by one fence, and its complement (j
                // ^ i), which will be fenced in an optimal manner whose cost
                // we have already computed.
                for (int j = 1; Integer.bitCount(i) == k && j < last; j++) {
                    if ((j & i) == j) {
                        T[i] = Math.min(T[i], length(getSubset(j, trees), M) + 
                                        T[j ^ i]);
                    }
                }
            }
        }
        return T[last - 1];
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
            int N = in.nextInt();
            int M = in.nextInt();

            if ((N | M) == 0)
                return;

            List<Point> trees = new ArrayList<Point>();
            for (int i = 0; i < N; i++) {
                trees.add(new Point(in.nextDouble(), in.nextDouble()));
            }

            System.out.printf("Case %d: length = %.2f\n", currCase++, 
                              minCost(trees, M));
        }
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
        if (Math.abs(cotanP - cotanQ) < Fence.EPS)
            return 0;
        else if (cotanP > cotanQ)
            return -1;
        else
            return 1;
    }
}

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}