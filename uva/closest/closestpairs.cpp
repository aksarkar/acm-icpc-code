/**
   UVa 10161
   PC 111402
 */
#include <cmath>
#include <cstdio>
#include <iostream>
#include <limits>
using namespace std;

int main() {
  int n;
  while (cin >> n && n > 0) {
    double x[n], y[n], result(numeric_limits<double>::max());
    // Really we should be using a more sophisticated algorithm than testing
    // every pair of points, but the limits (10K points) are such that brute
    // force will still run within the time limit (not in Java, though).
    for (int i = 0; i < n; i++) {
      cin >> x[i] >> y[i];
      for (int j = i + 1; j < n; j++) {
        double sqdist(pow(x[i] - x[j], 2) + pow(y[i] - y[j], 2));
        result = (result > sqdist) ? sqdist : result;
      }
    }
    result = sqrt(result);
    if (result < 10000)
      printf("%.4f\n", result);
    else
      printf("INFINITY\n");
  }
}
