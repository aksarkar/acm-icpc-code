/**
   Pacific Northwest Regional 1998 Problem 6
 */
import java.util.*;

class ChutesLadders {
    public static int play(ArrayList<Integer> rolls, int players, 
                            Map<Integer, Integer> chutesLadders, 
                            Set<Integer> loseTurnSquares, 
                            Set<Integer> extraTurnSquares) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < players; i++)
            positions.add(0);

        LinkedList<Integer> stack = new LinkedList<Integer>();
        stack.add(0, 0);

        int currentPlayer = 0, currentRoll = 0, newPos = 0, next = 0;
        while (!positions.contains(100)) {
            currentPlayer = stack.remove(0);
            currentRoll = rolls.remove(0);
            newPos = positions.get(currentPlayer) + currentRoll;
            next = (currentPlayer == players - 1) ? 0 : currentPlayer + 1;

            if (chutesLadders.containsKey(newPos)) {
                newPos = chutesLadders.get(newPos);
                positions.set(currentPlayer, newPos);
                if (stack.isEmpty())
                    stack.add(0, next);
            }
            else if (loseTurnSquares.contains(newPos)) {
                stack.add(0, next);
                stack.add(0, next);
            }
            else if (extraTurnSquares.contains(newPos)) {
                positions.set(currentPlayer, newPos);
                stack.add(0, currentPlayer);
            }
            else if (newPos > 100) {
                if (stack.isEmpty())
                    stack.add(0, next);
            }
            else {
                positions.set(currentPlayer, newPos);
                if (stack.isEmpty())
                    stack.add(0, next);
            }
        }
        return ++currentPlayer;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        ArrayList<Integer> rolls = new ArrayList<Integer>();
        for (int i = in.nextInt(); i != 0; i = in.nextInt())
            rolls.add(i);
        while (true) {
            int players = in.nextInt();
            if (players == 0)
                return;
        
            HashMap<Integer, Integer> chutesLadders = 
                new HashMap<Integer, Integer>();
            while (true) {
                int start = in.nextInt();
                int end = in.nextInt();
                if (start == 0 && end == 0)
                    break;
                chutesLadders.put(start, end);
            }

            HashSet<Integer> loseTurnSquares = new HashSet<Integer>();
            HashSet<Integer> extraTurnSquares = new HashSet<Integer>();
            for (int i = in.nextInt(); i != 0; i = in.nextInt()) {
                if (i < 0)
                    loseTurnSquares.add(Math.abs(i));
                else
                    extraTurnSquares.add(i);
            }

            System.out.println(ChutesLadders.play(new ArrayList<Integer>(rolls),
                                                  players, chutesLadders,
                                                  loseTurnSquares, extraTurnSquares));
        }
    }
}