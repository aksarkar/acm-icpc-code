import java.util.*;

/**
   ACM Midatlantic Regional 2006 Problem E
 */
class Marbles {
  static List<State> succ(State s) {
    List<State> result = new LinkedList<State>();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (i == j)
          continue;
        int[] x = Arrays.copyOf(s.x, 3);
        if (x[i] >= x[j]) {
          x[i] -= x[j];
          x[j] += x[j];
          result.add(new State(x));
        }
      }
    }
    return result;
  }

  static boolean goal(State s) {
    return s.x[0] == s.x[1] && s.x[1] == s.x[2];
  }

  static State bfs(State start) {
    Deque<State> queue = new LinkedList<State>();
    Set<State> disc = new HashSet<State>();
    queue.addLast(start);
    disc.add(start);
    while (!queue.isEmpty()) {
      State curr = queue.removeFirst();
      if (goal(curr)) {
        return curr;
      }
      List<State> succ = succ(curr);
      for (State t : succ) {
        if (!disc.contains(t)) {
          t.prev = curr;
          queue.addLast(t);
          disc.add(t);
        }
      }
    }
    return start;
  }
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) {
      State start = new State(new int[] {in.nextInt(),
                                         in.nextInt(),
                                         in.nextInt()});
      State end = bfs(start);
      Deque<String> stack = new LinkedList<String>();
      while (end != null) {
        stack.push(String.format("%4d %4d %4d", end.x[0],
                                 end.x[1], end.x[2]));
        end = end.prev;
      }
      while (!stack.isEmpty()) {
        System.out.println(stack.pop());
      }
      System.out.println("============");
    }
  }
}

class State {
  int[] x;
  State prev;

  public State(int[] x) {
    this.x = x;
    prev = null;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(x);
  }

  public boolean equals(Object o) {
    if (o.getClass() != State.class)
      return false;
    State t = (State)o;
    return Arrays.equals(this.x, t.x);
  }

  public String toString() {
    return Arrays.toString(x);
  }
}