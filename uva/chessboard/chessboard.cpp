/**
   UVa 10161
   PC 111201
 */
#include <cmath>
#include <iostream>
using namespace std;

void getCoords(int n, int* result) {
    int s = 1;
    while (s * s < n)
        s++;
    if (s % 2) {
        result[0] = s;
        result[1] = 0;
        for (int i = pow(s - 1, 2) + 1; i <= pow(s - 1, 2) + s; i++) {
            result[1]++;
            if (i == n) return;
        }
        for (int i = pow(s - 1, 2) + s; i < n; i++) {
            result[0]--;
        }
    }
    else {
        result[0] = 0;
        result[1] = s;
        for (int i = pow(s - 1, 2) + 1; i <= pow(s - 1, 2) + s; i++) {
            result[0]++;
            if (i == n) return;
        }
        for (int i = pow(s - 1, 2) + s; i < n; i++) {
            result[1]--;
        }
    }
}

int main() {
    int n, coords[2];
    while (cin >> n) {
        if (n == 0) break;
        getCoords(n, coords);
        printf("%d %d\n", coords[0], coords[1]);
    }
}
