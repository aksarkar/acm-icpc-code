/**
   UVa 10067
   PC 110902
 */
#include <iostream>
#include <queue>
#include <set>
using namespace std;

int bfs(int start, int end, set<int> forbidden) {
  queue<int> Q;
  queue<int> D;
  set<int> V;
  Q.push(start);
  D.push(0);
  V.insert(start);

  while (!Q.empty()) {
    int curr = Q.front();
    Q.pop();
    int currd = D.front();
    D.pop();

    if (curr == end) {
      return currd;
    }
        
    int mask = 1;
    while (mask < 10000) {
      int next = (curr / (mask * 10)) * mask * 10 +
          ((curr % (mask * 10) - mask + mask * 10) % (mask * 10));
      if (!V.count(next) && !forbidden.count(next)) {
        Q.push(next);
        D.push(currd + 1);
        V.insert(next);
      }

      next = (curr / (mask * 10)) * mask * 10 + 
          ((curr % (mask * 10) + mask + mask * 10) % (mask * 10));
      if (!V.count(next) && !forbidden.count(next)) {
        Q.push(next);
        D.push(currd + 1);
        V.insert(next);
      }
      cerr << endl;

      mask *= 10;
    }
  }
  return -1;
}

int main() {
  int N;
  cin >> N;
  while (N--) {
    int t;
    int start = 0;
    for (int i = 0; i < 4; i++) {
      cin >> t;
      start = start * 10 + t;
    }

    int end = 0;
    for (int i = 0; i < 4; i++) {
      cin >> t;
      end = end * 10 + t;
    }

    int F;
    cin >> F;
    set<int> forbidden;
    for (int i = 0; i < F; i++) {
      int f = 0;
      for (int j = 0; j < 4; j++) {
        cin >> t;
        f = f * 10 + t;
      }
      forbidden.insert(f);
    }

    cout << bfs(start, end, forbidden) << endl;
  }
}
