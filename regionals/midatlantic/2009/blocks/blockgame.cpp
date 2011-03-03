#include <iostream>
#include <cstring>
#include <queue>
#include <set>

using namespace std;

typedef unsigned long long ull;

struct block
{
    int x, y, len;
    char id;
    bool is_vert;
} blocks[18];

char target;
char grid[6][6];
int nblocks, target_index, pos[18];

int parse_blocks ()
{
    nblocks = 0;
    for (int i = 0; i < 6; i++)
        for (int j = 0; j < 6; j++)
            if (grid[i][j] != '.')
            {
                if (grid[i][j] == target)
                    target_index = nblocks;

                blocks[nblocks].x = j;
                blocks[nblocks].y = i;
                blocks[nblocks].id = grid[i][j];

                // check if is vert
                if (i + 1 < 6 && grid[i + 1][j] == grid[i][j])
                {
                    blocks[nblocks].is_vert = true;
                    pos[nblocks] = i;

                    int k = i;
                    while (k < 6 && grid[k][j] == blocks[nblocks].id)
                        grid[k++][j] = '.';

                    blocks[nblocks].len = k - i;
                }
                // check if is horiz
                else if (j + 1 < 6 && grid[i][j + 1] == grid[i][j])
                {
                    blocks[nblocks].is_vert = false;
                    pos[nblocks] = j;

                    int k = j;
                    while (k < 6 && grid[i][k] == blocks[nblocks].id)
                        grid[i][k++] = '.';

                    blocks[nblocks].len = k - j;
                }

                nblocks++;
            }
}

ull encode_state (int pos[])
{
    ull result = 0;

    for (int i = 0; i < nblocks; i++)
        result = result * 6 + pos[i];

    return result;
}

void decode_state (ull state, int pos[])
{
    for (int i = 0; i < nblocks; i++)
    {
        pos[nblocks - i - 1] = state % 6;
        state /= 6;
    }
}

void fill_grid (char grid[][6], int pos[])
{
    memset (grid, 0, 6 * 6);

    for (int i = 0; i < nblocks; i++)
    {
        for (int j = 0; j < blocks[i].len; j++)
        {
            if (blocks[i].is_vert)
                grid[pos[i] + j][blocks[i].x] = blocks[i].id;
            else
                grid[blocks[i].y][pos[i] + j] = blocks[i].id;
        }
    }
}

bool solution_found (char grid[][6], int pos[])
{
    for (int i = pos[target_index] + 2; i < 6; i++)
    {
        if (grid[blocks[target_index].y][i] != 0)
            return false;
    }

    return true;
}

int bfs ()
{
    ull state;
    int cur_dist, t;
    queue <ull> q;
    queue <int> dist;
    set <ull> visited;

    state = encode_state (pos);
    q.push (state);
    dist.push (0);
    visited.insert (state);

    while (!q.empty ())
    {
        state = q.front ();
        q.pop ();

        cur_dist = dist.front ();
        dist.pop ();

        decode_state (state, pos);
        fill_grid (grid, pos);

        if (solution_found (grid, pos))
            return cur_dist + 1;

        // try to move all pieces
        for (int i = 0; i < nblocks; i++)
        {
            // try to move block left/up
            for (int j = pos[i] - 1; j >= 0; j--)
            {
                if ((blocks[i].is_vert && grid[j][blocks[i].x] == 0) ||
                    !blocks[i].is_vert && grid[blocks[i].y][j] == 0)
                    {
                        t = pos[i];
                        pos[i] = j;

                        state = encode_state (pos);

                        if (!visited.count (state))
                        {

                            q.push (state);
                            dist.push (cur_dist + 1);
                            visited.insert (state);
                        }

                        pos[i] = t;
                    }
                    else
                        break;
            }
            // try to move block down/right
            for (int j = pos[i] + blocks[i].len; j < 6; j++)
            {
                if ((blocks[i].is_vert && grid[j][blocks[i].x] == 0) ||
                    !blocks[i].is_vert && grid[blocks[i].y][j] == 0)
                    {
                        t = pos[i];
                        pos[i] = j - blocks[i].len + 1;

                        state = encode_state (pos);

                        if (!visited.count (state))
                        {

                            q.push (state);
                            dist.push (cur_dist + 1);
                            visited.insert (state);
                        }

                        pos[i] = t;
                    }
                    else
                        break;
            }
        }
    }

    return -1;
}

int main ()
{
    while (cin >> target && target != '*')
    {
        // read input
        cin.ignore();
        for (int i = 0; i < 6; i++)
            cin.getline (grid[i], 7);

        // find blocks
        parse_blocks ();

        // run bfs
        cout << bfs () << endl;
    }
}
