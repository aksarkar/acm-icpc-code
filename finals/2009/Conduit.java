import java.io.*;
import java.util.*;

class Conduit {
    static final File INPUTFILE = new File("conduit.in");
    static final double EPS = 1e-8;

    /**
       Return the distance between the two specified points.
     */
    static double distance(Point p, Point q) {
        return Math.sqrt(Math.pow(p.x - q.x, 2) + Math.pow(p.y - q.y, 2));
    }

    /**
       Return the midpoint of the line segment with the specified endpoints.
     */
    static Point midpoint(Point p, Point q) {
        return new Point((p.x + q.x) / 2, (p.y + q.y) / 2);
    }

    /**
       Return the determinant of the specified matrix.
     */
    static double det(double[][] mat) {
        if (mat.length == 2) {
            return mat[0][0] * mat[1][1] - mat[1][0] * mat[0][1];
        }
        double result = 0;
        int sign = 1;
        for (int i = 0; i < mat.length; i++) {
            double[][] sub = new double[mat.length - 1][mat.length -1];
            for (int k = 1; k < mat.length; k++) {
                for (int j = i + 1; j < mat.length; j++) {
                    sub[j - (i + 1)][k - 1] = mat[j][k];
                }
                for (int j = 0; j < i; j++) {
                    sub[j + (i + 1)][k - 1] = mat[j][k];
                }
            }
            result += sign * det(sub);
            sign *= -1;
        }
        return result;
    }

    /**
       Return the circle which contains all the specified points on its
       circumference. Assume we are given either 2 or 3 points.
     */
    static Circle getCircle(List<Point> points) {
        if (points.size() == 2) {
            // The points lie on a diameter of the circle
            return new Circle(midpoint(points.get(0), points.get(1)), 
                              distance(points.get(0), points.get(1)) / 2);
        }
        else {
            // http://home.att.net/~srschmitt/zenosamples/zs_circle3pts.html
            double[][] mat = new double[3][3];
            for (int i = 0; i < 3; i++) {
                mat[i][0] = points.get(i).x;
                mat[i][1] = points.get(i).y;
                mat[i][2] = 1;
            }
            double m11 = det(mat);
            if (Math.abs(m11) < EPS)
                return null;

            for (int i = 0; i < 3; i++) {
                mat[i][0] = Math.pow(points.get(i).x, 2) + 
                    Math.pow(points.get(i).y, 2);
            }
            double m12 = det(mat);

            for (int i = 0; i < 3; i++) {
                mat[i][1] = points.get(i).x;
            }
            double m13 = det(mat);

            for (int i = 0; i < 3; i++) {
                mat[i][2] = points.get(i).y;
            }
            double m14 = det(mat);

            Point c = new Point(.5 * m12 / m11, -.5 * m13 / m11);
            double r = c.x * c.x + c.y * c.y + m14 / m11;
            return new Circle(c, r);
        }
    }

    /**
       Return true iff the Point p is contained within the Circle c (including
       its circumference).
     */
    static boolean inCircle(Circle c, Point p) {
        return Math.abs(c.r - distance(c.c, p)) < EPS ||
            c.r > distance(c.c, p);
    }

    /**
       Return the subset corresponding to the specified bit vector (as an
       integer).
     */
    static List<Point> getSubset(List<Point> points, int subset) {
        List<Point> result = new LinkedList<Point>();
        Iterator<Point> iter = points.iterator();
        while (iter.hasNext() && subset != 0) {
            Point p = iter.next();
            if ((subset & 1) == 1)
                result.add(p);
            subset >>>= 1;
        }
        return result;
    }

    /**
       Return the circle with minimum radius which contains all the specified
       points.
     */
    static Circle minCircle(List<Point> points) {
        int last = 1 << points.size();
        Circle result = null;
        double minRadius = Double.MAX_VALUE;
        for (int i = 0; i < last; i++) {
            if (Integer.bitCount(i) == 2 || Integer.bitCount(i) == 3) {
                Circle c = getCircle(getSubset(points, i));
                boolean valid = true;
                for (int j = 0; valid && j < points.size(); j++) {
                    valid = inCircle(c, points.get(j));
                }
                if (valid && c.r < minRadius) {
                    minRadius = c.r;
                    result = c;
                }
            }
        }
        return result;
    }

    /**
       Return the minimum diameter of a circle which contains all circles with
       the specified diameters.
     */
    static int minDiam(int[] diameters) {
        return 0;
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
        int[] diameters = new int[4];
        while (in.hasNext()) {
            diameters[0] = in.nextInt();

            if (diameters[0] == 0)
                return;

            diameters[1] = in.nextInt();
            diameters[2] = in.nextInt();
            diameters[3] = in.nextInt();

            System.out.printf("Case %d: %d", currCase++, minDiam(diameters));
        }
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

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}