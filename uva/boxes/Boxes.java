/**
   UVa 10215
   PC 111306
 */
import java.util.*;

public class Boxes {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextDouble()) {
      double length = in.nextDouble();
      double width = in.nextDouble();
      double a = 12;
      double b = -(4 * length + 4 * width);
      double c = length * width;
      double mincut = Math.min(width / 2, length / 2);
      double maxcut = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
      System.out.format("%.3f %.3f %.3f\n", maxcut, 0f, mincut);
    }
  }
}
