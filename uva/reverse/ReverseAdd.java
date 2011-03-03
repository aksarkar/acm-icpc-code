/**
   UVa 10018
   PC 110502
 */
import java.util.Scanner;

public class ReverseAdd {
  public static boolean isPalin(long x) {
    StringBuilder num = new StringBuilder(String.valueOf(x));
    if (num.toString().equals(num.reverse().toString()))
      return true;
    else return false;
  }

  public static long reverseAdd(long x) {
    // This is a really lazy way to do it
    StringBuilder num = new StringBuilder(String.valueOf(x));
    return x + Long.parseLong(num.reverse().toString());
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int num = in.nextInt();
    for (int i = 0; i < num; i++) {
      long curr = in.nextLong();
      int iters = 0;
      do {
        iters++;
        curr = reverseAdd(curr);
      }
      while (!isPalin(curr));
      System.out.format("%d %d\n", iters, curr);
    }
  }
}
