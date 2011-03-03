import java.util.*;

class Cantor {
    public static void main(String[] args) {
        StringBuilder[] cantor = new StringBuilder[13];
        cantor[0] = new StringBuilder("-");
        for (int i = 1; i < 13; i++) {
            cantor[i] = new StringBuilder();
            cantor[i].append(cantor[i - 1]);
            for (int j = 0; j < Math.pow(3, i - 1); j++)
                cantor[i].append(" ");
            cantor[i].append(cantor[i - 1]);
        }
        
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            System.out.println(cantor[in.nextInt()]);
        }
    }
}