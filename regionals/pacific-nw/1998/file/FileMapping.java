/**
   Pacific Northwest Regional 1998 Problem 4
 */
import java.util.*;

class FileMapping {
    public static String processDataSet(String dataset) {
        Dir root = new Dir("ROOT", null);
        Dir current = root;
        String[] entries = dataset.split("\\s+");
        for (String s : entries) {
            if (s.matches("f.*")) {
                current.add(new File(s));
            }
            else if (s.matches("d.*")) {
                Dir newdir = new Dir(s, current);
                current.add(newdir);
                current = newdir;
            }
            else if (s.equals("]")) {
                current = current.container;
            }
        }

        return root.toString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        StringBuilder dataset = new StringBuilder("");
        int counter = 1;
        while (true) {
            String line = in.nextLine();
            if (line.equals("*")) {
                System.out.format("DATA SET %d:\n", counter);
                System.out.println(FileMapping.processDataSet(dataset.toString()));
                dataset = new StringBuilder("");
                counter++;
            }
            else if (line.equals("#")) {
                return;
            }
            else {
                dataset.append(line);
                dataset.append(" ");
            }
        }
    }
}

class File implements Comparable<File> {
    public String name;

    public File(String name) {
        this.name = name;
    }

    public int compareTo(File other) {
        return this.name.compareTo(other.name);
    }
}

class Dir implements Comparable<Dir> {
    public TreeSet<Dir> dirs;
    public TreeSet<File> files;
    public String name;
    public Dir container;

    public Dir(String name, Dir container) {
        this.name = name;
        this.dirs = new TreeSet<Dir>();
        this.files = new TreeSet<File>();
        this.container = container;
    }

    public void add(File f) {
        this.files.add(f);
    }

    public void add(Dir d) {
        this.dirs.add(d);
    }

    public int compareTo(Dir other) {
        return -this.name.compareTo(other.name);
    }

    public String toString() {
        StringBuilder result = new StringBuilder("");
        result.append(this.name + "\n");
        for (Dir d : this.dirs) {
            String[] pieces = d.toString().split("\n");
            for (String s : pieces)
                result.append("|     " + s + "\n");
        }
        for (File f : this.files) {
            result.append(f.name);
            result.append("\n");
        }
        return result.toString();
    }
}