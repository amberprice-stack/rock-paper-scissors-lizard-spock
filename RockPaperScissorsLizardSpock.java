package rockpaperscissorslizardspock;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissorsLizardSpock {

    public enum Move {
        Rock, Paper, Scissors,Lizard, Spock;

        static {
            Rock.willBeat(Scissors);
            Rock.willBeat(Lizard);
            Paper.willBeat(Rock);
            Paper.willBeat(Spock);
            Scissors.willBeat(Paper);
            Scissors.willBeat(Lizard);
            Spock.willBeat(Scissors);
            Spock.willBeat(Rock);
            Lizard.willBeat(Paper);
            Lizard.willBeat(Spock);
        }
              
    private Move[] ibeat;
        private void willBeat(Move...moves) {
            ibeat = moves;
        }  
        
    public boolean beats(Move move) {
            return Arrays.binarySearch(ibeat, move) >= 0;
        }
    } 
    
  private static final String MOVEPROMPT = buildOptions();
    private static String buildOptions() {
        StringBuilder sb = new StringBuilder();
        sb.append("     -> ");
        for (Move m : Move.values()) {
            sb.append(m.ordinal() + 1).append(") ").append(m.name()).append(" ");
        }
        sb.append(" q) Quit");
        return sb.toString();
    }

    private static final char prompt(Scanner scanner, String prompt, char defval) {
        // prompt the user.
        System.out.print(prompt + ": ");
        // it would be nice to use a Console or something, but running from Eclipse there isn't one.
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return defval;
        }
        return input.charAt(0);
    }

    private static boolean playAgain(Scanner scanner) {
        return ('n' != prompt(scanner, "\nPlay Again (y/n)?", 'y'));
    }

    private static Move getHumanMove(Scanner scanner) {
        // loop until we get some valid input.
        do {
            char val = prompt(scanner, MOVEPROMPT, 'q');
            if ('q' == val) {
                return null;
            }
            int num = (val - '0') - 1;
            if (num >= 0 && num < Move.values().length) {
                return Move.values()[num];
            }
            System.out.println("Invalid move " + val);
        } while (true);
    }

    public static void main(String[] args) {
        final Random rand = new Random();
        final Move[] moves = Move.values();
        final Scanner scanner = new Scanner(System.in);
        int htotal = 0;
        int ctotal = 0;
        do {
            System.out.println("\nBest of 3.... Go!");
            int hscore = 0;
            int cscore = 0;
            bestofthree: do {
                final Move computer = moves[rand.nextInt(moves.length)];
                final Move human = getHumanMove(scanner);
                if (human == null) {
                    System.out.println("Human quits Best-of-3...");
                    break bestofthree;
                }
                if (human == computer) {
                    System.out.printf("  DRAW... play again!! (%s same as %s)\n", human, computer);
                } else if (human.beats(computer)) {
                    hscore++;
                    System.out.printf("  HUMAN beats Computer (%s beats %s)\n", human, computer);
                } else {
                    cscore++;
                    System.out.printf("  COMPUTER beats Human (%s beats %s)\n", computer, human);
                }
            } while (hscore != 2 && cscore != 2);

            if (hscore == 2) {
                htotal++;
            } else {
                ctotal++;
            }

            String winner = hscore == 2 ? "Human" : "Computer";
            System.out.printf("\n %s\n **** %s wins Best-Of-Three (Human=%d, Computer=%d - game total is Human=%d Computer=%d)\n",
                    winner.toUpperCase(), winner, hscore, cscore, htotal, ctotal);
        } while (playAgain(scanner));
        System.out.printf("Thank you for playing. The final game score was Human=%d and Computer=%d\n", htotal, ctotal);

    }
}