import java.util.*;

class Convert {
  static void get(Scanner in, List<Unit> u1) {
    String[] u = in.nextLine().split("\\s+");
    for (int i = 0; i < u.length; i++) {
      u1[i] = new Unit(u[i]);
    }
    for (int i = 0; i < units.length; i++) {
      double c1 = in.nextDouble();
      double n1 = in.next();
      in.next();
      double c2 = in.nextDouble();
      double n2 = in.nextDouble();
      for (int j = 0; j < u1.length; j++) {
        for (int k = 0; k < u1.length; k++) {
          if (u1[j].name.equals(n1) && u1[k].name.equals(n2)) {
            u1.edges.add(u2);
            u1.weights.add(c2 / c1);
            u2.edges.add(u1);
            u2.weights.add(c1 / c2);
          }
        }
      }
    }
    in.nextLine();
    return u1;
  }
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      List<Unit> units = new ArrayList<Unit>();
      get(in, units);
    }
  }
}

class Unit {
  String name;
  List<Unit> edges;
  List<Double> weights;

  public Unit(String name) {
    this.name = name;
    edges = new ArrayList<Unit>();
    weights = new ArrayList<Unit>();
  }
}