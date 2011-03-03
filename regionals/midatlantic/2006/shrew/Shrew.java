import java.util.*;

/**
   ACM Midatlantic Regional Problem H
 */
class Shrew {
  static boolean compatible(String chars, String j, String m, String f) {
    for (int i = 0; i < chars.length(); i++) {
      if (chars.charAt(i) == 'D') {
        if (j.charAt(i) == '0' && (m.charAt(i) == '1' || f.charAt(i) == '1'))
          return false;
        if (j.charAt(i) == '1' && m.charAt(i) == '0' && f.charAt(i) == '0')
          return false;
      }
      else {
        if (j.charAt(i) == '1' && (m.charAt(i) == '0' || f.charAt(i) == '0'))
          return false;
        if (j.charAt(i) == '0' && m.charAt(i) == '1' && f.charAt(i) == '1')
          return false;
      }
    }
    return true;
  }
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String chars = in.nextLine();
    String line = in.nextLine();
    List<S> adults = new ArrayList<S>();
    do {
      String[] s = line.split("\\s+");
      adults.add(new S(s[0], s[1], s[2]));
      line = in.nextLine();
    }
    while (!line.equals("***"));
    List<S> juvs = new ArrayList<S>();
    line = in.nextLine();
    do {
      String[] s = line.split("\\s+");
      juvs.add(new S(s[0], "", s[1]));
      line = in.nextLine();
    }
    while (!line.equals("***"));
    Collections.sort(adults);
    for (S j : juvs) {
      List<String> parents = new ArrayList<String>();
      for (S mother : adults) {
        if (!mother.sex.equals("F"))
          continue;
        for (S father : adults) {
          if (!father.sex.equals("M"))
            continue;
          if (compatible(chars, j.chars, mother.chars, father.chars))
            parents.add(String.format("%s-%s", mother.name, father.name));
        }
      }
      System.out.printf("%s by ", j.name);
      if (!parents.isEmpty())
        System.out.print(parents.get(0));
      for (int i = 1; i < parents.size(); i++) {
        System.out.printf(" or %s", parents.get(i));
      }
      System.out.println();
    }
  }
}

class S implements Comparable<S> {
  String name, sex, chars;

  public S(String name, String sex, String chars) {
    this.name = name;
    this.sex = sex;
    this.chars = chars;
  }

  public int compareTo(S s) {
    return name.compareTo(s.name);
  }
}