/**
   UVa 100
   PC 110101
 */
#include <iostream>

int lengths[1000000] = {1};

int collatz(int n) {
    if (n == 1)
        return 1;
    else if (lengths[n - 1] > 0)
        return lengths[n - 1];
    else if (n & 1)
        lengths[n - 1] = collatz(3 * n + 1) + 1;
    else
        lengths[n - 1] = collatz(n / 2) + 1;
    return lengths[n - 1];
}

int main() {
    int i, j;
    while (std::cin >> i >> j) {
        bool switched = false;
        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
            switched = true;
        }

        int max = 0;
        for (int k = i; k <= j; k++) {
            int c = collatz(k);
            max = (max < c) ? c : max;
        }

        if (switched)
            printf("%d %d %d\n", j, i, max);
        else
            printf("%d %d %d\n", i, j, max);
    }
    return 0;
}
