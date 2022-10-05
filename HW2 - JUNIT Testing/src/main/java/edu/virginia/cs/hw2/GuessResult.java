package edu.virginia.cs.hw2;

import java.util.*;

public class GuessResult {
    public static final int GUESS_RESULT_ARRAY_SIZE = 5;
    private final LetterResult[] guessResult =
            {LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY, LetterResult.GRAY};
    private String answer; //always uppercase
    private String guess; //always uppercase

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer.toUpperCase();
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess.toUpperCase();
    }

    public LetterResult[] getGuessResult() {
        verifyAllFieldsAreInitialized();

        String[] answerArr = answer.split("");
        List<String> answerList = new ArrayList<String>(Arrays.asList(answerArr));

        WordleDictionary dict = new WordleDictionary();

        if(!dict.isLegalWordleWord(guess)){
            throw new IllegalWordException("Invalid Word: " + guess);
        }

        if (guess.equals(answer)) {
            return getCorrectAnswerArray();
        }
        assignGreen(answerList);
        assignYellowAndGrey(answerList);
        return guessResult;
    }

    private void assignGreen(List<String> answerList) {
        for(int i = 0; i < guess.length(); i++){
            if(guess.substring(i,i+1).equals(answer.substring(i,i+1))){
                guessResult[i] = LetterResult.GREEN;
                answerList.remove(guess.substring(i,i+1));
            }
        }
    }

    private void assignYellowAndGrey(List<String> answerList) {
        for(int i = 0; i < guess.length(); i++) {
            if (guessResult[i] != LetterResult.GREEN) {
                if (answer.contains(guess.substring(i, i + 1)) && answerList.contains(guess.substring(i, i + 1))) {
                    guessResult[i] = LetterResult.YELLOW;
                    answerList.remove(guess.substring(i, i + 1));
                } else {
                    guessResult[i] = LetterResult.GRAY;
                }
            }
        }
    }

    private void verifyAllFieldsAreInitialized() {
        if (guess == null) {
            throw new IllegalStateException("The guess field in GuessResult must be initialized before calling getGuessResult");
        }
        if (answer == null) {
            throw new IllegalStateException("The guess field in GuessResult must be initialized before calling getGuessResult");
        }
    }

    private LetterResult[] getCorrectAnswerArray() {
        fillGuessResultArrayWithOneColor();
        return guessResult;
    }

    private void fillGuessResultArrayWithOneColor() {
        Arrays.fill(guessResult, LetterResult.GREEN);
    }
}
