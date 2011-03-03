/**
   UVa 10161
 */
#include <cmath>
#include <iostream>
using namespace std;

int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        int p, q;
        cin >> p >> q;
        if (p == q) {
            cout << 0 << endl;
            continue;
        }
        int s = ceil(sqrt(q - p));
        if (s * s - (q - p) < s)
            cout << 2 * s - 1 << endl;
        else
            cout << 2 * s - 2 << endl;
    }
}
