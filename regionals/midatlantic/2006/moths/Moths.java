import java.util.*;

/**
   ACM Midatlantic Regional 2006 Problem D (UNSOLVED)
 */
class Moths {
  static double theta(double x, double y) {
    return Math.abs(Math.atan2(x, y)) * 180 / Math.PI;
  }
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) {
      int n = in.nextInt();
      if (n == 0)
        return;
      double cx = in.nextDouble();
      double cy = in.nextDouble();
      double fov = in.nextDouble();
      double[] trees = new double[3 * n];
      for (int i = 0; i < n; i++) {
        trees[3 * i] = theta(in.nextDouble() - cx, in.nextDouble() - cy);
        trees[3 * i + 1] = trees[3 * i] - 360.;
        trees[3 * i + 2] = trees[3 * i] + 360.;
      }
      double minAngle = 0;
      int maxTrees = 0;
      for (int i = 0; i < 3600; i++) {
        double i_ = i / 10.;
        int t = 0;
        for (int j = 0; j < trees.length; j++) {
          if (trees[j] >= (i_ - fov / 2) + .1 && trees[j] <= (i_ + fov / 2) - .1)
            t++;
        }
        if (t > maxTrees) {
          minAngle = i_;
          maxTrees = t;
        }
      }
      System.out.printf("Point the camera at angle %.1f to view %d infested trees.\n",
                        minAngle, maxTrees);
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