import java.util.*;

class Lock {
    static int ccw(int n, int start, int end) {
        if (start > (n - 1) / 2 && end < (n - 1) / 2)
            return end + n - start;
        else if (start > end)
            return n - (start - end);
        else
            return end - start;
    }

    static int cw(int n, int start, int end) {
        if (start < (n - 1) / 2 && end > (n - 1) / 2)
            return start + n - end;
        else if (start > end)
            return start - end;
        else
            return n - (end - start);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int n = in.nextInt();
            int t1 = in.nextInt();
            int t2 = in.nextInt();
            int t3 = in.nextInt();
            if (n == 0 && t1 == 0 && t2 == 0 && t3 == 0)
                return;
            
            int ticks = 2 * n + n - 1 + n + ccw(n, t1, t2) + cw(n, t2, t3);
            System.err.format("%d %d\n", ccw(n, t1, t2), cw(n, t2, t3));
            System.out.println(ticks);
        }
    }
}
