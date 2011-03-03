import java.util.*;

class Crypto {
    public static void println(String myString) {
        System.out.println(myString);
        return;
    }

    public static int getClass(char c) {
        if ('a' <= c && c <= 'i') {
            return 1;
        }
        else if ('j' <= c && c <= 'r') {
            return 2;
        }
        else if (('s' <= c && c <= 'z') || c == '_') {
            return 3;
        }
        return 0;
    }

    public static void process() {
        Scanner sc = new Scanner(System.in);
        int k1 = sc.nextInt();
        int k2 = sc.nextInt();
        int k3 = sc.nextInt();
        String str = sc.nextLine();
        char[] orig = new char[str.length()];
        char[] newa = new char[str.length()];
    }

    public static int getPosOfNextChar(String str, int i, int cclass) {
        for(; i < str.length(); i++) {
            if (getClass(str.charAt(i)) == cclass) {
                return new Integer(i);
            }
        }
        for (i = 0; i < str.length(); i++) {
            if (getClass(str.charAt(i)) == cclass) {
                return new Integer(i);
            }
        }
        return 666;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int k1 = sc.nextInt();
            int k2 = sc.nextInt();
            int k3 = sc.nextInt();
            if (k1 == 0 && k2 == 0 && k3 == 0) return;
            String str = sc.nextLine();
            str = sc.nextLine();
            char[] newa = new char[str.length()];
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                int cclass = getClass(c);
                int k = 0;
                switch (cclass) {
                case 1: k = k1; break;
                case 2: k = k2; break;
                case 3: k = k3; break;
                }
                int newPos = i;
				
                for (int j = 0; j < k; j++) {
                    newPos = getPosOfNextChar(str, newPos+1, cclass);
                }		
				
                newa[newPos] = c;
            }
            String result = new String(newa);
            println(result);
        }
    }
}