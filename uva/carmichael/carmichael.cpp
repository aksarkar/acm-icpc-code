/**
   UVa 10006
   PC 110702
*/
#include <iostream>

int main() {
  bool carmichael[65001];
  for (int i = 0; i < 65001; i++)
    carmichael[i] = false;
  carmichael[561] = true;
  carmichael[1105] = true;
  carmichael[1729] = true;
  carmichael[2465] = true;
  carmichael[2821] = true;
  carmichael[6601] = true;
  carmichael[8911] = true;
  carmichael[10585] = true;
  carmichael[15841] = true;
  carmichael[29341] = true;
  carmichael[41041] = true;
  carmichael[46657] = true;
  carmichael[52633] = true;
  carmichael[62745] = true;
  carmichael[63973] = true;

  int n;
  while (std::cin >> n) {
    if (n == 0)
      break;
    else if (carmichael[n])
      printf("The number %d is a Carmichael number.\n", n);
    else
      printf("%d is normal.\n", n);
  }
  return 0;
}
