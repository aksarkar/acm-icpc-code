// PC #110404
// UVA #10191
import java.util.*;

class Nap {
    static String timeToHM(int time) {
        int hours = time / 60;
        int minutes = time - hours * 60;
        if (hours == 0)
            return String.format("%d minutes", minutes);
        else
            return String.format("%d hours and %d minutes", hours, minutes);
    }

    static String timeToString(int time) {
        time -= 600;
        int hours = time / 60;
        int minutes = time - hours * 60;
        return String.format("%d:%02d", hours + 10, minutes);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        in.useDelimiter("[\\s:]+");
        int counter = 0;
        while (in.hasNext()) {
            int n = in.nextInt();
            Interval[] intervals = new Interval[n + 2];
            intervals[0] = new Interval(600, 600);
            for (int i = 1; i < n + 1; i++) {
                int start = 60 * in.nextInt() + in.nextInt();
                int end = 60 * in.nextInt() + in.nextInt();
                in.nextLine();
                intervals[i] = new Interval(start, end);
            }
            intervals[n + 1] = new Interval(1080, 1080);

            Arrays.sort(intervals);
            int max = 0;
            int start = 600;
            for (int i = 1; i < intervals.length; i++) {
                int candidate = intervals[i].start - intervals[i - 1].end;
                if (candidate > max) {
                    max = candidate;
                    start = intervals[i - 1].end;
                }
            }
            System.out.format("Day #%d: the longest nap starts at %s and will last for %s.\n", ++counter, timeToString(start), timeToHM(max));
        }
    }
}

class Interval implements Comparable<Interval> {
    int start, end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    public int compareTo(Interval other) {
        if (this.start < other.start)
            return -1;
        else if (this.start == other.start && this.end < other.end)
            return -1;
        else if (this.start == other.start && this.end == other.end)
            return 0;
        else
            return 1;
    }
}
