import java.util.*;

class Ship {
    static int MAX = 20;

    static void backtrack(int[][] cost, int i, int j, int move, int A,
                          int[][] path) {
        if (i == 0 && j == 0) {
            path[i][j] = move;
            return;
        }
        int[][] work = new int[MAX][MAX];
        for (int i_ = 0; i_ < MAX; i_++) {
            for (int j_ = 0; j_ < MAX; j_++) {
                work[i_][j_] = cost[i_][j_];
            }
        }
        for (int i_ = 1; move % A == 0 && i_ < MAX - 1; i_++) {
            for (int j_ = 1; j_ < MAX - 1; j_++) {
                if (cost[i_ - 1][j_] >= Integer.MAX_VALUE / 2 ||
                    cost[i_][j_ - 1] >= Integer.MAX_VALUE / 2 ||
                    cost[i_ + 1][j_] >= Integer.MAX_VALUE / 2 ||
                    cost[i_][j_ + 1] >= Integer.MAX_VALUE / 2) {
                    work[i_][j_] = Integer.MAX_VALUE / 2;
                }
            }
        }
        path[i][j] = move;
        if (i > 0 && cost[i - 1][j] < Integer.MAX_VALUE / 2) {
            backtrack(work, i - 1, j, move + 1, A, path);
        }
        else if (j > 0 && cost[i][j - 1] < Integer.MAX_VALUE / 2) {
            backtrack(work, i, j - 1, move + 1, A, path);
        }
        else if (j < MAX - 1 && cost[i][j + 1] < Integer.MAX_VALUE / 2) {
            backtrack(work, i, j + 1, move + 1, A, path);
        }
        else if (i < MAX - 1 && cost[i + 1][j] < Integer.MAX_VALUE / 2) {
            backtrack(work, i + 1, j, move + 1, A, path);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char[][] grid = new char[MAX][MAX];
        int[][] cost = new int[MAX][MAX];
        int[] w = new int[MAX];
        int N = in.nextInt();
        for (int k = 0; k < N; k++) {
            int L = in.nextInt();
            int A = in.nextInt();
            int M = in.nextInt();
            for (int i = 0; i < MAX; i++) {
                for (int j = 0; j < MAX; j++) {
                    grid[i][j] = '\0';
                    cost[i][j] = Integer.MAX_VALUE / 2;
                    w[i] = 0;
                }
            }
            in.nextLine();
            int si = 0, sj = 0;
            for (int i = 0; i < M; i++) {
                String line = in.nextLine();
                w[i] = line.length();
                for (int j = 0; j < w[i]; j++) {
                    grid[i][j] = line.charAt(j);
                    if (grid[i][j] == 'Y') {
                        si = i;
                        sj = j;
                    }
                }
            }

            for (int j = 0; j < w[0]; j++) {
                cost[0][j] = grid[0][j] == 'F' ? Integer.MAX_VALUE / 2 : j;
            }
            for (int i = 0; i < M; i++) {
                cost[i][0] = grid[i][0] == 'F' ? Integer.MAX_VALUE / 2 : i;
            }
            for (int i = 1; i < M; i++) {
                for (int j = 1; j < w[i]; j++) {
                    cost[i][j] = grid[i][j] != 'F' ?
                            Math.min(cost[i - 1][j], cost[i][j - 1]) + 1 :
                            Integer.MAX_VALUE / 2;
                }
                for (int j = w[i]; j < MAX; j++) {
                    cost[i][j] = Integer.MAX_VALUE / 2;
                }
            }

            System.out.printf("Sinking Ship #%d\n", k + 1);
            if (cost[si][sj] > 40 || cost[si][sj] / A > L) {
                System.out.println("No Path Exists.");
            }
            else {
                int[][] path = new int[MAX][MAX];
                for (int i = 0; i < MAX; i++) {
                    for (int j = 0; j < MAX; j++) {
                        path[i][j] = -1;
                    }
                }
                backtrack(cost, si, sj, 0, A, path);
                for (int i = 0; i < M; i++) {
                    for (int j = 0; j < w[i]; j++) {
                        if (path[i][j] < 0)
                            System.out.printf("  %3s", "X");
                        else {
                            System.out.printf("  %3d", path[i][j]);
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
        }
        System.out.println("All Sets Run.");
    }
}

class Pair {
    int i, j;

    public Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }
}