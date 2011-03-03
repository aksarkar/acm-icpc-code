// PC #110103
// UVA #10137
#include <algorithm>
#include <cmath>
#include <iostream>
using namespace std;

int main() {
    int n;
    while (cin >> n) {
        if (n == 0)
            return 0;
        double costs[n];
        for (int i = 0; i < n; i++) {
            cin >> costs[i];
        }
        sort(costs, costs + n);
        
        double sum = 0;
        for (int i = 0; i < n; i++)
            sum += costs[i];
        double meanc = ceil(100 * sum / n) / 100;
        double meanf = floor(100 * sum / n) / 100;

        double Dc[n], Df[n];
        for (int i = 0; i < n; i++) {
            Dc[i] = abs(costs[i] - meanc);
            Df[i] = abs(costs[i] - meanf);
        }

        double take = 0, give = 0;
        for (int i = 0; costs[i] < meanf; i++)
            take += Df[i];
        for (int i = n - 1; costs[i] > meanc; i--)
            give += Dc[i];

        printf("$%.2f\n", max(give, take));
    }
}
