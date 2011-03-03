/**
   UVa 120
   PC 110402
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Pancakes {
  public static ArrayList<Integer> sort(ArrayList<Integer> diameters) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    int bottom = diameters.size() - 1;
    while (bottom > 0) {
      if (isSorted(diameters))
        break;
      int rightIndex = rightIndex(diameters, bottom);
      int leftIndex = leftIndex(diameters, bottom);
      if (rightIndex == bottom)
        bottom--;
      else if (leftIndex == 0) {
        flip(diameters, bottom);
        result.add(diameters.size() - bottom);
        bottom--;
      }
      else {
        flip(diameters, rightIndex);
        result.add(diameters.size() - rightIndex);
        flip(diameters, bottom);
        result.add(diameters.size() - bottom);
        bottom--;
      }
    }
    return result;
  }

  public static int rightIndex(ArrayList<Integer> list, int bottom) {
    int curmax = 0;
    int index = 0;
    for (int i = 0; i <= bottom; i++) {
      if (list.get(i) >= curmax) {
        curmax = list.get(i);
        index = i;
      }
    }
    return index;
  }
     
  public static int leftIndex(ArrayList<Integer> list, int bottom) {
    int curmax = 0;
    int index = 0;
    for (int i = 0; i <= bottom; i++) {
      if (list.get(i) > curmax) {
        curmax = list.get(i);
        index = i;
      }
    }
    return index;
  }

  public static void flip(ArrayList<Integer> list, int index) {
    for (int i = 0; i < index; i++, index--) {
      int temp = list.get(i);
      list.set(i, list.get(index));
      list.set(index, temp);
    }
  }
     
  public static boolean isSorted(ArrayList<Integer> list) {
    for (int i = 0; i < list.size(); i++)
      for (int j = i; j < list.size(); j++)
        if (list.get(i) > list.get(j)) return false;
    return true;
  }
     
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      String line = in.nextLine();
      System.out.println(line);
      String[] pieces = line.split("\\s+");
      ArrayList<Integer> diameters = new ArrayList<Integer>();
      for (String s : pieces)
        diameters.add(Integer.parseInt(s));
      ArrayList<Integer> flips = sort(diameters);
      StringBuilder soln = new StringBuilder("");
      for (Integer i : flips) {
        soln.append(String.valueOf(i));
        soln.append(" ");
      }
      soln.append("0");
      System.out.println(soln.toString());
    }
  }
}
