import java.util.*;

/**
   ACM Midatlantic Regional 1998 Problem 1

   Given unlabeled golf scores and the rules by which the players change order,
   print labeled scores. On a given hole, players shoot in order of increasing
   score on the previous hole. To handle ties, whoever shot earlier on the
   previous hole goes first. In other words, use a stable sort to sort the
   players on the previous hole to get the order at the current hole.
 */
class Golf {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            // Pairs of names and scores (used for sorting)
            Pair[] players = new Pair[4];
            // The original order (used for output)
            Pair[] order = new Pair[4];
            // Maximum length of the names (used for output)
            int maxlen = 0;

            for (int i = 0; i < 4; i++) {
                players[i] = new Pair();
                players[i].name = in.next();
                order[i] = players[i];
                maxlen = Math.max(maxlen, players[i].name.length());
            }

            // Pad the players' names appropriately using spaces
            char[][] work = new char[4][maxlen];
            for (int i = 0; i < 4; i++) {
                int numspaces = maxlen - players[i].name.length();
                for (int j = 0; j < numspaces; j++) {
                    work[i][j] = ' ';
                }
                for (int j = numspaces; j < maxlen; j++) {
                    work[i][j] = players[i].name.charAt(j - numspaces);
                }
            }

            // Print the names vertically
            for (int j = 0; j < maxlen; j++) {
                System.out.printf("%8s", "");
                for (int i = 0; i < 4; i++) {
                    System.out.printf("%8c", work[i][j]);
                }
                System.out.println();
            }

            // Print a line of dashes
            for (int i = 0; i < 8 * 5; i++)
                System.out.print('-');
            System.out.println();

            // For each hole...
            for (int i = 0; i < 18; i++) {
                // Get the next line of scores in sorted order
                for (int j = 0; j < 4; j++) {
                    players[j].score = in.nextInt();
                    players[j].totalScore += players[j].score;
                }
                // Print the score line in the original order
                System.out.printf("%8d", i + 1);
                for (int j = 0; j < 4; j++) {
                    System.out.printf("%8d", order[j].score);
                }
                System.out.println();
                // Stable sort
                Arrays.sort(players);
            }

            // Print a line of dashes
            for (int i = 0; i < 8 * 5; i++)
                System.out.print('-');
            System.out.println();

            // Print the total scores in the original order
            System.out.printf("%8s", "Total");
            for (int i = 0; i < 4; i++) {
                System.out.printf("%8d", order[i].totalScore);
            }
            System.out.println();
        }
    }
}

class Pair implements Comparable<Pair> {
    String name;
    int score;
    int totalScore;

    public int compareTo(Pair other) {
        if (this.score < other.score)
            return -1;
        else if (this.score > other.score)
            return 1;
        else
            return 0;
    }
}
