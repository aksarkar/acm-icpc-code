/**
   ACM Midatlantic Regional 2002 Problem F

   Given a random walk on a grid (a nonsimple polygon of rectilinear segments),
   determine how many grid squares are inside (unreachable from outside).
   Iterate over the grid, marking squares outside if they are next to squares
   which are already marked outside, given there is no edge between them, until
   reaching a fixed point. Then, count the number of squares inside.
 */

#include <cstring>
#include <iostream>
#include <string>
using namespace std;

#define LEN 102

int grid[LEN][LEN];

int count() {
    for (int j = 0; j < 102; j++) {
        grid[0][j] |= 1;
        grid[j][0] |= 1;
        grid[j][101] |= 1;
        grid[101][j] |= 1;
    }
    bool changed = true;
    while (changed) {
        changed = false;
        for (int i = 1; i < 101; i++) {
            for (int j = 1; j < 101; j++) {
                if (grid[i][j] & 1) {
                    continue;
                }
                if ((!(grid[i][j] & 2) && (grid[i - 1][j] & 1)) ||
                    (!(grid[i][j] & 4) && (grid[i][j + 1] & 1)) ||
                    (!(grid[i][j] & 8) && (grid[i + 1][j] & 1)) ||
                    (!(grid[i][j] & 16) && (grid[i][j - 1] & 1))) {
                    changed = true;
                    grid[i][j] |= 1;
                }
            }
        }
    }
    int ninside = 0;
    for (int i = 1; i < 101; i++) {
        for (int j = 1; j < 101; j++) {
            if (!(grid[i][j] & 1))
                ninside++;
        }
    }
    return ninside;
}

int main() {
    int N;
    cin >> N;
    for (int i = 0; i < N; i++) {
        memset(grid, 0, sizeof(grid));
        int X, Y, Z;
        cin >> X >> Y >> Z;
        string dir;
        int offset;
        for (int j = 0; j < Z; j++) {
            cin >> dir >> offset;
            for (int k = 0; !dir.compare("N") && k < offset; k++, Y++) {
                grid[X][Y + 1] |= 8;
                grid[X + 1][Y + 1] |= 2;
            }
            for (int k = 0; !dir.compare("S") && k < offset; k++, Y--) {
                grid[X][Y] |= 8;
                grid[X + 1][Y] |= 2;
            }
            for (int k = 0; !dir.compare("E") && k < offset; k++, X++) {
                grid[X + 1][Y + 1] |= 16;
                grid[X + 1][Y] |= 4;
            }
            for (int k = 0; !dir.compare("W") && k < offset; k++, X--) {
                grid[X][Y] |= 4;
                grid[X][Y + 1] |= 16;
            }
        }
        cout << "Data Set " << i + 1 << ": " << count() << " square feet."
             << endl;
    }
    cout << "End of Output" << endl;
}
