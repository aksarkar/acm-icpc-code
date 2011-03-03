/**
   Southwestern European Regional 1996 Problem D
 */
import java.util.*;

class Hexagon {
    public static ArrayList<Integer> findMaxScore(String[] values) {
        ArrayList<Integer> vals = new ArrayList<Integer>();
        for (String s : values)
            vals.add(Integer.parseInt(s));
        Collections.sort(vals);

        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(8 * vals.get(2) + 6 * vals.get(1) + 5 * vals.get(0));
        result.add(7 * (vals.get(2) + vals.get(1)) + 5 * vals.get(0));

        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        in.nextLine();
        for (int i = 0; i < num; i++) {
            String[] dir1 = in.nextLine().split("\\s+");
            String[] dir2 = in.nextLine().split("\\s+");
            String[] dir3 = in.nextLine().split("\\s+");

            ArrayList<Integer> scores1 = findMaxScore(dir1);
            ArrayList<Integer> scores2 = findMaxScore(dir2);
            ArrayList<Integer> scores3 = findMaxScore(dir3);

            ArrayList<Integer> result = new ArrayList<Integer>();
            result.add(scores1.get(0) + scores2.get(1) + scores3.get(1));
            result.add(scores2.get(0) + scores3.get(1) + scores1.get(1));
            result.add(scores3.get(0) + scores1.get(1) + scores2.get(1));

            Collections.sort(result);

            System.out.format("Test #%d\n%d\n\n", i + 1, 
                              result.get(result.size() - 1));
        }
    }
}