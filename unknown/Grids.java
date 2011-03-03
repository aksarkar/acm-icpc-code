import java.util.*;

class Grids {
    public static int calcPerim(String[][] grid, int cols, int rows, 
                            int y, int x) {
        if (y < 0 || y > rows - 1 || x < 0 || x > cols - 1) {
            return 0;
        }
        if (!grid[y][x].equals("X")) {
            return 0;
        }
        int perim = 4;
        perim -= (x > 0 && !grid[y][x - 1].equals(".")) ? 1 : 0;
        perim -= (x < cols - 1 && !grid[y][x + 1].equals(".")) ? 1 : 0;
        perim -= (y > 0 && !grid[y - 1][x].equals(".")) ? 1 : 0;
        perim -= (y < rows - 1 && !grid[y + 1][x].equals(".")) ? 1 : 0;
        grid[y][x] = "Y";
        return perim + Grids.calcPerim(grid, cols, rows, y, x + 1)
            + Grids.calcPerim(grid, cols, rows, y + 1, x + 1)
            + Grids.calcPerim(grid, cols, rows, y + 1, x)
            + Grids.calcPerim(grid, cols, rows, y + 1, x - 1)
            + Grids.calcPerim(grid, cols, rows, y, x - 1)
            + Grids.calcPerim(grid, cols, rows, y - 1, x - 1)
            + Grids.calcPerim(grid, cols, rows, y - 1, x)
            + Grids.calcPerim(grid, cols, rows, y - 1, x + 1);
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int rows, cols, y, x;
        while (true) {
            rows = s.nextInt();
            cols = s.nextInt();
            y = s.nextInt();
            x = s.nextInt();
            if (rows == 0 && cols == 0 && y == 0 && x == 0)
                break;
            s.nextLine();
            String[][] grid = new String[rows][cols];
            for (int j = 0; j < rows; j++) {
                String line = s.nextLine();
                for (int i = 0; i < cols; i++)
                    grid[j][i] = String.valueOf(line.charAt(i));
            }
            System.out.println(Grids.calcPerim(grid, cols, rows, y - 1, x - 1));
         }
    }
}