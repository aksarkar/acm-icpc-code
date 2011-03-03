import java.util.*;

/**
   ACM Midatlantic Regional 2009 Problem A

   Given a triangle, and two line segments, construct a parallelogram with
   equal area by using the required line segment as the base and a point on the
   other line segment as a vertex.
 */
class Euclid {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double[] x = new double[6];
        double[] y = new double[6];
        while (in.hasNext()) {
            boolean nonzero = false;
            for (int i = 0; i < 6; i++) {
                x[i] = in.nextDouble();
                y[i] = in.nextDouble();
                if (x[i] != 0 || y[i] != 0)
                    nonzero = true;
            }
            if (!nonzero)
                // Termination condition
                return;
            // Three points determine two vectors with a common point. The area
            // of the triangle defined by those three points is half the cross
            // product of these vectors.
            double area = .5 * Math.abs((x[5] - x[3]) * (y[4] - y[3]) - (y[5] - y[3]) * (x[4] - x[3]));
            // The length of the base of the parallelogram
            double l = Math.hypot(x[1] - x[0], y[1] - y[0]);
            // The required height of the parallelogram
            double h = area / l;
            // The distance from the end of the segment to the base of the
            // parallelogram is the magnitude of that vector projected onto a
            // vector perpendicular to the base
            double CtoAB = Math.abs((x[0] - x[1]) * (y[1] - y[2]) - (x[1] - x[2]) * (y[0] - y[1])) / l;
            // The length of the segment
            double AC = Math.hypot(x[2] - x[1], y[2] - y[1]);
            // The ratio of the side of the parallelogram to the whole segment
            // equals the ratio of the height of the parallelogram to the
            // distance from the endpoint to the base
            double AH = h / CtoAB * AC;
            System.out.format("%.3f %.3f %.3f %.3f\n",
                              x[1] + AH / AC * (x[2] - x[0]),
                              y[1] + AH / AC * (y[2] - y[0]),
                              x[0] + AH / AC * (x[2] - x[0]),
                              y[0] + AH / AC * (y[2] - y[0]));
        }
    }
}
