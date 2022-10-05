package edu.virginia.cs.hw2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuessResultTest {

    private GuessResult testGuessResult;

    @BeforeEach
    public void setupTestResult() {
        testGuessResult = new GuessResult();
    }

    @Test
    @DisplayName("GuessResult constructor verification.")
    public void testThrowsErrorOnUninitializedFields() {
        assertNull(testGuessResult.getGuess());
        assertNull(testGuessResult.getAnswer());
        assertThrows(IllegalStateException.class, () ->
                testGuessResult.getGuessResult());
    }

    @Test
    @DisplayName("Correct answer Guess Result Test")
    public void testLetterResultCorrectAnswer() {
        givenInputGuessAndAnswer();

        LetterResult[] guessResult = testGuessResult.getGuessResult();

        assertEquals("GGGGG", getLetterResultArrayAsString(guessResult));
    }

    private void givenInputGuessAndAnswer() {
        testGuessResult.setGuess("MATCH");
        testGuessResult.setAnswer("MATCH");
    }

    @Test
    @DisplayName("Helper function that converts the LetterResult[] in String for ease of use")
    public void testGetLetterResultArrayAsString() {
        LetterResult[] testArray = {LetterResult.GREEN, LetterResult.GRAY, LetterResult.YELLOW,LetterResult.GREEN, LetterResult.YELLOW};
        assertEquals("GgYGY", getLetterResultArrayAsString(testArray));
    }

    private String getLetterResultArrayAsString(LetterResult[] letterResultArray) {
        StringBuilder builder = new StringBuilder(5);
        for (LetterResult letterResult : letterResultArray) {
            char letterResultCharacter = getCharacterForLetterResult(letterResult);
            builder.append(letterResultCharacter);
        }
        return builder.toString();
    }

    @Test
    @DisplayName("LetterResult.Green -> `G`")
    public void testGetCharacterForLetterResult_Green() {
        assertEquals('G', getCharacterForLetterResult(LetterResult.GREEN));
    }

    @Test
    @DisplayName("LetterResult.Yellow -> `Y`")
    public void testGetCharacterForLetterResult_Yellow() {
        assertEquals('Y', getCharacterForLetterResult(LetterResult.YELLOW));
    }

    @Test
    @DisplayName("LetterResult.Gray -> `g`")
    public void testGetCharacterForLetterResult_Gray() {
        assertEquals('g', getCharacterForLetterResult(LetterResult.GRAY));
    }

    private static char getCharacterForLetterResult(LetterResult letterResult) {
        return switch(letterResult) {
            case GRAY -> 'g';
            case GREEN -> 'G';
            case YELLOW -> 'Y';
        };
    }

    @Test
    public void testCorrectGuess() {
        GuessResult guess = new GuessResult();
        guess.setAnswer("happy");
        guess.setGuess("happy");
        LetterResult[] guessArray = guess.getGuessResult();
        assertEquals("GGGGG", getLetterResultArrayAsString(guessArray));
    }

    @Test
    public void testPartiallyCorrectGuess() {
        GuessResult guess = new GuessResult();
        guess.setAnswer("HAPPY");

        guess.setGuess("party");
        LetterResult[] guessArray = guess.getGuessResult();
        assertEquals("YGggG", getLetterResultArrayAsString(guessArray));
    }
    @Test
    public void testIllegalWord() {
        GuessResult guess = new GuessResult();
        guess.setGuess("ASDFADSF");

        guess.setAnswer("happy");
        assertThrows(IllegalWordException.class, ()->guess.getGuessResult());
    }
    //Repeat rules: Wordle handles repeat letters by
    @Test
    public void testRepeatLettersInWord() {
        GuessResult guess = new GuessResult();
        guess.setAnswer("Happy");

        guess.setGuess("poppy");
        LetterResult[] guessArray = guess.getGuessResult();
        assertEquals("ggGGG", getLetterResultArrayAsString(guessArray));
    }
    
    @Test
    public void testRepeatLettersInWordGreenAfterYellowCase() {
        GuessResult guess = new GuessResult();
        guess.setAnswer("ZRZqz");
        guess.setGuess("ZzvZZ");
        LetterResult[] guessArray = guess.getGuessResult();
        assertEquals("GYggG", getLetterResultArrayAsString(guessArray));
    }

    @Test
    public void testRepeatLettersInWordGreenBeforeAndAfterYellowCase() {
        GuessResult guess = new GuessResult();
        guess.setAnswer("xzzzx");
        guess.setGuess("zvzvz");
        LetterResult[] guessArray = guess.getGuessResult();
        assertEquals("YgGgY", getLetterResultArrayAsString(guessArray));
    }

    @Test
    public void testRepeatLettersInWordGreenGreyAndYellowCase() {
        GuessResult guess = new GuessResult();
        guess.setAnswer("AABBB");
        guess.setGuess("BACAA");
        LetterResult[] guessArray = guess.getGuessResult();
        assertEquals("YGgYg", getLetterResultArrayAsString(guessArray));
    }

    @Test
    public void testRepeatLettersInWordGreyAndYellowCase() {
        GuessResult guess = new GuessResult();
        guess.setAnswer("BBBAA");
        guess.setGuess("AAABB");
        LetterResult[] guessArray = guess.getGuessResult();
        assertEquals("YYgYY", getLetterResultArrayAsString(guessArray));
    }

}