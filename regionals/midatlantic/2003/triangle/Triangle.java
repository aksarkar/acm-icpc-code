/**
   ACM Midatlantic Regional 2003 Problem F
*/
import java.util.*;

class Triangle {
  final static double EPS = 1e-3;
  
  static double dist(Point p, Point q) {
    return Math.hypot(p.x - q.x, p.y - q.y);
  }
    
  /**
     Return the angle (in radians) opposite side C, given all three side
     lengths using the law of cosines.
   */
  public static double coslaw(double a, double b, double c) {
    return Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) /
                     (2 * a * b));
  }

  public static String classify(Point[] points) {
    double[] dists = new double[3];
    for (int i = 0; i < 3; i++) {
      dists[i] = dist(points[i], points[(i + 1) % 3]);
    }

    // Check triangle inequality
    for (int i = 0; i < 3; i++) {
      if (dists[i] + dists[(i + 1) % 3] - EPS < dists[(i + 2) % 3]) {
        return "Not a Triangle";
      }
    }

    // Classify by side lengths
    int numEqualPairs = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = i + 1; j < 3; j++) {
        if (Math.abs(dists[i] - dists[j]) < EPS) {
          ++numEqualPairs;
        }
      }
    }
    String lenClass = "Equilateral";
    if (numEqualPairs == 0) {
      lenClass = "Scalene";
    }
    else if (numEqualPairs == 1) {
      lenClass = "Isosceles";
    }

    // Classify by angles
    double[] angles = new double[3];
    for (int i = 0; i < 3; i++) {
      angles[i] = coslaw(dists[i], dists[(i + 1) % 3], dists[(i + 2) %3]);
    }
    Arrays.sort(angles);
    String angleClass = "Acute";
    if (Math.abs(angles[2] - Math.PI / 2) < EPS) {
      angleClass = "Right";
    }
    else if (angles[2] > Math.PI / 2) {
      angleClass = "Obtuse";
    }

    return String.format("%s %s", lenClass, angleClass);
  }

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    String line = s.nextLine();
    do {
      String[] pieces = line.split(" ");
      Point[] triangle = new Point[3];
      for (int i = 0; i < 3; i++) {
        triangle[i] = new Point(Double.parseDouble(pieces[2 * i]),
                                Double.parseDouble(pieces[2 * i + 1]));
      }
      System.out.println(classify(triangle));
      line = s.nextLine();
    }
    while (!line.equals("-1"));
    System.out.println("End of Output");
  }
}

class Point {
  double x, y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
}