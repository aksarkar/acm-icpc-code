/**
   UVa 10213
 */
import java.math.*;
import java.util.*;

class Land {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        final BigInteger SIX = BigInteger.valueOf(6);
        final BigInteger TWENTY_THREE = BigInteger.valueOf(23);
        final BigInteger EIGHTEEN = BigInteger.valueOf(18);
        final BigInteger TWENTY_FOUR = BigInteger.valueOf(24);
        for (int i = 0; i < n; i++) {
            BigInteger m = BigInteger.valueOf(in.nextInt());
            System.out.println(m.pow(4).
                               subtract(m.pow(3).multiply(SIX)).
                               add(m.pow(2).multiply(TWENTY_THREE)).
                               subtract(m.multiply(EIGHTEEN)).
                               add(TWENTY_FOUR).divide(TWENTY_FOUR));
        }
    }
}