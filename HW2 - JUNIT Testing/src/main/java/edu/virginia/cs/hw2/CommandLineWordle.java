package edu.virginia.cs.hw2;

import java.util.*;

public class CommandLineWordle {
    private Scanner scanner;
    private GameState game;

    public static void main(String[] args) {
        CommandLineWordle play = new CommandLineWordle();
        play.run();
    }

    public void run() {
        initializeWordle();
        do {
            playGameOfWordle();
        } while (playAgain());
        finalizeWordle();
    }

    public void playGameOfWordle() {
        println("Here we go!");
        game = new GameState();

        while(!game.isGameOver()) {
            println("You have " + game.getRemainingGuesses() + " guesses remaining!");
            getAndHandleUserGuess();
        }
        generateWinOrLossMessage();
    }

    private void initializeWordle() {
        println("Welcome To Wordle!");
        initializeScanner();
        preloadDefaultDictionaries();
    }

    private void generateWinOrLossMessage() {
        if (game.isWin()) {
            System.out.println("Congratulations! You won in " + game.getGuessCount() + " guesses!");
        } else if (game.isLoss()) {
            System.out.println("Sorry, you are out of guesses... The word was " + game.getAnswer());
        }
    }

    private void getAndHandleUserGuess() {
        String guess = prompt("Enter a 5-letter word> ");
        try {
            LetterResult[] result = game.submitGuess(guess);
            System.out.println(getResultString(guess, result));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Try again!");
        }
    }

    private String getResultString(String guess, LetterResult[] result) {
        StringBuilder wordWithColoredLetters = new StringBuilder();
        for (int letterIndex = 0; letterIndex < result.length; letterIndex++) {
            char guessLetter = guess.charAt(letterIndex);
            LetterResult letterResult = result[letterIndex];
            getColorfulPrintMessage(wordWithColoredLetters, guessLetter, letterResult);
        }
        return wordWithColoredLetters.toString();
    }

    private void getColorfulPrintMessage(StringBuilder wordWithColoredLetters, char guessLetter, LetterResult letterResult) {
        String coloredLetterResult = getColorfulLetterResult(guessLetter, letterResult);
        wordWithColoredLetters.append(coloredLetterResult);
    }

    private String getColorfulLetterResult(char guessLetter, LetterResult letterResult) {
        return switch (letterResult) {
            case GRAY -> getGrayLetter(guessLetter);
            case GREEN -> getGreenLetter(guessLetter);
            case YELLOW -> getYellowLetter(guessLetter);
        };
    }

    private String getGreenLetter(char guessLetter) {
        return "\033[1;42m" + guessLetter + "\033[0m";
    }

    private String getYellowLetter(char guessLetter) {
        return "\033[1;43m" + guessLetter + "\033[0m";
    }

    private String getGrayLetter(char guessLetter) {
        return "\033[1;47m" + guessLetter + "\033[0m";
    }



    private boolean playAgain() {
        while (true) {
            String userResponse = prompt("Would you like to play again? (Y/N) > ");
            if (userResponse.toUpperCase().startsWith("Y")) {
                return true;
            } else if (userResponse.toUpperCase().startsWith("N")) {
                return false;
            } else {
                println("I'm sorry, that was not a valid input. Please try again.");
            }
        }
    }

    private void finalizeWordle() {
        println("Thank you for playing!");
        scanner.close();
    }

    private void println(Object message) {
        System.out.println(message);
    }

    private void initializeScanner() {
        scanner = new Scanner(System.in);
    }

    private void preloadDefaultDictionaries() {
        println("Loading dictionaries...");
        DefaultDictionaryFactory factory = new DefaultDictionaryFactory();
        factory.loadDefaultDictionaries();
    }

    private String prompt(String s) {
        println(s);
        return scanner.nextLine();
    }
}
