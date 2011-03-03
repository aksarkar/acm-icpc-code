/**
   ACM Midatlantic Regional 1996 Problem H
 */
import java.util.*;
import java.util.regex.*;

class Slurpys {
    static Pattern slu, sli1, sli2, _slurpy;

    static {
        slu = Pattern.compile("([DE]F+)+G");
        sli1 = Pattern.compile("A(H|(([DE]F+)+G)C)");
        sli2 = Pattern.compile("AB(\\w+)C");
        _slurpy = Pattern.compile("(\\w+[CH])(\\w+G)");
    }

    public static boolean isSlump(String s) {
        Matcher slump = slu.matcher(s);
        return slump.matches();
    }

    public static boolean isSlimp(String s) {
        Matcher slimpB = sli1.matcher(s);
        Matcher slimpA = sli2.matcher(s);
        if (slimpB.matches()) 
            return true;
        else if (slimpA.matches()) {
            String t = slimpA.group(1);
            return isSlimp(t);
        }
        else
            return false;
    }

    public static String isSlurpy(String s) {
        Matcher slurpy = _slurpy.matcher(s);
        if (slurpy.matches()) {
            String part1 = slurpy.group(1);
            String part2 = slurpy.group(2);
            if (Slurpys.isSlimp(part1) && Slurpys.isSlump(part2))
                return "YES";
            else
                return "NO";
        }
        else
            return "NO";
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = Integer.parseInt(s.nextLine());
        ArrayList<String> answers = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            String t = s.nextLine();
            answers.add(isSlurpy(t));
        }
        System.out.println("SLURPYS OUTPUT");
        for (String a : answers)
            System.out.println(a);
        System.out.println("END OF OUTPUT");
    }
}