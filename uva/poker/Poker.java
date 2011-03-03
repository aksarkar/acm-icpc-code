/**
   UVa 10315
   PC 110202

   UNSOLVED
 */
import java.util.*;

class Poker {
    final static int HAND_SIZE = 5;
    
    public static boolean straight(Card[] hand) {
        for (int i = 1; i < HAND_SIZE; i++)
            if (hand[i].value != hand[i - 1].value + 1)
                return false;
        return true;
    }

    public static boolean flush(Card[] hand) {
        for (int i = 1; i < HAND_SIZE; i++)
            if (hand[i].suit != hand[0].suit)
                return false;
        return true;
    }

    public static int[] count(Card[] hand) {
        int[] counts = new int[14];
        for (int i = 0; i < 13; i++)
            counts[i] = 0;
        for (int i = 0; i < HAND_SIZE; i++)
            counts[hand[i].value]++;
        return counts;
    }

    public static String highCard(Card[] black, Card[] white) {
        int i = HAND_SIZE - 1;
        while (black[i].value == white[i].value && i > 0) {
            i--;
        }
        if (black[i].value > white[i].value)
            return "Black wins.";
        else if (black[i].value < white[i].value)
            return "White wins.";
        else
            return "Tie.";
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Card[] black = new Card[HAND_SIZE];
        Card[] white = new Card[HAND_SIZE];

        while (in.hasNext()) {
            for (int i = 0; i < HAND_SIZE; i++)
                black[i] = new Card(in.next());
            for (int i = 0; i < HAND_SIZE; i++)
                white[i] = new Card(in.next());
        }

        Arrays.sort(black);
        Arrays.sort(white);

        boolean blackStraight = straight(black);
        boolean whiteStraight = straight(white);
        boolean blackFlush = flush(black);
        boolean whiteFlush = flush(white);
        boolean blackStraightFlush = blackStraight && blackFlush;
        boolean whiteStraightFlush = whiteStraight && whiteFlush;

        int[] blackCounts = count(black);
        int[] whiteCounts = count(white);

        boolean blackFourOf = false, whiteFourOf = false, 
            blackFullHouse = false, whiteFullHouse = false,
            blackThreeOf = false, whiteThreeOf = false,
            blackTwoPair = false, whiteTwoPair = false,
            blackPair = false, whitePair = false;
        
        for (int i = 0; i < blackCounts.length; i++) {
            if (blackCounts[i] == 4)
                blackFourOf = true;
            else if (blackCounts[i] == 3) {
                blackThreeOf = true;
                if (blackPair)
                    blackFullHouse = true;
            }
            else if (blackCounts[i] == 2) {
                if (blackPair)
                    blackTwoPair = true;
                blackPair = true;
            }
        }
        
        for (int i = 0; i < whiteCounts.length; i++) {
            if (whiteCounts[i] == 4)
                whiteFourOf = true;
            else if (whiteCounts[i] == 3) {
                whiteThreeOf = true;
                if (whitePair)
                    whiteFullHouse = true;
            }
            else if (whiteCounts[i] == 2) {
                if (whitePair)
                    whiteTwoPair = true;
                whitePair = true;
            }
        }

        if (blackStraightFlush && whiteStraightFlush) {
            System.out.println(highCard(black, white));
        }
        else if (blackStraightFlush && !(whiteStraightFlush))
            System.out.println("Black wins.");
        else if (whiteStraightFlush && !(blackStraightFlush))
            System.out.println("White wins.");
        else if (blackFourOf && whiteFourOf) {
            int blackFour = 0, blackOne = 0, whiteFour = 0, whiteOne = 0;
            for (int i = 0; i < blackCounts.length; i++) {
                if (blackCounts[i] == 4)
                    blackFour = i;
                if (blackCounts[i] == 1)
                    blackOne = i;
                if (whiteCounts[i] == 4)
                    whiteFour = i;
                if (blackCounts[i] == 1)
                    whiteOne = i;
            }
            if (blackFour > whiteFour)
                System.out.println("Black wins.");
            else if (blackFour < whiteFour)
                System.out.println("White wins.");
            else
                System.out.println("Tie.");
        }
        else if (blackFourOf && !whiteFourOf)
            System.out.println("Black wins.");
        else if (whiteFourOf && !blackFourOf)
            System.out.println("White wins.");
        else if (blackFlush && whiteFlush) {
            System.out.println(highCard(black, white));
        }
        else if (blackFlush && !whiteFlush)
            System.out.println("Black wins.");
        else if (whiteFlush && !blackFlush)
            System.out.println("White wins.");
        else if (blackStraight && whiteStraight) {
            
        }
    }
}

class Card implements Comparable<Card> {
    int value;
    char suit;

    public Card(String s) {
        if (s.charAt(0) == 'J')
            this.value = 10;
        else if (s.charAt(0) == 'Q')
            this.value = 11;
        else if (s.charAt(0) == 'K')
            this.value = 12;
        else if (s.charAt(0) == 'A')
            this.value = 13;
        else
            this.value = Integer.parseInt(s.substring(0, 1));
        this.suit = s.charAt(1);
    }

    @Override
    public int compareTo(Card other) {
        return this.value - other.value;
    }
}