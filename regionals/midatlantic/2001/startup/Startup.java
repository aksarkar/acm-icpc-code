/**
   ACM Midatlantic Regional 2001 Problem C
 */
import java.util.*;

class Startup {
    public static ArrayList<String> getWords(String doc) {
        String[] words = doc.split("\\s+");
        ArrayList<String> result = new ArrayList<String>();
        for (String word : words) {
            word = word.toLowerCase();
            word = word.replaceAll("\\W", "");
            if (!word.matches("\\s*"))
                result.add(word);
        }
        return result;
    }

    public static HashMap<String, Integer> 
        getFrequency(ArrayList<String> words) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        for (String word : words) {
            if (result.containsKey(word))
                result.put(word, result.get(word) + 1);
            else
                result.put(word, 1);
        }
        return result;
    }

    public static double calcScore(ArrayList<String> queryList, 
                                   ArrayList<String> words) {
        HashMap<String, Integer> queryFreq = Startup.getFrequency(queryList);
        HashMap<String, Integer> wordFreq = Startup.getFrequency(words);

        double result = 0;
        for (String queryword : queryFreq.keySet()) {
            for (String docword : wordFreq.keySet()) {
                if (queryword.equals(docword)) {
                    result += Math.sqrt(queryFreq.get(queryword) * 
                                        wordFreq.get(docword));
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        in.useDelimiter("----------");

        String query = in.next();
        ArrayList<String> queryWords = Startup.getWords(query);

        String document = in.next();
        while (!document.matches("\\s+")) {
            ArrayList<String> words = Startup.getWords(document);
            double score = Startup.calcScore(new ArrayList<String>(queryWords), words);
            System.out.format("%.2f\n", score);
            document = in.next();
        }
    }
}