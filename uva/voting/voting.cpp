/**
   UVa 10142
   PC 110108
*/
#include <cstring>
#include <iostream>
#include <sstream>
#include <string>
using namespace std;

#define MAX_CANDIDATES 20
#define MAX_BALLOTS 1000

int main() {
  int ballots[MAX_BALLOTS][MAX_CANDIDATES];
  int *head[MAX_BALLOTS];
  int count[MAX_CANDIDATES];
  bool elim[MAX_CANDIDATES];
  string names[MAX_CANDIDATES];

  int numcases;
  cin >> numcases;

  while (numcases--) {
    int numcandidates;
    cin >> numcandidates;
    cin.ignore();

    for (int i = 0; i < numcandidates; ++i) {
      getline(cin, names[i]);
    }

    string line;
    int numballots = 0;
    while (getline(cin, line) && !line.empty()) {
      istringstream stream(line);
      for (int j = 0; j < numcandidates; j++)
        stream >> ballots[numballots][j];
      head[numballots] = ballots[numballots];
      ++numballots;
    }

    memset(elim, 0, MAX_CANDIDATES);
    bool done(false);
    while (!done) {
      memset(count, 0, MAX_CANDIDATES);
      for (int i = 0; i < numballots; ++i) {
        while (elim[*head[i] - 1]) {
          ++head[i];
        }
        ++count[*head[i] - 1];
      }

      int max(count[0]), maxi(0), min(count[0]);
      for (int i = 1; i < numcandidates; ++i) {
        if (!elim[i] && count[i] > max) {
          max = count[i];
          maxi = i;
        }
        if (!elim[i] && count[i] < min) {
          min = count[i];
        }
      }

      if (2 * max > numballots) {
        cout << names[maxi] << endl;
        done = true;
      }
      else if (max == min) {
        for (int i = 0; i < numcandidates; ++i) {
          if (!elim[i]) {
            cout << names[i] << endl;
          }
        }
        done = true;
      }
      else {
        for (int i = 0; i < numcandidates; ++i) {
          if (count[i] == min) {
            elim[i] = true;
          }
        }
      }
    }

    if (numcases > 0)
      cout << endl;
  }
}
