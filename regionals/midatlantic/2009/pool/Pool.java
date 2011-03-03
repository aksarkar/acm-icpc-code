import java.util.*;

/**
   ACM Midatlantic Regional Problem F

   Given the locations of a cue ball and a target ball and a required number
   of bounces, determine the shortest distance the cue ball must travel to hit
   the target ball after bouncing the required number of times.

   Reflect the table so paths involving bounces become straight lines. Create a
   grid of reflected/translated tables; then, paths to candidate target balls
   which cross the required number of table boundaries will be equivalent to
   paths which bounce the required number of times. Then, simply find the
   shortest straight-line distance from the cue ball to each of the valid
   candidate balls.
 */
class Pool {
    /**
       Return true iff the path from the cue ball (Point p) to the candidate
       ball (Point q) does not strike another candidate ball (Point r) before
       the required number of bounces.
     */
    static boolean isValid(Point p, Point q, Point r) {
        // Either the three points are not colinear
        return Math.abs((q.x - p.x) * (r.y - p.y) - (q.y - p.y) * (r.x - p.x)) > 1e-7
            // Or q and r are on opposite sides of p
            || (q.x - p.x > 0) == (r.x - p.x < 0)
            && (q.y - p.y > 0) == (r.y - p.y < 0);
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int L = in.nextInt();
            int W = in.nextInt();
            if ((L|W) == 0)
                // Termination condition
                return;
            // Cue ball
            Point C = new Point(in.nextInt(), in.nextInt());
            // Target ball
            Point T = new Point(in.nextInt(), in.nextInt());
            int N = in.nextInt();
            if (N == 0) {
                // Special case: zero bounces
                System.out.format("%.3f\n", C.distance(T));
                continue;
            }
            // Grid of candidate target balls (on reflected/translated tables)
            Point[][] candidate = new Point[2 * N + 1][2 * N + 1];
            double x, y;
            // Calculate the position of the candidate target balls, by
            // appropriately reflecting and translating the x- and
            // y-coordinates of the original target ball.

            // If the number of bounces is even, the original table will have
            // even index and tables with odd indexes will be reflected.
            // Similarily, if the number of bounces is odd, the original table
            // will have odd index and tables with even indexes will be
            // reflected.
            for (int i = 0; i < 2 * N + 1; i++) {
                for (int j = 0; j < 2 * N + 1; j++) {
                    if (i % 2 == N % 2)
                        y = T.y + (N - i) * W;
                    else
                        y = (W - T.y) + (N - i) * W;
                    if (j % 2 == N % 2)
                        x = T.x + (j - N) * L;
                    else
                        x = (L - T.x) + (j - N) * L;
                    candidate[i][j] = new Point(x, y);
                }
            }
            // Find the shortest path
            double min = Double.MAX_VALUE;
            // KISS: iterate over the whole grid
            for (int i = 0; i < 2 * N + 1; i++) {
                for (int j = 0; j < 2 * N + 1; j++) {
                    // The candidate ball is separated by the required number
                    // of bounces if the Manhattan distance (which is equal to
                    // the number of boundaries crossed) between that table and
                    // the original table is that number.
                    if (Math.abs(N - j) + Math.abs(N - i) == N) {
                        // Check that this path does not strike the target ball
                        // (a candidate for which the Manhattan distance is
                        // less than the required number of bounces) before the
                        // required number of bounces
                        boolean valid = true;
                        // KISS: iterate over the whole grid
                        for (int k = 0; valid && k < 2 * N + 1; k++)
                            for (int l = 0; valid && l < 2 * N + 1; l++)
                                if (Math.abs(N - l) + Math.abs(N - k) < N)
                                    valid = isValid(C, candidate[i][j], candidate[k][l]);
                        if (valid)
                            min = Math.min(min, C.distance(candidate[i][j]));
                    }
                }
            }
            System.out.format("%.3f\n", min);
        }
    }
}

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double distance(Point other) {
        // Useful library function
        return Math.hypot(this.x - other.x, this.y - other.y);
    }
}