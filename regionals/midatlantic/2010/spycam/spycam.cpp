/**
   ACM Midatlantic Regional 2010 Problem G
 */
#include <cstring>
#include <algorithm>
#include <iostream>
using namespace std;

/**
   A bounding rectangle
 */
struct Rect {
  int xmin, ymin, xmax, ymax;
};

const char SENTINEL = 'z' + 1;  // Simplify many checks
char pic[42][42];               // Include sentinels
Rect rects[26];
bool present[26];
bool above[27][27];             // Include sentinel

/**
   Compute bounding rectangles for each piece of paper
 */
void findBounds() {
  for (int k = 0; k < 26; k++) {
    rects[k].xmin = 42;
    rects[k].xmax = 0;
    rects[k].ymin = 42;
    rects[k].ymax = 0;
    for (int i = 0; i < 42; i++) {
      for (int j = 0; j < 42; j++) {
        if (pic[i][j] == 'a' + k) {
          present[k] = true;
          rects[k].xmin = min(rects[k].xmin, i);
          rects[k].xmax = max(rects[k].xmax, i);
          rects[k].ymin = min(rects[k].ymin, j);
          rects[k].ymax = max(rects[k].ymax, j);
        }
      }
    }
  }
}

/**
   Return true if the bounding rectangle contains the specified character.
 */
bool contains(Rect &r, char p) {
  for (int i = r.xmin; i <= r.xmax; i++) {
    for (int j = r.ymin; j <= r.ymax; j++) {
      if (pic[i][j] == p)
        return true;
    }
  }
  return false;
}

/**
   Compute the transitive closure of the above relation over the set of pieces
   of paper.
 */
void findAbove() {
  for (int i = 0; i < 26; i++) {
    for (int j = 0; j < 26; j++) {
      if (i != j && contains(rects[i], 'a' + j)) {
        above[j][i] = true;
      }
    }
  }
  for (int k = 0; k < 27; k++) {
    for (int i = 0; i < 27; i++) {
      for (int j = 0; j < 27; j++) {
        above[i][j] |= (above[i][k] && above[k][j]);
      }
    }
  }
}

/**
   Return true iff the specified rectangle can be extended in at least one
   direction. The rectangle cannot be extended in a particular direction if
   along that edge we find a pixel which must be below it.
 */
bool canExtend(int k) {
  Rect &r(rects[k]);
  bool up(true), right(true), down(true), left(true);
  for (int i = r.xmin; i <= r.xmax; i++) {
    up &= !above[k][pic[i][r.ymin - 1] - 'a'];
    down &= !above[k][pic[i][r.ymax + 1] - 'a'];
  }
  for (int j = r.ymin; j <= r.ymax; j++) {
    left &= !above[k][pic[r.xmin - 1][j] - 'a'];
    right &= !above[k][pic[r.xmax + 1][j] - 'a'];
  }
  return up || right || down || left;
}

/**
   Find the pieces of paper which are completely uncovered.
 */
void findUncovered() {
  cout << "Uncovered: ";
  for (int k = 0; k < 26; k++) {
    bool uncovered = present[k];
    for (int i = rects[k].xmin; uncovered && i <= rects[k].xmax; i++) {
      for (int j = rects[k].ymin; uncovered && j <= rects[k].ymax; j++) {
        uncovered = pic[i][j] == ('a' + k);
      }
    }
    if (uncovered && !canExtend(k)) {
      cout << (char)('a' + k);
    }
  }
  cout << endl;
}

int main() {
  int R(0), C(0);
  while (cin >> R >> C && (R | C)) {
    memset(pic, SENTINEL, sizeof(pic));
    memset(above, 0, sizeof(above));
    for (int i = 0; i < 27; i++) {
      above[i][26] = true;  // Everything is above the sentinel
    }
    memset(present, 0, sizeof(present));
    for (int i = 1; i <= R; i++) {
      for (int j = 1; j <= C; j++) {
        cin >> pic[i][j];
        if (pic[i][j] == '.')
          pic[i][j] = SENTINEL;
      }
    }
    findBounds();
    findAbove();
    findUncovered();
  }
}
