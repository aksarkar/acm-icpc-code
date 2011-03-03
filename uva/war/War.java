/**
   UVa 10158
   PC 111005

   UNSOLVED
*/
import java.util.LinkedList;
import java.util.Scanner;

public class War {
  public static boolean[][] friends, enemies;

  public static boolean areFriends(int x, int y) {
    return friends[x][y];
  }

  public static boolean areEnemies(int x, int y) {
    return enemies[x][y];
  }

  public static boolean setFriends(int x, int y) {
    if (areFriends(x, y)) return true;
    if (areEnemies(x, y)) return false;

    boolean[][] graph = deepcopy(friends);

    graph[x][y] = true;
    graph[y][x] = true;

    boolean[] visited = new boolean[graph.length];
    for (int i = 0; i < visited.length; i++)
      visited[i] = false;

    LinkedList<Integer> queue = new LinkedList<Integer>();
    queue.add(x);
    while (!queue.isEmpty()) {
      int curr = queue.removeFirst();
      visited[curr] = true;
      if (areEnemies(x, curr) || areEnemies(y, curr)) return false;
      graph[x][curr] = true;
      graph[curr][x] = true;
      graph[y][curr] = true;
      graph[curr][y] = true;
      for (int i = 0; i < graph[curr].length; i++)
        if (graph[curr][i] && !visited[i]) queue.add(i);
    }

    for (int i = 0; i < enemies[y].length; i++) {
      if (areEnemies(y, i) && !setEnemies(x, i)) return false;
    }
    for (int i = 0; i < enemies[x].length; i++) {
      if (areEnemies(x, i) && !setEnemies(y, i)) return false;
    }
    friends = graph;
    return true;
  }

  public static boolean setEnemies(int x, int y) {
    if (areEnemies(x, y)) return true;
    if (areFriends(x, y)) return false;

    enemies[x][y] = true;
    enemies[y][x] = true;

    for (int i = 0; i < enemies[y].length; i++) {
      if (areEnemies(y, i) && !setFriends(x, i)) {
        enemies[x][y] = false;
        enemies[y][x] = false;
        return false;
      }
      for (int j = 0; j < friends[y].length; j++) {
        if (j != y && areFriends(y, j) && areEnemies(y, i) && !setEnemies(j, i)) {
          enemies[x][y] = false;
          enemies[y][x] = false;
          return false;
        }
      }
    }
    for (int i = 0; i < enemies[x].length; i++) {
      if (areEnemies(x, i) && !setFriends(y, i)) {
        enemies[x][y] = false;
        enemies[y][x] = false;
        return false;
      }
      for (int j = 0; j < friends[x].length; j++) {
        if (j != x && areFriends(x, j) && areEnemies(x, i) && !setEnemies(j, i)) {
          enemies[x][y] = false;
          enemies[y][x] = false;
          return false;
        }
      }
    }
    return true;
  }

  public static boolean[][] deepcopy(boolean[][] graph) {
    boolean[][] result = new boolean[graph.length][graph.length];
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph.length; j++) {
        result[i][j] = graph[i][j];
      }
    }
    return result;
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    friends = new boolean[n][n];
    enemies = new boolean[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        friends[i][j] = (i == j);
        enemies[i][j] = false;
      }
    }

    while (in.hasNextInt()) {
      int operation = in.nextInt();
      int x = in.nextInt();
      int y = in.nextInt();
      if (operation == 0 && x == 0 && y == 0)
        return;
      else if (operation == 1) {
        if (!setFriends(x, y)) System.out.println(-1);
      }
      else if (operation == 2) {
        if (!setEnemies(x, y)) System.out.println(-1);
      }
      else if (operation == 3) {
        System.out.println((areFriends(x, y) ? 1 : 0));
      }
      else {
        System.out.println((areEnemies(x, y) ? 1 : 0));
      }
    }
  }
}
