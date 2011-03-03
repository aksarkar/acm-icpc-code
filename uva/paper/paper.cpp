/**
   UVa 177
 */

#include <algorithm>
#include <cmath>
#include <cstdio>
#include <cstring>
#include <iostream>
using namespace std;

#define MAX 14

#define UP 0
#define RIGHT 1
#define DOWN 2
#define LEFT 3

int main() {
  int repr[1 << MAX];
  repr[0] = RIGHT;
  for (int i = 1; i < MAX; i++) {
    for (int j = 1 << (i - 1); j < 1 << i; j++) {
      repr[j] = ((repr[(1 << i) - j - 1] - 1) % 4 + 4) % 4;
    }
  }

  int xmin[MAX], xmax[MAX], ymin[MAX], ymax[MAX];
  memset(xmin, 0, MAX);
  memset(xmax, 0, MAX);
  memset(ymin, 0, MAX);
  memset(ymax, 0, MAX);
  for (int i = 0; i < MAX; i++) {
    int x = 0, y = 0;
    for (int j = 0; j < 1 << i; j++) {
      switch (repr[j]) {
        case 0:
          y++;
          ymax[i] = max(ymax[i], y);
          break;
        case 1:
          x++;
          xmax[i] = max(xmax[i], x);
          break;
        case 2:
          y--;
          ymin[i] = min(ymin[i], y);
          break;
        case 3:
          x--;
          xmin[i] = min(xmin[i], x);
      }
    }
  }

  int n;
  while (!cin.eof()) {
    cin >> n;
    if (n == 0)
      return 0;

    int w = 2 * (xmax[n] - xmin[n]) + 1;
    int h = ymax[n] - ymin[n] + 1;
    char **buff = new char*[h];
    for (int i = 0; i < h; i++) {
      buff[i] = new char[w];
      memset(buff[i], ' ', w);
    }

    int x = 2 * abs(xmin[n]);
    int y = ymax[n];
    int vert = 0;
    for (int i = 0; i < 1 << n; i++) {
      switch (repr[i]) {
        case 0:
          buff[y--][x] = '|';
          vert = 1;
          break;
        case 1:
          x += vert;
          buff[y][x++] = '_';
          vert = 0;
          break;
        case 2:
          buff[y++][x] = '|';
          vert = 1;
          break;
        case 3:
          x -= vert;
          buff[y][x--] = '_';
          vert = 0;
      }
    }

    for (int i = 0; i < h; i++) {
      printf("%s\n", buff[i]);
    }

    printf("^\n");

    for (int i = 0; i < h; i++)
      delete[] buff[i];
    delete[] buff;
  }
  return 0;
}
