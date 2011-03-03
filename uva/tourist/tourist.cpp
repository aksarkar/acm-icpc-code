/**
   UVa 10099
   PC 110903
*/
#include <iostream>
using namespace std;

int table[100][100];

/**
   Max-flow min-cut: modified Floyd-Warshall algorithm.
*/
int floydWarshall(int size, int start, int end) {
  for (int k = 0; k < size; k++)
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++)
        table[i][j] = max(table[j][i], min(table[i][k], table[k][j]));
  return table[start][end];
}

int main() {
  int n, r, scenario = 0;

  while (cin >> n >> r) {
    if (n == 0 && r == 0)
      break;

    scenario++;
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        table[i][j] = 0;
        
    int c1, c2, weight;
    for (int i = 0; i < r; i++) {
      cin >> c1 >> c2 >> weight;
      c1--;
      c2--;
      table[c1][c2] = table[c2][c1] = weight;
    }
        
    int start, end, numPeople;
    cin >> start >> end >> numPeople;
    start--;
    end--;
        
    int maxPeople = floydWarshall(n, start, end) - 1;
    int quotient = numPeople / maxPeople;
    int remainder = numPeople % maxPeople;
    int soln = (remainder == 0) ? quotient : quotient + 1;
        
    printf("Scenario #%d\n", scenario);
    printf("Minimum Number of Trips = %d\n\n", soln);
  }
    
  return 0;
}
