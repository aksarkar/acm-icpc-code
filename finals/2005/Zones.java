import java.io.*;
import java.util.*;

/*
  ACM World Finals Problem J
*/
class Zones {
    static final String INPUTFILE = "zones.in";

    /*
      Count the number of bits set in the specified integer.
    */
    static int countBits(int n) {
        int result = 0;
        // If at any point n = 0, then no more bits are set.
        while (n != 0) {
            // Add 1 iff the least-significant bit is set
            result += n & 1;
            // Handle n < 0 using logical shift, not arithmetic shift
            n >>>= 1;
        }
        return result;
    }

    /*
      Calculate the total cost (number of people served) by the specified
      subset of towers. We do this by adding together the cost for each of the
      towers taken one at a time and then subtract off the cost for overlap
      regions which have been counted multiple times.
     */
    static int totalCost(int subset, List<Integer> subsets, 
                         List<Integer> costs) {
        int result = 0;
        for (int i = 0; i < subsets.size(); i++) {
            if (countBits(subsets.get(i)) == 1 &&
                (subsets.get(i) & subset) == subsets.get(i)) {
                // Add the cost of a single tower to be built
                result += costs.get(i);
            }
            int overlap = countBits(subsets.get(i) & subset);
            if (overlap > 1) {
                // The ith region has been counted overlap times, so subtract
                // (overlap - 1) times its cost.
                result -= (overlap - 1) * costs.get(i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File(INPUTFILE));
        }
        catch (FileNotFoundException e) {
            return;
        }

        int curr = 1;
        while (in.hasNext()) {
            int planned = in.nextInt();
            int built = in.nextInt();

            // Bit wizardry to check whether multiple integers are all equal to
            // 0
            if ((planned | built) == 0)
                return;

            // Store the mapping between subsets of towers and the number of
            // people served (the cost) by the entire subset. There is no
            // advantage to using a map-type data structure because we will end
            // up iterating over all of the elements anyway to calculate the
            // total cost.

            // Subsets are specified by bit vectors stored as integers; the ith
            // bit (counting from the least-significant bit) is 1 iff the ith
            // element is contained in the subset.
            List<Integer> subsets = new LinkedList<Integer>();
            List<Integer> costs = new LinkedList<Integer>();

            // Read data for each tower taken invidiually
            for (int i = 0; i < planned; i++) {
                subsets.add(1 << i);
                costs.add(in.nextInt());
            }

            // Read data for overlap regions
            int numOverlap = in.nextInt();
            for (int i = 0; i < numOverlap; i++) {
                // Initialize the subset to contain no towers
                int subset = 0;
                int numTowers = in.nextInt();
                // Add the specified towers to the subset
                for (int j = 0; j < numTowers; j++)
                    // Subtract one to account for one-indexing
                    subset |= 1 << (in.nextInt() - 1);
                subsets.add(subset);
                costs.add(in.nextInt());
            }

            // Try all possible subsets of towers which contain the correct
            // number of towers, calculating the total cost for the subset
            // using inclusion-exclusion. We iterate through subsets by
            // counting as this is the easiest to implement (but not the most
            // efficient). Note that the tie-breaking rule really supports
            // using this approach, as we generate subsets in lexicographical
            // order (counting in binary).
            int maxCost = 0;
            int maxSubset = 0;

            // The last subset to check has all bits set up to the planned-th
            // bit; the next "subset" (which isn't valid, as it would contain a
            // non-existent tower) is 1 << planned.
            for (int i = 0; i < (1 << planned); i++) {
                if (countBits(i) != built)
                    // The current subset doesn't contain the correct number of
                    // elements
                    continue;
                int cost = totalCost(i, subsets, costs);
                if (cost > maxCost) {
                    maxCost = cost;
                    maxSubset = i;
                }
            }

            if (curr > 1)
                System.out.println();
            System.out.printf("Case Number  %d\n", curr++);
            System.out.printf("Number of Customers: %d\n", maxCost);
            System.out.print("Locations recommended:");
            for (int i = 0; i < planned; i++) {
                if (((maxSubset >> i) & 1) == 1)
                    System.out.printf(" %d", i + 1);
            }
            System.out.println();
        }
    }
}