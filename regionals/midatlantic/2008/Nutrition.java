import java.util.*;

class Nutrition {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            double total = in.nextInt();
            double fat = in.nextInt();
            double carb  = in.nextInt();
            double prot = in.nextInt();
            if (total == 0 && carb == 0 && prot == 0 && fat == 0)
                return;
            
            double min = 4 * (carb + prot - 1) + 9 * (fat - .5);
            double max = 4 * (carb + prot + 1) + 9 * (fat + .5);
            if (min <= total && total < max)
                System.out.println("yes");
            else
                System.out.println("no");
        }
    }
}