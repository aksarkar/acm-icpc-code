import java.util.*;
import java.util.regex.*;

/**
   ACM Midatlantic Regional 2010 Problem E
 */
class Abstract {
  static final Pattern SENT_PATT = Pattern.compile("\\s*(.*?[.!?])",
                                                   Pattern.DOTALL);
  static final Pattern WORD_PATT = Pattern.compile("\\w+");

  static String topicSentence(String paragraph) {
    List<String> sentences = new ArrayList<String>();
    List<Set<String>> words = new ArrayList<Set<String>>();
    Matcher sentm = SENT_PATT.matcher(paragraph);
    while (sentm.find()) {
      String sentence = sentm.group(1);
      sentences.add(sentence);
      Matcher wordm = WORD_PATT.matcher(sentence);
      Set<String> sentwords = new HashSet<String>();
      while (wordm.find()) {
        sentwords.add(wordm.group().toLowerCase());
      }
      words.add(sentwords);
    }

    if (sentences.size() < 3) {
      return "";
    }

    int bestIndex = 0;
    int bestScore = 0;
    for (int i = 0; i < words.size(); i++) {
      int score = 0;
      for (String w : words.get(i)) {
        boolean found = false;
        for (int j = i + 1; !found && j < words.size(); j++) {
          found = words.get(j).contains(w);
        }
        score += found ? 1 : 0;
      }
      if (score > bestScore) {
        bestIndex = i;
        bestScore = score;
      }
    }
    return sentences.get(bestIndex);
  }
  
  static String buildAbstract(String article) {
    StringBuilder result = new StringBuilder();
    for (String paragraph : article.split("\n\n")) {
      String t = topicSentence(paragraph);
      if (!t.isEmpty()) {
        result.append(t);
        result.append('\n');
      }
    }
    if (result.length() > 0) {
      result.deleteCharAt(result.length() - 1);
    }
    return result.toString();
  }
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String line = in.nextLine();
    while (!line.equals("******")) {
      StringBuilder article = new StringBuilder(line);
      line = in.nextLine();
      while (!line.equals("***") && !line.equals("******")) {
        article.append('\n');
        article.append(line);
        line = in.nextLine();
      }
      if (line.equals("***")) {
        line = in.nextLine();
      }
      System.out.println(buildAbstract(article.toString()));
      System.out.println("======");
    }
  }
}
