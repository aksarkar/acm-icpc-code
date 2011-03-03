/**
   Pacific Northwest Regional 1998 Problem 5
 */
import java.util.*;

class GourmetClub {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HashSet<String> tables = new HashSet<String>();
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            String[] pieces = in.nextLine().split("\\s+");
            for (String s : pieces)
                tables.add(s);
        }
        String[] last = in.nextLine().split("\\s+");
        
        Permutation p = new Permutation();
        while (true) {
            ArrayList<String> candidate = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                if (!p.next())
                    return;
                int[] index = p.current;
                char[] t = new char[4];
                for (int j = 0; j < 4; j++) {
                    t[j] = last[j].charAt(index[j]);
                }
                candidate.add(String.valueOf(t));
            }
            boolean works = true;
            for (String s : candidate)
                works = !tables.contains(s);
            if (works == true)
                result.addAll(candidate);
            if (result.size() == 8) {
                System.out.println(result);
                return;
            }
        }
    }
}

class Permutation {
    int[] current = {0, 0, 0, 0};

    public boolean next() {
        int i = 0;
        while (this.current[i] == 3) {
            this.current[i] = 0;
            i++;
        }
        if (i == 3) return false;
        this.current[i]++;
        return true;
    }
}