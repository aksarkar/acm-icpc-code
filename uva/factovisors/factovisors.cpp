/**
   UVa 10139
 */
#include <iostream>
using namespace std;

typedef long long llong;

int main() {
    llong n, m;
    while (cin >> n >> m) {
        if (n == 0) {
            if (m == 1)
                printf("%lld divides %lld!\n", m, n);
            else
                printf("%lld does not divide %lld!\n", m, n);
            continue;
        }
        if (m == 0) {
            printf("%lld does not divide %lld!\n", m, n);
            continue;
        }
        if (n >= m) {
            printf("%lld divides %lld!\n", m, n);
            continue;
        }
        llong n_ = n;
        for (llong i = n - 1; i > 1 && n_ > 0; i--)
            n_ = n_ * i % m;
        if (n_ == 0)
            printf("%lld divides %lld!\n", m, n);
        else
            printf("%lld does not divide %lld!\n", m, n);
    }
}
