/**
   ACM Midatlantic Regional 1996 Problem F
 */
import java.util.*;

class Perfection {
    public static void getType(String s) {
        int x = Integer.parseInt(s);
        int sum = 0;
        for (int i = 1; i < x; i++) {
            if (x % i == 0)
                sum += i;
        }
        String type;
        if (sum == x)
            type = " PERFECT";
        else if (sum > x)
            type = " ABUNDANT";
        else
            type = " DEFICIENT";
        System.out.println(Perfection.padString(s) + type);
    }
    
    public static String padString(String s) {
        StringBuilder builder = new StringBuilder(s);
        int numSpaces = 5 - s.length();
        for (int i = 0; i < numSpaces; i++)
            builder.insert(0," ");
        return builder.toString();
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String line = s.nextLine();
        String[] pieces = line.split("\\s+");
        
        System.out.println("PERFECTION OUTPUT");
        for (int i = 0; i < pieces.length - 1; i++)
            Perfection.getType(pieces[i]);
        System.out.println("END OF OUTPUT");
    }
}