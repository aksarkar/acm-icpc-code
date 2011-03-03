/**
   UVa 10198
   PC 110603

   Simple dynamic programming algorithm. The number of ways to write numbers
   which sum up to n is the sum of the number of ways to write n - 1 followed
   by a 1, n - 2 followed by a 2, n - 3 followed by a 3, and n - 1 followed by
   a 4.
 */
import java.math.BigInteger;
import java.util.Scanner;

public class Counting {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    // Precompute everything; this will overflow a long
    BigInteger[] table = new BigInteger[1000];
    table[0] = BigInteger.valueOf(2);
    table[1] = BigInteger.valueOf(5);
    table[2] = BigInteger.valueOf(13);
    for (int i = 3; i < table.length; i++)
      table[i] = table[i] = table[i - 1].multiply(BigInteger.valueOf(2)).add(table[i - 2])
          .add(table[i - 3]);

    while (s.hasNextInt()) {
      int num = s.nextInt();
      System.out.println(table[num - 1]);
    }
  }
}
