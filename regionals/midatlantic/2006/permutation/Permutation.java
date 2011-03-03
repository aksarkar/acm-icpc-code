import java.util.*;

/**
   ACM Midatlantic Regional 2006 Problem A
 */
class Permutation {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) {
      int n = in.nextInt();
      if (n == 0)
        return;
      int[] p = new int[n];
      for (int i = 0; i < n; i++) {
        p[i] = in.nextInt();
      }
      int[] r = new int[n];
      for (int i = 0; i < n; i++) {
        r[i] = 0;
      }
      for (int i = 0; i < n; i++) {
        int j = 0;
        while (p[i] > 0 || r[j] > 0) {
          if (r[j] == 0)
            p[i]--;
          j++;
        }
        r[j] = i + 1;
      }
      System.out.print(r[0]);
      for (int i = 1; i < n; i++) {
        System.out.printf(",%d", r[i]);
      }
      System.out.println();
    }
  }
}