/**
   UVa 102
 */

#include <iostream>
#include <string>
using namespace std;

int main() {
  int bottles[9], work[6], total(0);
  string confs[] = {"BCG", "BGC", "CBG", "CGB", "GBC", "GCB"};
  while (!cin.eof()) {
    total = 0;
    for (int i = 0; i < 9; i++) {
      cin >> bottles[i];  // BGC
      total += bottles[i];
    }
    work[0] = total - bottles[0] - bottles[5] - bottles[7];  // BCG
    work[1] = total - bottles[0] - bottles[4] - bottles[8];  // BGC
    work[2] = total - bottles[2] - bottles[3] - bottles[7];  // CBG
    work[3] = total - bottles[2] - bottles[4] - bottles[6];  // CGB
    work[4] = total - bottles[1] - bottles[3] - bottles[8];  // GBC
    work[5] = total - bottles[1] - bottles[5] - bottles[6];  // GCB
    int min_(work[0]), mini(0);
    for (int i = 1; i < 6; i++) {
      if (work[i] < min_) {
        min_ = work[i];
        mini = i;
      }
    }
    if (!cin.eof()) {
      cout << confs[mini] << " " << min_ << endl;
    }
  }
}
