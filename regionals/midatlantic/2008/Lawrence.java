import java.util.*;

class Lawrence {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n == 0 && m == 0)
                return;

            int[] depots = new int[n];
            for (int i = 0; i < n; i++)
                depots[i] = in.nextInt();

            int[][] table = new int[m][n - 1];
            for (int i = 0; i < m - 1; i++)
                for (int j = 0; j < n - 1; j++)
                    table[i][j] = 0;
                
            int value = 0;
            for (int i = 0; i < n - 1; i++)
                for (int j = i + 1; j < n; j++)
                    value += depots[i] * depots[j];

            for (int j = 0; j < n - 1; j++)
                table[0][j] = value;
        }
    }
}
