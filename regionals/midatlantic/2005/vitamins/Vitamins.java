import java.util.*;

/**
   ACM Midatlantic Regional 2005 Problem A
 */
class Vitamins {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> lt = new ArrayList<String>();
        while (in.hasNext()) {
            String[] pieces = in.nextLine().split("\\s+", 4);
            double amt = Double.parseDouble(pieces[0]);
            if (amt < 0)
                continue;
            String unit = pieces[1];
            double reqd = Double.parseDouble(pieces[2]);
            String name = pieces[3];
            double pdv = 100 * amt / reqd;
            if (pdv > 1)
                System.out.format("%s %.1f %s %.0f%%\n", name, amt, unit, pdv);
            else
                lt.add(name);
        }
        System.out.println("Provides no significant amount of:");
        for (String s : lt)
            System.out.println(s);
    }
}
