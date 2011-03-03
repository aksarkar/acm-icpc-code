import java.util.*;
import java.util.regex.*;

class Index {
    public static Pattern indexable = Pattern.compile("\\{(.+?)\\}");

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ArrayList<String> docs = new ArrayList<String>();
        s.useDelimiter("\\*");
        String line;
        do {
            line = s.next().trim();
            docs.add(line);
        }
        while (!line.equals(""));
        docs.remove("");

        for (int i = 0; i < docs.size(); i++) {
            System.out.format("DOCUMENT %d\n", i + 1);
            String[] pages = docs.get(i).split("&");
            for (int j = 0; j < pages.length; j++) {
                Matcher m = indexable.matcher(pages[j]);
                while(m.find()) {
                    String[] pieces = m.group(1).trim().split("\\s*[$%]\\s*");
                    System.out.println(Arrays.toString(pieces));
                }
            }
        }
    }
}