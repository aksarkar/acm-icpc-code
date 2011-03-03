import java.util.*;

class Eyesore {
    static class Interval implements Comparable<Interval> {
        public double start, end;
        public Interval(double start, double end) {
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            double length = in.nextDouble();
            if (length < 0)
                return;

            List<Interval> intervals = new LinkedList<Interval>();

            while (true) {
                double start = in.nextDouble();
                double end = in.nextDouble();
                if (start > end)
                    break;
                if (start > length)
                    continue;
                if (end > length)
                    end = length;
                intervals.add(new Interval(start, end));
            }

            Collections.sort(intervals);

            List<Interval> finalints = new LinkedList<Interval>();
            while (!intervals.isEmpty()) {
                if (intervals.size() == 1)
                    finalints.add(intervals.remove(0));
                else if (intervals.get(0).start <= intervals.get(1).start &&
                         intervals.get(0).end <= intervals.get(1).end &&
                         intervals.get(0).end > intervals.get(1).start) {
                    intervals.get(0).end = intervals.get(1).end;
                    intervals.remove(1);
                }
                else if (intervals.get(0).start <= intervals.get(1).start &&
                         intervals.get(0).end >= intervals.get(1).end)
                    intervals.remove(1);
                else
                    finalints.add(intervals.remove(0));
            }

            for (Interval i : finalints)
                length -= (i.end - i.start);

            System.out.format("The total planting length is %.1f\n", length);
        }
    }
}
