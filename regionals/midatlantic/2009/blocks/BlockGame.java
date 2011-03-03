import java.util.*;
import java.io.*;

// Block class to keep track of information for each block
class Block
{
    int i, j, len;
    char id;
    boolean isVert;

    public Block ()
    {
        i = -1;
        j = -1;
        len = 0;
        id = '_';
    }
}

class BlockGame
{
    // the grid used in all the computations
    public static char[][] grid = new char[6][6];

    // the id of the target block to move
    public static char target;

    // index of the target block to move in the block array
    public static int targetIndex;

    // the number of blocks
    public static int numBlocks;

    // array of blocks
    public static Block[] blocks = new Block[18];

    // array of starting positions used for encoding/decoding
    public static int[] pos = new int[18];

    // Parse the grid into blocks
    //   Fills blocks[] with blocks corresponding to the blocks found in grid[][]
    //   Sets numBlocks to equal the number of total blocks found on the board
    public static void parseBlocks ()
    {
        // reset the number of blocks
        numBlocks = 0;

        // iterate through the grid
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
            {
                // check to see if the grid contains a piece at (i, j)
                if (grid[i][j] != '.')
                {

                    if (grid[i][j] == target)
                        targetIndex = numBlocks;

                    // initialize a new block
                    blocks[numBlocks] = new Block();
                    blocks[numBlocks].id = grid[i][j];
                    blocks[numBlocks].i = i;
                    blocks[numBlocks].j = j;

                    // test if block is vertical
                    if (i + 1 < 6 && grid[i + 1][j] == blocks[numBlocks].id)
                    {
                        blocks[numBlocks].isVert = true;
                        pos[numBlocks] = i;

                        int k = i;
                        while (k < 6 && grid[k][j] == blocks[numBlocks].id)
                            grid[k++][j] = '.';

                        blocks[numBlocks].len = k - i;
                    }
                    // else it's horizontal
                    else
                    {
                        blocks[numBlocks].isVert = false;
                        pos[numBlocks] = j;

                        int k = j;
                        while (k < 6 && grid[i][k] == blocks[numBlocks].id)
                            grid[i][k++] = '.';

                        blocks[numBlocks].len = k - j;
                    }

                    numBlocks++;
                }
            }
    }

    // Performs a breadth first search on possible states of the grid until it
    // finds a solution state.  Returns the minimum distance to the first solution
    // state.
    public static int bfs ()
    {
        // queue to k
        Queue<Long> q = new LinkedList<Long> ();

        // queue to keep track of distances
        Queue<Integer> dist = new LinkedList<Integer> ();

        // set to keep track of visited states
        Set<Long> visited = new HashSet<Long> ();

        // next state;
        long state = encode (pos);

        // distance to current state
        int curDist = 0;

        q.add (state);
        dist.add (0);
        visited.add (state);

        while (q.peek() != null)
        {
            state = q.poll ();
            curDist = dist.poll ();

            // fill pos[] with information from current state
            decode (pos, state);

            // fill grid[][] with information from current pos[]
            fillGrid (grid, pos);


            if (solutionFound (grid, pos))
                return curDist + 1;

            // try to move each block
            for (int i = 0; i < numBlocks; i++)
            {
                // move up/left
                for (int j = pos[i] - 1; j >= 0; j--)
                {
                    // check to see if the next up/left position is empty
                    if ((blocks[i].isVert && grid[j][blocks[i].j] == '.') ||
                       (!blocks[i].isVert && grid[blocks[i].i][j] == '.'))
                    {
                        int temp = pos[i];
                        pos[i] = j;

                        state = encode (pos);
                        if (!visited.contains (state))
                        {
                            q.add (state);
                            dist.add (curDist + 1);
                            visited.add (state);
                        }

                        pos[i] = temp;
                    }
                    else
                        break;
                }

                // move down/right
                for (int j = pos[i] + blocks[i].len; j < 6; j++)
                {
                    // check to see if the next down/right position is empty
                    if ((blocks[i].isVert && grid[j][blocks[i].j] == '.') ||
                       (!blocks[i].isVert && grid[blocks[i].i][j] == '.'))
                    {
                        int temp = pos[i];
                        pos[i] = j - blocks[i].len + 1;

                        state = encode (pos);
                        if (!visited.contains (state))
                        {
                            q.add (state);
                            dist.add (curDist + 1);
                            visited.add (state);
                        }

                        pos[i] = temp;
                    }
                    else
                        break;
                }
            }
        }

        return -1;
    }

    // Encode the current position information as a base 6 long
    public static long encode (int[] pos)
    {
        long ret = 0;

        for (int i = 0; i < numBlocks; i++)
            ret = ret * 6 + pos[i];

        return ret;
    }

    // Decode a state represented as a base 6 long and fill pos[] with the
    // correspondong position information
    public static void decode (int[] pos, long state)
    {
        for (int i = 0; i < numBlocks; i++)
        {
            pos[numBlocks - i - 1] = (int)(state % 6);
            state /= 6;
        }
    }

    // Fills grid with the appropriate information found in pos[]
    public static void fillGrid (char[][] grid, int[] pos)
    {
        for (int i = 0; i < 6; i++)
            Arrays.fill (grid[i], '.');

        for (int i = 0; i < numBlocks; i++)
        {
            for (int j = 0; j < blocks[i].len; j++)
            {
                if (blocks[i].isVert)
                    grid[pos[i] + j][blocks[i].j] = blocks[i].id;
                else
                    grid[blocks[i].i][pos[i] + j] = blocks[i].id;
            }
        }
    }

    // Checks if the current state of grid is a solution state.
    //   Starts to the right of the target block and scans rightward, checking
    //   if each position is empty
    public static boolean solutionFound (char[][] grid, int[] pos)
    {
        for (int j = pos[targetIndex] + blocks[targetIndex].len; j < 6; j++)
        {
            if (grid[blocks[targetIndex].i][j] != '.')
                return false;
        }

        return true;
    }

    public static void main (String[] args)
    {
        Scanner s = new Scanner (System.in);

        while (s.hasNext())
        {
            // read input
            target = s.next().charAt(0);

            if (target == '*')
                break;

            for (int i = 0; i < 6; i++)
                grid[i] = s.next().toCharArray();

            // "good" programming style
            parseBlocks ();

            System.out.println (bfs ());
        }
    }
}
