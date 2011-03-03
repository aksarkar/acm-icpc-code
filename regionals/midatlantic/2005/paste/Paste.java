import java.util.*;

/**
   ACM Midatlantic Regional Problem D
 */
class Paste {
  static List<Tag> parse(String s) {
    List<Tag> result = new LinkedList<Tag>();
    Deque<Tag> work = new LinkedList<Tag>();
    StringBuilder buff = new StringBuilder();
    int i = 0;
    while (i < s.length()) {
      buff.setLength(0);
      while (i < s.length() && s.charAt(i) != '<') {
        i++;
      }
      if (i == s.length())
        return result;
      Tag curr;
      boolean end = false;
      int j = i + 1;
      if (s.charAt(i + 1) == '/') {
        end = true;
        j = i + 2;
        curr = work.pop();
      }
      else
        curr = new Tag();
      while (s.charAt(i) != '>') {
        buff.append(s.charAt(i));
        i++;
      }
      if (!end) {
        buff.deleteCharAt(0);
        curr.name = buff.toString();
        curr.open = j;
        work.push(curr);
      }
      else {
        curr.close = j;
        result.add(curr);
      }
    }
    return result;
  }
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) {
      int b = in.nextInt() + 1;
      int e = in.nextInt() + 1;
      if ((b | e) == 0)
        return;
      String t = in.nextLine();
      List<Tag> tags = parse(t);
      Collections.sort(tags);
      StringBuilder s = new StringBuilder(t.substring(b, e));
      Deque<Tag> work = new LinkedList<Tag>();
      for (Tag tag : tags) {
        if (tag.close >= b && tag.open < e) {
          work.push(tag);
        }
      }
      while (!work.isEmpty()) {
        Tag curr = work.pop();
        if (curr.open < b)
          s.insert(0, String.format("<%s>", curr.name));
        if (curr.close > e) {
          s.append(String.format("</%s>", curr.name));
        }
      }
      System.out.println(s);
    }
  }
}

class Tag implements Comparable<Tag> {
  int open, close;
  String name;

  public Tag() {
    name = null;
    open = 0;
    close = 0;
  }

  public int compareTo(Tag o) {
    if (this.open < o.open) {
      return -1;
    }
    else if (this.open > o.open) {
      return 1;
    }
    else if (this.close < o.close) {
      return -1;
    }
    else if (this.close > o.close) {
      return 1;
    }
    else {
      return 0;
    }
  }
}