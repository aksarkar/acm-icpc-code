import java.util.*;

class Circuit {
    static class Resistor {
        String start, end;
        double r;

        public Resistor(String start, String end, double r) {
            this.start = start;
            this.end = end;
            this.r = r;
        }

        public String toString() {
            return this.start + " " + this.end + " " + String.valueOf(r);
        }

        public boolean equals(Object o) {
            if (o.getClass() != Resistor.class)
                return false;
            Resistor other = (Resistor)o;
            return this.start.equals(other.start) && this.end.equals(other.end) 
                && this.r == other.r;
        }
    }

    static boolean parallel(List<Resistor> circuit) {
        if (circuit.size() == 1)
            return false;
        for (int i = 0; i < circuit.size() - 1; i++) {
            for (int j = i + 1; j < circuit.size(); j++) {
                if (circuit.get(i).start.equals(circuit.get(j).start) &&
                    circuit.get(i).end.equals(circuit.get(j).end)) {
                    System.err.format("%s | %s (parallel)\n", circuit.get(i), circuit.get(j));
                    circuit.add(new Resistor(circuit.get(i).start, circuit.get(i).end, 1 / (1 / circuit.get(i).r + 1 / circuit.get(j).r)));
                    circuit.remove(j);
                    circuit.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    static boolean series(List<Resistor> circuit) {
        if (circuit.size() == 1)
            return false;
        for (int i = 0; i < circuit.size() - 1; i++) {
            for (int j = i + 1; j < circuit.size(); j++) {
                boolean isSeries = false;
                String shared = "", start = "", end = "";
                if (circuit.get(i).start.equals(circuit.get(j).start) &&
                    !circuit.get(i).end.equals(circuit.get(j).end)) {
                    isSeries = true;
                    shared = circuit.get(i).start;
                    if (circuit.get(i).end.compareTo(circuit.get(j).end) < 0) {
                        start = circuit.get(i).end;
                        end = circuit.get(j).end;
                    }
                    else {
                        start = circuit.get(j).end;
                        end = circuit.get(i).end;
                    }
                }
                else if (circuit.get(i).end.equals(circuit.get(j).end) &&
                         !circuit.get(i).start.equals(circuit.get(j).start)) {
                    isSeries = true;
                    shared = circuit.get(i).end;
                    if (circuit.get(i).start.compareTo(circuit.get(j).start) < 0) {
                        start = circuit.get(i).start;
                        end = circuit.get(j).start;
                    }
                    else {
                        start = circuit.get(j).start;
                        end = circuit.get(i).start;
                    }
                }
                else if (circuit.get(i).start.equals(circuit.get(j).end) &&
                         !circuit.get(i).end.equals(circuit.get(j).start)) {
                    isSeries = true;
                    shared = circuit.get(i).start;
                    if (circuit.get(i).end.compareTo(circuit.get(j).start) < 0) {
                        start = circuit.get(i).end;
                        end = circuit.get(j).start;
                    }
                    else {
                        start = circuit.get(j).start;
                        end = circuit.get(i).end;
                    }
                }                    
                else if (circuit.get(j).start.equals(circuit.get(i).end) &&
                         !circuit.get(j).end.equals(circuit.get(i).start)) {
                    isSeries = true;
                    shared = circuit.get(j).start;
                    if (circuit.get(j).end.compareTo(circuit.get(i).start) < 0) {
                        start = circuit.get(j).end;
                        end = circuit.get(i).start;
                    }
                    else {
                        start = circuit.get(i).start;
                        end = circuit.get(j).end;
                    }
                }                    
                else
                    continue;
                if (shared.equals("A") || shared.equals("Z"))
                    continue;
                for (int k = 0; k < circuit.size(); k++) {
                    if (k == i || k == j)
                        continue;
                    if (shared.equals(circuit.get(k).start) || shared.equals(circuit.get(k).end)) {
                        isSeries = false;
                        break;
                    }
                }
                if (isSeries) {
                    System.err.format("%s | %s (series)\n", circuit.get(i), circuit.get(j));
                    circuit.add(new Resistor(start, end, circuit.get(i).r + circuit.get(j).r));
                    circuit.remove(j);
                    circuit.remove(i);
                    return true;
                }
            }
        }
        return false;
    }        

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int counter = 0;
        while (true) {
            int n = in.nextInt();
            if (n == 0)
                return;

            LinkedList<Resistor> circuit = new LinkedList<Resistor>();
            for (int i = 0; i < n; i++) {
                String start = in.next();
                String end = in.next();
                double r = in.nextDouble();
                if (start.compareTo(end) < 0)
                    circuit.add(new Resistor(start, end, r));
                else if (start.compareTo(end) > 0)
                    circuit.add(new Resistor(end, start, r));
            }

            boolean a = false, z = false;
            for (int i = 0; i < circuit.size(); i++) {
                if (circuit.get(i).start.equals("A"))
                    a = true;
                if (circuit.get(i).end.equals("Z"))
                    z = true;
            }

            if (!a || !z) {
                System.out.format("Circuit %d: -1.000\n", ++counter);
                continue;
            }

            while (true) {
                System.err.println(circuit.toString());
                if (parallel(circuit))
                    continue;
                if (series(circuit))
                    continue;
                break;
            }
            if (circuit.size() == 1)
                System.out.format("Circuit %d: %.3f\n", ++counter, circuit.get(0).r);
            else
                System.out.format("Circuit %d: -1.000\n", ++counter);
        }
    }
}
