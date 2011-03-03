import java.util.*;

/**
   ACM Midatlantic Regional 2010 Problem A
 */
class Odometer {
  static void next(StringBuffer s) {
    int i = s.length() - 1;
    while (s.charAt(i) == '9') {
      s.setCharAt(i, '0');
      --i;
    }
    if (i >= 0) {
      s.setCharAt(i, (char)(s.charAt(i) + 1));
    }
  }

  static boolean isPalindrome(StringBuffer s) {
    int p = -1, q = s.length();
    while (++p < --q) {
      if (s.charAt(p) != s.charAt(q))
        return false;
    }
    return true;
  }
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) {
      StringBuffer s = new StringBuffer(in.next());
      if (s.toString().equals("0"))
        return;
      int c = 0;
      while (!isPalindrome(s)) {
        c++;
        next(s);
      }
      System.out.println(c);
    }
  }
}