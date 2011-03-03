/**
   ACM Midatlantic Regional 2001 Problem E
 */
import java.util.*;

class Emissions {
    public static void main(String[] args) {
        ArrayList<Table> tables = new ArrayList<Table>();
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        in.nextLine();

        for (int i = 0; i < num; i++) {
            String input = in.next();
            if (input.equals("."))
                input = "";
            String cancellations = in.next();
            if (cancellations.equals("."))
                cancellations = "";
                
            tables.add(new Table(input.toCharArray(), 
                                 cancellations.toCharArray()));
        }

        int a = in.nextInt();
        int b = in.nextInt();
        while (a != 0 && b != 0) {
            tables.get(a - 1).addConnection(tables.get(b - 1));
            in.nextLine();
            a = in.nextInt();
            b = in.nextInt();
        }

        for (Table t : tables) {
            StringBuilder s = new StringBuilder(":");
            for (Character c : t.output())
                s.append(c);
            s.append(":");
            System.out.println(s.toString());
        }
    }
}

class Table {
    public TreeSet<Character> input, cancellations, output;
    public ArrayList<Table> downstream;

    public Table(char[] input, char[] cancellations) {
        this.input = new TreeSet<Character>();
        for (char c : input)
            this.input.add(c);

        this.cancellations = new TreeSet<Character>();
        for (char c : cancellations)
            this.cancellations.add(c);

        this.output = output();

        this.downstream = new ArrayList<Table>();
    }

    public void addConnection(Table t) {
        this.downstream.add(t);
        t.input.addAll(this.output);
    }

    public TreeSet<Character> output() {
        TreeSet<Character> output = new TreeSet<Character>();
        for (Character c : this.input) {
            if (!this.cancellations.contains(c))
                output.add(c);
        }
        return output;
    }
}