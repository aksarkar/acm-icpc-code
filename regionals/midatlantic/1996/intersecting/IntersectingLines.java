/**
   ACM Midatlantic Regional 1996 Problem A
 */
import java.util.*;
import java.io.*;

class IntersectingLines {
    public static void findIntersection(int[] coords) {
        float m1, m2;
        m1 = (Float.valueOf(coords[3]) - coords[1]) / (coords[2] - coords[0]);
        m2 = (coords[7] - coords[5]) / (coords[6] - coords[4]);
        
        if (m1 == 0 && Float.isInfinite(m2)) {
            System.out.format("POINT %.2f %.2f\n", Float.valueOf(coords[1]), Float.valueOf(coords[4]));
        }
        else if (m2 == 0 && Float.isInfinite(m1)) {
            System.out.format("POINT %.2f %.2f\n", Float.valueOf(coords[0]), Float.valueOf(coords[5]));
        }
        else if (Float.isInfinite(m1) && Float.isInfinite(m2)) {
            if (coords[0] == coords[4])
                System.out.println("LINE");
            else
                System.out.println("NONE");
        }
        else if (m1 == 0 && m2 == 0) {
            if (coords[1] == coords[5])
                System.out.println("LINE");
            else
                System.out.println("NONE");
        }
        else {
            float b1 = coords[1] - m1 * coords[0];
            float b2 = coords[7] - m2 * coords[6];

            if (m1 == m2) {
                if (b1 == b2)
                    System.out.println("LINE");
                else
                    System.out.println("NONE");
                return;
            }

            float x = (b2 - b1) / (m1 - m2);
            float y = m1 * x + b1;
            System.out.format("POINT %.2f %.2f\n", x, y);
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        ArrayList<String> answers = new ArrayList<String>();
            
        System.out.println("INTERSECTING LINES OUTPUT");
        for (int i = 0; i < n; i++) {
            int[] coords = new int[8];
            for (int j = 0; j < 8; j++)
                coords[j] = s.nextInt();
            IntersectingLines.findIntersection(coords);
        }
        System.out.println("END OF OUTPUT");
    }
}