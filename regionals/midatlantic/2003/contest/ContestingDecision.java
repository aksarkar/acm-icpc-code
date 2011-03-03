/**
   ACM Midatlantic Regional 2003 Problem A
 */
import java.util.*;

class ContestingDecision {
    public static int countProblems(int[] times) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result += (times[2 * i] > 0 && times[2 * i + 1] > 0) ? 1 : 0;
        }
        return result;
    }

    public static int calcPenalty(int[] times) {
        int penalty = 0;
        for (int i = 0; i < 4; i++) {
            int numSubmissions = times[2 * i];
            int time = times[2 * i + 1];
            if (numSubmissions == 0)
                penalty += 0;
            else if (time == 0)
                penalty += 0;
            else
                penalty += time + 20 * (numSubmissions - 1);
        }
        return penalty;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int minPenalty = 0;
        String winner = "";
        int numSolved = 0;
        int numTeams = s.nextInt();
        for (int i = 0; i < numTeams; i++) {
            String name = s.next();
            int[] times = new int[8];
            for (int j = 0; j < 8; j++) {
                times[j] = s.nextInt();
            }
            int num = ContestingDecision.countProblems(times);
            int penalty = ContestingDecision.calcPenalty(times);
            if (num > numSolved || (num == numSolved && penalty < minPenalty)) {
                minPenalty = penalty;
                winner = name;
                numSolved = num;
            }
        }
        System.out.format("%s %d %d\n", winner, numSolved, minPenalty);
    }
}