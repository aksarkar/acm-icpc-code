import java.util.*;

/**
   ACM Midatlantic Regional 2009 Problem C

   Implement the given rules for a betting game. Do all calculations in cents
   (integers) to avoid floating-point errors in truncating/rounding
   intermediate/final results.
 */
class Wager {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            long wager = in.nextInt() * 100;
            int m = in.nextInt();
            long winnings = 0;
            boolean loss = false;
            for (int j = 0; j < m; j++) {
                int moneyline = in.nextInt();
                String result = in.next();
                if (loss)
                    continue;
                else if (result.equals("Loss")) {
                    loss = true;
                    continue;
                }
                else if (result.equals("Tie"))
                    continue;
                else if (moneyline > 0)
                    winnings = (1000 * moneyline / 100 * wager) / 1000;
                else
                    winnings = (1000 * 100 / -moneyline * wager) / 1000;
                wager += winnings;
            }
            long total = Math.min(100000000, wager);
            if (loss)
                System.out.println("$0.00");
            else {
                long cents = total % 100;
                total /= 100;
                long hundreds = total % 1000;
                total /= 1000;
                long thousands = total % 1000;
                total /= 1000;
                if (total > 0)
                    System.out.format("$%d,%03d,%03d.%02d\n", total, thousands, hundreds, cents);
                else if (thousands > 0)
                    System.out.format("$%d,%03d.%02d\n", thousands, hundreds, cents);
                else if (hundreds > 0)
                    System.out.format("$%d.%02d\n", hundreds, cents);
                else
                    System.out.format("$0.%02d\n", cents);
            }
        }
    }
}