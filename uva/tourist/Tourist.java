/**
   UVa 10099
   PC 110903
*/
import java.util.Scanner;

class Tourist {
  /**
     Max-flow min-cut: modified Floyd-Warshall algorithm.
  */
  public static int floydWarshall(int[][] table, int start, int end) {
    for (int k = 0; k < table.length; k++) {
      for (int i = 0; i < table.length; i++) {
        for (int j = 0; j < table.length; j++) {
          table[i][j] = Math.max(table[j][i], Math.min(table[i][k], table[k][j]));
        }
      }
    }
    return table[start][end];
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int scenario = 0;
    while (in.hasNext()) {
      scenario++;
      int n = in.nextInt();
      int r = in.nextInt();
      if (n == 0 && r == 0) return;

      int[][] table = new int[n][n];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          table[i][j] = 0;
        }
      }
      for (int i = 0; i < r; i++) {
        int c1 = in.nextInt() - 1;
        int c2 = in.nextInt() - 1;
        int weight = in.nextInt();
        table[c1][c2] = weight;
        table[c2][c1] = weight;
      }

      int start = in.nextInt() - 1;
      int end = in.nextInt() - 1;
      int numPeople = in.nextInt();
      int maxPeople = floydWarshall(table, start, end) - 1;
      int quotient = numPeople / maxPeople;
      int remainder = numPeople % maxPeople;
      int soln = (remainder == 0) ? quotient : quotient + 1;

      System.out.format("Scenario #%d\n", scenario);
      System.out.format("Minimum number of trips = %d\n\n", soln);
    }
  }
}
