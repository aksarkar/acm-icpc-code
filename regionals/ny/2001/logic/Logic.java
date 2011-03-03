import java.util.*;

/**
   ACM Greater New York Regional 2001 Problem E

   Given a description of a logic circuit and inputs, compute the output.
 */
class Logic {
    static final int DIM = 100, LEN = 26;

    static int traverse(char[][] pic, int i, int j, String inputs) {
        while (pic[i][j] == '+' || pic[i][j] == '-' || pic[i][j] == ':' || pic[i][j] == '?') {
            // Overwrite paths so we don't backtrack
            if (i > 0 && pic[i - 1][j] == '|') {  // up
                while (pic[--i][j] == '|') {
                    pic[i][j] = ' ';
                }
            }
            else if (j < DIM - 1 && pic[i][j + 1] == '-') {  // right
                while (pic[i][++j] == '-') {
                    pic[i][j] = ' ';
                }
            }
            else if (i < DIM - 1 && pic[i + 1][j] == '|') {  // down
                while (pic[++i][j] == '|') {
                    pic[i][j] = ' ';
                }
            }
            else if (j > 0) {  // left
                while (pic[i][--j] == '-') {
                    pic[i][j] = ' ';
                }
            }
        }
        if (pic[i][j] == 'o') {
            return traverse(pic, i, j - 1, inputs) == 0 ? 1 : 0;
        }
        else if (pic[i][j] == '>') {
            return traverse(pic, i - 1, j - 2, inputs) | traverse(pic, i + 1, j - 2, inputs);
        }
        else if (pic[i][j] == ')') {
            return traverse(pic, i - 1, j - 2, inputs) & traverse(pic, i + 1, j - 2, inputs);
        }
        else if ('A' <= pic[i][j] && pic[i][j] <= 'Z') {
            return inputs.charAt(pic[i][j] - 'A') - '0';
        }
        else {
            assert(false);
            return -1;
        }
    }

    public static void main(String[] args) {
        char[][] pic = new char[DIM][DIM];
        char[][] work = new char[DIM][DIM];
        Scanner in = new Scanner(System.in);
        boolean first = true;
        while (in.hasNext()) {
            for (int i = 0; i < DIM; i++)
                Arrays.fill(pic[i], ' ');
            int i = 0, qi = -1, qj = -1;
            String line = in.nextLine();
            while (!line.equals("*")) {
                for (int j = 0; j < line.length(); j++) {
                    pic[i][j] = line.charAt(j);
                    if (pic[i][j] == '?') {
                        qi = i;
                        qj = j;
                    }
                }
                i++;
                line = in.nextLine();
            }
            line = in.nextLine();
            while(!line.equals("*")) {
                for (int j = 0; j < DIM; j++)
                    for (int k = 0; k < DIM; k++)
                        work[j][k] = pic[j][k];
                System.out.println(traverse(work, qi, qj, line));
                line = in.nextLine();
            }
            System.out.println();
        }
    }
}