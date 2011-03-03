import java.util.*;

/**
   ACM Greater New York Regional 2000 Problem C

   Compute digital roots. Because there is no upper limit (i.e. input can
   overflow arbitrary sized containers), compute it using modular arithmetic
   one digit at a time:

   n = x_k * 10^k + x_(k - 1) * 10^(k - 1) + ... + x_1 * 10^1 + x_0 * 10^0
   n mod 9 = (x_k + x_(k - 1) + ... + x_1 + x_0) mod 9
   dr(n) = { n mod 9, n mod 9 != 0
             9        otherwise
 */
class Digital {
    static int root(String n) {
        int result = 0;
        for (int i = 0; i < n.length(); i++) {
            result = (result + (int)n.charAt(i) - (int)'0') % 9;
        }
        return result == 0 ? 9 : result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String n = in.next();
            if (n.equals("0"))
                return;
            System.out.println(root(n));
        }
    }
}