import java.util.*;

/**
   ACM Midatlantic Regional 2009 Problem H

   Given a set of words, find the longest shortest path between any two pairs
   of words, where each step in the path consists of one insertion, deletion,
   or substitution.

   The Levenshtein distance is the minimum number of insertions, deletions, and
   substitutions required to change one word into another; we use a well-known
   dynamic programming algorithm to calculate it. 

   We represent the paths between words as an undirected graph, where edges are
   unweighted (have weight 1), and there is an edge between two words (nodes)
   iff the Levenshtein distance between them is 1. Then, use the Floyd-Warshall
   algorithm to calculate the shortest path between all pairs of nodes, as the
   number of nodes is not prohibitively large.
 */
class WordChain {
    /**
       Return the Levenshtein distance between s and t
     */
    static int distance(String s, String t) {
        int[][] T = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i < s.length() + 1; i++)
            T[i][0] = i;
        for (int j = 0; j < t.length() + 1; j++)
            T[0][j] = j;
        for (int i = 1; i < s.length() + 1; i++) {
            for (int j = 1; j < t.length() + 1; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1))
                    T[i][j] = Math.min(T[i - 1][j - 1], Math.min(T[i][j - 1], T[i - 1][j]) + 1);
                else
                    T[i][j] = Math.min(T[i - 1][j - 1], Math.min(T[i][j - 1], T[i - 1][j])) + 1;
            }
        }
        return T[s.length()][t.length()];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            if (n == 0)
                return;
            String[] words = new String[n];
            // Undirected graph as an adjacency matrix
            int[][] G = new int[n][n];
            for (int i = 0; i < n; i++)
                words[i] = in.next();
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (distance(words[i], words[j]) == 1) {
                        G[i][j] = 1;
                        G[j][i] = 1;
                    }
                    else {
                        // We are guaranteed the longest path will have length
                        // at most n (it will include every word). Use n + 1
                        // instead of infinity to denote the absence of an
                        // edge to avoid overflow in Floyd-Warshall
                        G[i][j] = n + 1;
                        G[j][i] = n + 1;
                    }
                }
            }
            // Floyd-Warshall algorithm: all-pairs shortest paths
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        G[i][j] = Math.min(G[i][j], G[i][k] + G[k][j]);

            // Find the longest shortest path
            int max = 0;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (G[i][j] < n + 1 && G[i][j] > max)
                        max = G[i][j];
            // The path length we've calculated counts edges, but we must
            // output the number of nodes in the path.
            System.out.println(max + 1);
        }
    }
}