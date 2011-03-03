import java.util.*;
import java.util.regex.*;

class Stems {
    static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

    static String apply(String s, List<Pattern> from, List<Pattern> to) {
        String[] words = s.split("\\s+");
        String[] result = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < from.size(); j++) {
                Matcher m = from.get(j).matcher(words[i]);
                if (m.matches()) {
                    result[i] = words[i].replace(from.get(j).toString(), to.get(j).toString());
                    break;
                }
            }
        }
        for (int i = 0; i < words.length; i++)
            s = s.replaceFirst(words[i], result[i]);
        return s;
    }

    static Pattern parsePattern(String patt) {
        for (int i = 1; i < 10; i++) {
            if (!patt.contains(String.valueOf(i)))
                continue;
            patt = patt.replace(String.valueOf(i), String.valueOf(patt.charAt(i)));
        }
        for (int i = 0; i < 26; i++)
            patt = patt.replace(String.valueOf(ALPHA.charAt(i)), 
                                "[" + ALPHA.charAt(i) + Character.toUpperCase(ALPHA.charAt(i)) + "]");
        patt = patt.replace("*", "\\w+").replace("V", "[aeiou]").replace("C", "[^aeiou]");
        return Pattern.compile(patt);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            List<Pattern> from = new ArrayList<Pattern>();
            List<Pattern> to = new ArrayList<Pattern>();
            while (true) {
                String line = in.nextLine();
                if (line.isEmpty())
                    break;
                String[] pieces = line.split("=>");
                from.add(parse(pieces[0]));
                to.add(parse(pieces[1]));
            }
            StringBuilder s = new StringBuilder();
            while (true) {
                String line = in.nextLine();
                if (line.isEmpty())
                    break;
                s.append(line);
            }
            System.out.println(apply(s.toString(), from, to));
        }
    }
}