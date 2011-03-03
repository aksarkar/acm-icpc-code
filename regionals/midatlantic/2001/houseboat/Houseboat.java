/**
   ACM Midatlantic Regional 2001 Problem B
 */
import java.util.*;

class Houseboat {
    public static int erodesYear(double x, double y) {
        int years = 1;
        while (!(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < 
                 Math.sqrt( 100 * years / Math.PI))) {
            years++;
        }
        return years;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        in.nextLine();

        for (int i = 0; i < num; i++) {
            double x = in.nextFloat();
            double y = in.nextFloat();
            int year = Houseboat.erodesYear(x, y);
            System.out.format("Property %d: This property will begin eroding in year %d\n", i+1, year);
        }
        System.out.println("END OF OUTPUT.");
    }
}