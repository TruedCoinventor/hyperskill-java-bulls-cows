package bulls-cows;
import java.util.*;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final int MIN_CODE_LENGTH = 1;
    private static final int MIN_SYMBOL_RANGE = 10;
    private static final int MAX_SYMBOL_RANGE = 36;
    private static int codeLength;
    private static int symbolRange;

    public static void main(String[] args) {
        String secretCode = generateCode();
        System.out.println("Okay, let's start a game!");
        int turn = 1;
        String userGuess = "";
        while (!Objects.equals(userGuess, secretCode)) {
            System.out.printf("Turn %d:\n", turn);
            userGuess = INPUT.next();
            gradeAnswer(userGuess, secretCode);
            turn++;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static int getValidatedInput(String message, int min, int max) {
        int input = 0;
        boolean error = true;
        while (error) {
            System.out.println("Input " + message + ":");
            try {
                input = INPUT.nextInt();
                if (input < min || max < input) {
                    System.out.printf("Error, " + message + " must be between %d and %d!%n", min, max);
                } else {
                    error = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error, " + message +" is not a number!");
                INPUT.next();
            }
        }
        return input;
    }

    private static void setCodeLength() {
        codeLength = getValidatedInput("the length of the secret code", MIN_CODE_LENGTH, MAX_SYMBOL_RANGE);
    }

    private static void setSymbolRange() {
        symbolRange = getValidatedInput("the number of possible symbols in the code", MIN_SYMBOL_RANGE, MAX_SYMBOL_RANGE);
    }

    private static void setLengthAndRange() {
        setCodeLength();
        setSymbolRange();
        while (codeLength > symbolRange) {
            System.out.println("Error, the length of the secret code must not be more than symbol range!");
            setCodeLength();
            setSymbolRange();
        }
    }
    private static List<Character> generateCharacterPool() {
        List<Character> charPool = new ArrayList<>();
        for (char c = '0'; c <= '9'; c++) {
            charPool.add(c);
        }
        for (char c = 'a'; c < 'a' + (symbolRange - 10); c++) {
            charPool.add(c);
        }
        return charPool;
    }

    private static String generateCode() {
        setLengthAndRange();
        List<Character> charPool = generateCharacterPool();
        Collections.shuffle(charPool);

        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            codeBuilder.append(charPool.get(i));
        }
        System.out.println("The secret code is prepared: " + "*".repeat(codeLength) + " (0-9" + (symbolRange > 10 ? ", a-" + (char) ('a' + (symbolRange - 11)) : "") + ").");
        return codeBuilder.toString();
    }

    private static void gradeAnswer(String result, String expected) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < result.length(); i++) {
            if (result.contains(String.valueOf(expected.charAt(i)))) {
                cows++;
            }
            if (expected.charAt(i) == result.charAt(i)) {
                cows--;
                bulls++;
            }
        }
        String bullString = bulls == 1 ? " bull" : " bulls";
        String cowString = cows == 1 ? " cow" : " cows";
        if (bulls == 0 && cows == 0) {
            System.out.println("Grade: None\n");
        } else if (bulls > 0 && cows == 0) {
            System.out.println("Grade: " + bulls + " " + bullString + "\n");
        } else if (cows > 0 && bulls == 0) {
            System.out.println("Grade: " + cows + " " + cowString + "\n");
        } else {
            System.out.println("Grade: " + bulls + " " + bullString + " and " + cows + " " + cowString + "\n");
        }
    }
}