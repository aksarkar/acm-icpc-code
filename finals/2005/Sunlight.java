import java.io.*;
import java.util.*;

/**
   ACM World Finals 2005 Problem C
*/
class Sunlight {
    final static String INPUTFILE = "sunlight.in";

    final static double EPS = 1e-8;

    // Sunrise in seconds after midnight
    final static int START = (5 * 60 + 37) * 60;

    // Sunset in seconds after midnight
    final static int END = (18 * 60 + 17) * 60;

    /*
      Return the angle between the vector from origin to p and a horizontal
      vector (in the positive x-direction) from origin.
    */
    static double theta(Point p, Point origin, boolean east) {
        // Special cases
        if (Math.abs(p.x - origin.x) < EPS) {
            if (Math.abs(p.y - origin.y) < EPS) {
                // The two points are equal
                return east ? Math.PI : 0;
            }
            // One point is directly above the other
            else if (p.y > origin.y) {
                return Math.PI / 2;
            }
            else {
                // This value needs to be thrown away. If east is true, we're
                // looking for a minimum so return the positive value;
                // otherwise, we're looking for a maximum so return the
                // negative value
                return east ? 3 * Math.PI / 2 : -Math.PI / 2;
            }
        }

        double result = Math.atan((p.y - origin.y) / (p.x - origin.x));
        if (east)
            // Shift the result so it's measured from a horizontal vector in
            // the positive x-direction (rather than in the negative
            // x-direction)
            result += Math.PI;
        return result;
    }

    /*
      Convert seconds after midnight to 24-hour time as a string
    */
    static String toTime(int seconds) {
        int hours = seconds / 3600;
        seconds %= 3600;
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File(INPUTFILE));
        }
        catch (FileNotFoundException e) {
            // Shouldn't get here
            return;
        }

        // Current test case
        int curr = 1;
        while (in.hasNext()) {
            int numBuildings = in.nextInt();

            if (numBuildings == 0)
                // Termination condition
                return;

            if (curr > 1)
                // Hack to handle the weird output scheme
                System.out.println();

            System.out.printf("Apartment Complex: %d\n", curr++);

            // Width of each building
            int width = in.nextInt();

            // Height of each apartment
            int height = in.nextInt();
            
            // Number of apartments per building
            int[] numApartments = new int[numBuildings];

            // Top east corner of each building
            Point[] east = new Point[numBuildings];

            // Top west corner of each building
            Point[] west = new Point[numBuildings];

            // Current x coordinate (scanning left to right)
            int x = 0;
            for (int i = 0; i < numBuildings; i++) {
                numApartments[i] = in.nextInt();
                east[i] = new Point(x, numApartments[i] * height);
                west[i] = new Point(x + width, numApartments[i] * height);

                // Read the next offset
                if (i < numBuildings - 1) {
                    x += width + in.nextInt();
                }
            }
            
            int query = in.nextInt();
            while (query != 0) {
                // Extract the building and floor numbers from the apartment
                // numbers. Subtract one to account for one-indexing
                int building = query % 100 - 1;
                int floor = query / 100 - 1;
                if (building < 0 || building > numBuildings || floor < 0 || 
                    floor >= numApartments[building]) {
                    System.out.printf("\nApartment %d: Does not exist\n", 
                                      query);
                }
                else {
                    // Find the angle to the east for which the sun is blocked.
                    // Take the minimum angle between the bottom-east corner of
                    // the current apartment and the top-west corners of each
                    // of the buildings to the east.
                    Point e = new Point(east[building].x, floor * height);
                    double thetaE = Math.PI;
                    for (int i = 0; i < building; i++)
                        thetaE = Math.min(thetaE, theta(west[i], e, true));

                    // Analogous procedure for the angle to the west
                    Point w = new Point(west[building].x, floor * height);
                    double thetaW = 0;
                    for (int i = building + 1; i < numBuildings; i++)
                        thetaW = Math.max(thetaW, theta(east[i], w, false));

                    // Convert angles to times in seconds
                    int start = (int)(START + (END - START) * 
                                      (Math.PI - thetaE) / Math.PI);
                    int end = (int)(END - (END - START) * thetaW / Math.PI);

                    // Print out times converted to strings
                    System.out.printf("\nApartment %d: %s - %s\n",
                                      query, toTime(start), toTime(end));
                }

                // Read the next query
                query = in.nextInt();
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
