import java.util.*;

class Words {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String line = s.nextLine();
        String[] words = line.split("\\W+");
        Map<String, Integer> freq = new TreeMap<String, Integer>();
        for (String word : words) {
            String w = word.toLowerCase();
            if (freq.containsKey(w))
                freq.put(w, freq.get(w) + 1);
            else
                freq.put(w, 1);
        }
        int maxFreq = 0;
        ArrayList<String> maxWords = new ArrayList<String>();
        for (String word : freq.keySet()) {
            if (freq.get(word) > maxFreq) {
                maxFreq = freq.get(word);
                maxWords.clear();
                maxWords.add(word);
            }
            else if (freq.get(word) == maxFreq) {
                maxWords.add(word);
            }
        }
        System.out.format("%d occurrences\n", maxFreq);
        for (String word : maxWords)
            System.out.println(word);
    }
}