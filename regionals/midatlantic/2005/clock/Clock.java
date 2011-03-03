import java.util.*;

/**
   ACM Midatlantic Regional 2005 Problem F

   Given the angle between clock hands and a lower bound, find the time on the
   clock.
 */
class Clock {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        in.useDelimiter("[\\s:]+");
        while (in.hasNext()) {
            int angle = in.nextInt();
            if (angle == -1)
                return;
            // Convert time to seconds after 0:00:00
            double givenTime = 3600 * in.nextInt() + 60 * in.nextInt() + in.nextInt();
            double t = 120.0 * angle / 11;
            // The hands will make the same angle 11 times in one sweep of the
            // clock face (12 hours)
            double delta = 12.0 * 60 * 60 / 11;
            while (t < givenTime) {
                t += delta;
            }
            t += 1e-8;
            int hours = (int)t / 3600 % 24;
            t %= 3600;
            int mins = (int)t / 60;
            t %= 60;
            int secs = (int)t;
            System.out.format("%02d:%02d:%02d\n", hours, mins, secs);
        }
    }
}