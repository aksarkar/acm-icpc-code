// PC #110303
// UVA #10252
#include <algorithm>
#include <cstring>
#include <iostream>
using namespace std;

int main() {
    char a[1001], b[1001];
    int i, j, m, n;
    while (!cin.eof()) {
        cin.getline(a, 1001);
        if (cin.eof())
            return 0;
        cin.getline(b, 1001);
        m = strlen(a);
        n = strlen(b);
        sort(a, a + m);
        sort(b, b + n);
        i = 0;
        j = 0;
        while (i < m && j < n) {
            if (a[i] == b[j]) {
                cout << a[i];
                i++;
                j++;
            }
            else if (a[i] > b[j])
                j++;
            else
                i++;
        }
        cout << endl;
    }
}
