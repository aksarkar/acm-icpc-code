/**
   UVa 10038
   PC 110201
 */

#include <cstring>
#include <cstdlib>
#include <iostream>
using namespace std;

#define MAX 3000

int main() {
  int seq[MAX];
  bool seen[MAX];

  int n;
  while (!cin.eof()) {
    cin >> n;
    for (int i = 0; i < n; i++)
      cin >> seq[i];
    if (n == 0 && !cin.eof()) {
      cout << "Not Jolly" << endl;
    }
    else if (n == 1 && !cin.eof()) {
      cout << "Jolly" << endl;
    }
    else {
      memset(seen, 0, MAX);
      for (int i = 1; i < n; i++) {
        seen[abs(seq[i] - seq[i - 1])] = true;
      }
      bool jolly = true;
      for (int i = 1; jolly && i < n; i++) {
        jolly = seen[i];
      }
      if (jolly && !cin.eof())
        cout << "Jolly" << endl;
      else if (!cin.eof())
        cout << "Not jolly" << endl;
    }
  }
}
