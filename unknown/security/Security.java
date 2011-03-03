import java.util.*;

/**
   Implement rules for matching a PIN entry. One mismatch is allowed, where a
   mismatch is striking a key directly adjacent to the correct key.
 */
class Security {
    // Matrix of allowed mismatches
    static int[][] match = {{0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                            {0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
                            {0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                            {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                            {0, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                            {0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                            {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                            {1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
                            {0, 0, 0, 0, 0, 0, 1, 0, 1, 0}};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int N = in.nextInt();
            // List of valid PINs
            int[] valid = new int[N];
            
            for (int i = 0; i < N; i++) {
                valid[i] = in.nextInt();
            }

            int M = in.nextInt();
            for (int i = 0; i < M; i++) {
                int candidate = in.nextInt();
                boolean isValid = false;
                
                // Check for exact matches
                int j;
                for (j = 0; !isValid && j < N; j++) {
                    isValid = candidate == valid[j];
                }
                if (isValid) {
                    System.out.printf("%05d VALID %05d\n", candidate, valid[j - 1]);
                    continue;
                }

                // Check for PINs for which there is exactly one mismatch
                for (j = 0; !isValid && j < N; j++) {
                    int numMismatches = 0;
                    // Check each digit in turn
                    for (int k = 0; !isValid && numMismatches < 2 && k < 5; k++) {
                        int candidateDigit = (candidate / (int)Math.pow(10, k)) % 10;
                        int validDigit = (valid[j] / (int)Math.pow(10, k)) % 10;
                        if (candidateDigit == validDigit) {
                            continue;
                        }
                        else if (match[candidateDigit][validDigit] == 1) {
                            // Allowed mismatch
                            numMismatches++;
                        }
                        else {
                            // Disallowed mismatch
                            numMismatches = Integer.MAX_VALUE;
                        }
                    }
                    if (numMismatches < 2)
                        isValid = true;
                }
                if (isValid) {
                    System.out.printf("%05d VALID %05d\n", candidate, valid[j - 1]);
                }
                else {
                    System.out.printf("%05d INVALID\n", candidate);
                }
            }
            System.out.println("END OF OUTPUT");
        }
    }
}