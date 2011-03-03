/**
   ACM Midatlantic Regional 2002 Problem B
 */
import java.util.*;

class Spelling {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numWords = in.nextInt();
        in.nextLine();
        HashSet<String> dict = new HashSet<String>();
        for (int i = 0; i < numWords; i++) {
            dict.add(in.nextLine());
        }

        int numEmails = in.nextInt();
        in.nextLine();
        for (int i = 0; i < numEmails; i++) {
            boolean correct = true;
            ArrayList<String> miss = new ArrayList<String>();
            while (true) {
                String word = in.nextLine();
                if (word.equals("-1"))
                    break;
                if (!dict.contains(word)) {
                    correct = false;
                    miss.add(word);
                }
            }

            if (correct) {
                System.out.format("Email %d is spelled correctly.\n", i+1);
            }
            else {
                System.out.format("Email %d is not spelled correctly.\n", i+1);
                for (String s : miss)
                    System.out.println(s);
            }
        }

        System.out.println("End of Output");
    }
}