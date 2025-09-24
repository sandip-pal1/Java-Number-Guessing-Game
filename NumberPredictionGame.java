import java.util.Random;
import java.util.Scanner;

public class NumberPredictionGame {

    private static final Scanner scanner = new Scanner(System.in);
    private static int bestScore = Integer.MAX_VALUE; // lower is better

    public static void main(String[] args) {
        System.out.println("🎲 Welcome to Number Prediction Game!");
        boolean keepPlaying = true;

        while (keepPlaying) {
            int maxNumber = 100;   // Fixed range 1–100
            int maxAttempts = -1;  // -1 means unlimited

            // Ask user if they want an attempts limit
            System.out.print("Do you want to set a maximum number of attempts? (yes/no): ");
            if (readYesNo()) {
                System.out.print("Enter maximum attempts (e.g., 5): ");
                maxAttempts = readPositiveInt();
            }

            // Play one game round
            int attempts = playGame(maxNumber, maxAttempts);

            // Update best score if appropriate
            if (attempts > 0 && attempts < bestScore) {
                bestScore = attempts;
                System.out.println("🏆 New best score: " + bestScore + " attempts!");
            } else if (bestScore != Integer.MAX_VALUE) {
                System.out.println("Best score so far: " + bestScore + " attempts.");
            }

            // Ask to play again
            System.out.print("\nDo you want to play again? (yes/no): ");
            keepPlaying = readYesNo();
        }

        System.out.println("Thanks for playing! Goodbye 👋");
        scanner.close();
    }

    /**
     * Plays one round of the number guessing game.
     *
     * @param maxNumber   the upper bound (inclusive) for the random number
     * @param maxAttempts the maximum number of attempts allowed, or -1 for unlimited
     * @return number of attempts used if guessed correctly; -1 if user failed / quit
     */
    public static int playGame(int maxNumber, int maxAttempts) {
        Random random = new Random();
        int secret = random.nextInt(maxNumber) + 1; // 1..maxNumber
        int attempts = 0;

        System.out.println("\nI've picked a number between 1 and " + maxNumber + ".");
        if (maxAttempts > 0) {
            System.out.println("You have up to " + maxAttempts + " attempt(s) to guess it.");
        } else {
            System.out.println("You have unlimited attempts. Good luck!");
        }

        while (true) {
            System.out.print("Enter your guess (or type 0 to quit this round): ");
            int guess = readIntAllowZero();

            if (guess == 0) {
                System.out.println("You quit this round. The number was: " + secret);
                return -1;
            }

            if (guess < 1 || guess > maxNumber) {
                System.out.println("⚠️ Please guess a number between 1 and " + maxNumber + ".");
                continue;
            }

            attempts++;

            if (guess == secret) {
                System.out.println("🎉 Correct! You guessed it in " + attempts + " attempt(s).");
                return attempts;
            } else if (guess < secret) {
                System.out.println("⬆️ Too low.");
            } else {
                System.out.println("⬇️ Too high.");
            }

            if (maxAttempts > 0 && attempts >= maxAttempts) {
                System.out.println("❌ You've reached the maximum attempts. Game over.");
                System.out.println("The secret number was: " + secret);
                return -1;
            }

            System.out.println("Try again.");
        }
    }

    // Utility: read a positive integer (>=1)
    private static int readPositiveInt() {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val <= 0) {
                    System.out.print("Please enter a positive number: ");
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid positive number: ");
            }
        }
    }

    // Utility: read yes/no from user, returns true for yes, false for no
    private static boolean readYesNo() {
        while (true) {
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("yes") || line.equals("y")) return true;
            if (line.equals("no") || line.equals("n")) return false;
            System.out.print("Please type 'yes' or 'no': ");
        }
    }

    // Utility: read integer but allow 0 for quitting
    private static int readIntAllowZero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number (or 0 to quit): ");
            }
        }
    }
}
