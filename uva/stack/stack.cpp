/**
   UVa 10205
   PC 110205
 */

#include <cstring>
#include <cstdlib>
#include <iostream>
#include <string>
using namespace std;

#define MAX_SHUFFLES 100
#define NCARDS 52

struct card {
  int value, suit;
};

void init(card *deck) {
  for (int i = 0; i < 4; i++) {
    for (int j = 2; j < 15; j++) {
      deck->suit = i;
      deck->value = j;
      deck++;
    }
  }
}

void apply(int *shuffle, card *deck, card *result) {
  for (int i = 0; i < NCARDS; i++) {
    card *curr = &deck[shuffle[i] - 1];
    result[i].suit = curr->suit;
    result[i].value = curr->value;
  }
}

void print(card *deck) {
  for (int i = 0; i < NCARDS; i++) {
    switch (deck[i].value) {
      case 11:
        cout << "Jack";
        break;
      case 12:
        cout << "Queen";
        break;
      case 13:
        cout << "King";
        break;
      case 14:
        cout << "Ace";
        break;
      default:
        cout << deck[i].value;
    }
    cout << " of ";
    switch (deck[i].suit) {
      case 0:
        cout << "Clubs";
        break;
      case 1:
        cout << "Diamonds";
        break;
      case 2:
        cout << "Hearts";
        break;
      case 3:
        cout << "Spades";
    }
    cout << endl;
  }
}

int main() {
  int shuffles[MAX_SHUFFLES][NCARDS];
  card deck[NCARDS], work[NCARDS];
  int numCases;
  cin >> numCases;
  int n;
  string line;
  while (numCases--) {
    init(deck);
    cin >> n;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < NCARDS; j++) {
        cin >> shuffles[i][j];
      }
    }
    getline(cin, line);
    getline(cin, line);
    while (!line.empty()) {
      int k = atoi(line.c_str());
      apply(shuffles[k - 1], deck, work);
      memcpy(deck, work, NCARDS * sizeof(card));
      getline(cin, line);
    }
    print(deck);
    if (numCases > 0) {
      cout << endl;
    }
  }
}
