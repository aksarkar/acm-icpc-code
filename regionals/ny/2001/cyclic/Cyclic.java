import java.math.*;
import java.util.*;

/**
   ACM Greater New York Regional 2001 Problem G

   Determine whether a number is cyclical. Generate all cycles and then test
   that each multiple is in the set of pre-computed cycles.
 */
class Cyclic {
    static boolean isCycle(String n, BigInteger product, Set<String> cycles) {
        StringBuilder m = new StringBuilder(product.toString());
        while (m.length() < n.length()) {
            m.insert(0, "0");
        }
        return cycles.contains(m.toString());
    }
  
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String n = in.next();
            Set<String> cycles = new HashSet<String>();
            for (int i = 0; i < n.length(); i++) {
                cycles.add(n.substring(i) + n.substring(0, i));
            }
            boolean isCyclic = true;
            for (int i = 0; isCyclic && i < n.length(); i++) {
                isCyclic = isCycle(n, (new BigInteger(n)).
                                   multiply(BigInteger.valueOf(i + 1)),
                                   cycles);
            }
            if (isCyclic) {
                System.out.printf("%s is cyclic\n", n);
            }
            else {
                System.out.printf("%s is not cyclic\n", n);
            }
        }
    }
}
