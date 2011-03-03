/**
   UVa 111
*/

#include <cstring>

#include <algorithm>
#include <iostream>

using namespace std;

int lcs(int *x, int *y, int nx, int ny) {
  int table[nx + 1][ny + 1];
  memset(table, 0, sizeof(table));
  for (int i = 1; i < nx + 1; i++) {
    for (int j = 1; j < ny + 1; j++) {
      if (x[i - 1] == y[j - 1]) {
        table[i][j] = table[i - 1][j - 1] + 1;
      }
      else {
        table[i][j] = max(table[i - 1][j], table[i][j - 1]);
      }
    }
  }
  return table[nx][ny];
}

int main() {
  int n;
  cin >> n;
  int order[n], candidate[n];
  int t;
  for (int i = 0; i < n; i++) {
    cin >> t;
    order[t - 1] = i;
  }
  while (!cin.eof()) {
    for (int i = 0; i < n; i++) {
      cin >> t;
      candidate[t - 1] = i;
    }
    if (!cin.eof()) {
      cout << lcs(&order[0], &candidate[0], n, n) << endl;
    }
  }
}
