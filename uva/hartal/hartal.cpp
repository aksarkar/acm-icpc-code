/**
   UVa 10050
   PC 110203
 */

#include <cstring>
#include <iostream>
using namespace std;

#define MAX_DAYS 3650

int main() {
  bool isHartal[MAX_DAYS];
  int T;
  cin >> T;
  for (int i = 0; i < T; i++) {
    memset(isHartal, 0, MAX_DAYS);
    int N, P;
    cin >> N >> P;
    for (int j = 0; j < P; j++) {
      int h;
      cin >> h;
      int curr = h - 1;
      while (curr < N) {
        isHartal[curr] = true;
        curr += h;
      }
    }
    int count = 0;
    for (int j = 0; j < N; j++) {
      if (j % 7 != 5 && j % 7 != 6 && isHartal[j])
        count++;
    }
    cout << count << endl;
  }
}
