import java.util.*;

class Balloon {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) {
      int N = in.nextInt();
      int A = in.nextInt();
      int B = in.nextInt();
      if ((N | A | B) == 0)
        return;
      List<Team> teams = new ArrayList<Team>();
      for (int i = 0; i < N; ++i) {
        teams.add(new Team(in.nextInt(), in.nextInt(), in.nextInt()));
      }
      Collections.sort(teams);
      int d = 0;
      for (Team t : teams) {
        int na = 0, nb = 0;
        if (t.a < t.b) {
          na = Math.min(t.k, A);
          nb = t.k - na;
        }
        else {
          nb = Math.min(t.k, B);
          na = t.k - nb;
        }
        d += na * t.a + nb * t.b;
        A -= na;
        B -= nb;
      }
      System.out.println(d);
    }
  }
}

class Team implements Comparable<Team> {
  int k, a, b;

  public Team(int k, int a, int b) {
    this.k = k;
    this.a = a;
    this.b = b;
  }

  public int compareTo(Team other) {
    int d = Math.abs(this.a - this.b);
    int e = Math.abs(other.a - other.b);
    if (d < e)
      return 1;
    else if (d > e)
      return -1;
    else
      return 0;
  }
}