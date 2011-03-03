import java.util.*;
import java.math.*;

/**
   ACM Midatlantic 2009 Problem G

   Given a pool of characters and an index K, to find the Kth permutation in
   lexicographical (dictionary) order of the characters.
  
   We build the string one character at a time; however, we don't store the
   prefix we've built so far; we just print out characters as we find them. We
   only track the index of the first string which contains the prefix found so
   far. When this equals K, we have finished (with one caveat).

   [Running example: 2 "a"s, 3 "b"s, K = 5]

   In each step, we first want to try extending the current prefix with one
   of each of the remaining types of characters in the pool in turn and then
   calculate the index of the first string containing this new prefix.

   [Initially, our prefix is the empty string and we want to calculate the index
   of the first string which starts with "a" and the first which starts with
   "b".]

   We first calculate how many strings built from the remaining pool of
   characters begin with an "a", how many with a "b", etc. To find the
   number of strings starting with a particular character: fix the first
   character (decrementing the count of that character in the pool) and then
   calculate the number of permutations of the remaining characters in the
   pool. This is the tricky part: some of the characters are of the same
   type (for example, all "a"s are indistinguishable), so we need to account
   for counting some permutations twice. The number of permutations of n
   items, where a of them are of one type, b of them are of another, etc., is
   n! / (a!b!c!...). Here, n is the total number of characters in the pool
   minus one (the character which we fixed), and a, b, c, etc. are the number
   of "a"s, "b"s, "c"s, etc.

   [After fixing an "a" we need to permute 4 characters: 1 "a" and 3 "b"s. The
   number of ways to do this is 4! / (3!1!) = 4. After fixing a "b", we permute
   2 "a"s and 2 "b"s; the number of ways to do it is 4! / (2!2!) = 6.]

   Next, we find the index of the first string which contains each of these
   new prefixes. We know that the strings are sorted in lexicographical
   (dictionary) order, so we consider the prefix extended with an "a", then
   extended with a "b", etc. Because the strings are zero-indexed, the
   number of strings which begin with an "a" is precisely the index of the
   first string which starts with a "b", etc.

   [Initially, the current index is 0 (the first string is at index 0). There
   are 4 strings which begin with an "a" and they have indices 0 <= i < 4. The
   first string starting with a "b" is at index 4; there are 6 such strings and
   they have indices 4 <= i < 10.]

   These indices define a set of non-overlapping intervals. Given a target
   index K, we find in which interval K falls into. Each interval
   corresponds to a single character, so we know what the next character in
   the string is. We remove that character from the pool, update the current
   index, and repeat.

   [K = 5 falls in the interval of strings starting with "b", so we know the
   first character is a "b" (and we can output it now). We update the current
   index to 4 (the index of the first string starting with a "b") and the pool
   of characters to 2 "a"s and 2 "b"s.]

   Special case: the string desired is the first string beginning with the
   prefix we've built so far, but we haven't used up all the characters. In
   this case, the above procedure terminates without finding (printing) the
   whole string. But we know the strings are in lexicographical order, so the
   first string starting with the prefix we've found so far ends with the
   remaining characters in alphabetic order. So, we just add (print them) out
   in order.

   [Consider finding the 0th string. Initially, the prefix is the empty string
   and the current index is 0, so the first procedure doesn't do anything. In
   this case, just add (print out) all the "a"s, then all the "b"s, etc.]

*/

class Stringer {
    // This needs to work with BigIntegers because some of the intermediate
    // calculations will overflow even a long
    static BigInteger factorial(int n) {
        BigInteger result = BigInteger.valueOf(1);
        for (int i = 1; i < n + 1; i++)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    // For ACM programming, KISS > good style
    public static void main(String[] args) {
        // Input
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int N = in.nextInt();
            BigInteger K = in.nextBigInteger();

            // Termination condition
            if (N == 0 && K.equals(BigInteger.ZERO))
                return;

            // Pool of characters. pool[0] is the number of "a"s, pool[1] is
            // the number of "b"s, etc.
            int[] pool = new int[N];
            for (int i = 0; i < N; i++) {
                pool[i] = in.nextInt();
            }
            
            // Current index
            BigInteger curr = BigInteger.ZERO;

            // The intervals described above. The ith interval starts at
            // index[i] (inclusive) and ends at index[i + 1] (exclusive).
            BigInteger[] index = new BigInteger[N + 1];

            // Build the string one character at a time
            while (!curr.equals(K)) {
                // Calculate the total number of characters in the pool
                int total = 0;
                for (int i = 0; i < N; i++)
                    total += pool[i];

                // The first interval begins at the current index
                index[0] = curr;

                // Fix one of each character in the pool in turn
                for (int i = 0; i < N; i++) {
                    // If there are no characters of a certain type in the
                    // pool, then there are 0 strings which begin with that
                    // character, so the interval is of size zero.
                    if (pool[i] == 0) {
                        index[i + 1] = index[i];
                        continue;
                    }
                    // n!
                    BigInteger numerator = factorial(total - 1);
                    // (a!b!c!...)
                    BigInteger denominator = BigInteger.ONE;
                    for (int j = 0; j < pool.length; j++) {
                        if (j == i)
                            // Remove the fixed character from the pool
                            denominator = denominator.multiply(factorial(pool[j] - 1));
                        else
                            denominator = denominator.multiply(factorial(pool[j]));
                    }
                    // The endpoint of this interval is the start of the next
                    // one
                    index[i + 1] = index[i].add(numerator.divide(denominator));
                }

                // Find which interval K falls into; we start at 1 because we
                // know K > curr = index[0]
                int i = 1;
                while (i < index.length - 1 && K.compareTo(index[i]) >= 0)
                    i++;

                // i is incremented one too many times (to make the loop
                // condition false), so subtract 1 from it in everything that
                // follows:

                // Turn (i - 1) into an ASCII char: convert 'a' to an integer,
                // add (i - 1) to it, convert back to a char
                System.out.print((char)(i - 1 + (int)'a'));
                // Remove the character from the pool
                pool[i - 1]--;
                // Update the current index
                curr = index[i - 1];
            }

            // Handle the special case
            for (int i = 0; i < N; i++)
                for (int j = 0; j < pool[i]; j++)
                    System.out.print((char)(i + (int)'a'));

            // Print a newline
            System.out.println();
        }
    }
}
