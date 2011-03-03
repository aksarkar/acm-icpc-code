import java.util.*;

class Chemistry {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double[] D = new double[2];
        D[0] = in.nextDouble();
        while (in.hasNextDouble()) {
            D[1] = in.nextDouble();
            if (D[1] == 999) {
                System.out.println("End of Output");
                return;
            }
            System.out.format("%.2f\n", D[1] - D[0]);
            D[0] = D[1];
        }
    }
}
            