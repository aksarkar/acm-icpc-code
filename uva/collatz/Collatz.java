/**
   UVa 100
   PC 110101
 */
import java.util.HashMap;
import java.util.Scanner;

public class Collatz {
     public static HashMap<Integer, Integer> lengths = new HashMap<Integer, Integer>();
     
     public static int collatz(int n) {
          if (n == 1)
               return 1;
          if (lengths.containsKey(n))
               return lengths.get(n);
          if (n % 2 == 0)
               Collatz.lengths.put(n, collatz(n / 2) + 1);
          else
               Collatz.lengths.put(n, collatz(3 * n + 1) + 1);
          return Collatz.lengths.get(n);
     }
     
     public static void main(String[] args) {
          Scanner in = new Scanner(System.in);
          while (in.hasNext()) {
               int start = in.nextInt();
               int end = in.nextInt();
               boolean switched = false;
               if (start > end) {
                    switched = true;
                    int temp = start;
                    start = end;
                    end = temp;
               }
               int maxcount = 0;
               for (int i = start; i <= end; i++) {
                    int count = Collatz.collatz(i);
                    if (maxcount < count)
                         maxcount = count;
               }
               if (switched)
                    System.out.format("%d %d %d\n", end, start, maxcount);
               else
                    System.out.format("%d %d %d\n", start, end, maxcount);
          }
     }
}
