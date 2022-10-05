package edu.virginia.cs.hw2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private GameState testGame;
    private int guessNum;
    private GameState.GameStatus gameStatus;
    private WordleDictionary wordleDictionary;

    @BeforeEach
    public void initAll(){
        guessNum = 5;
        gameStatus = GameState.GameStatus.PLAYING;
        wordleDictionary = new WordleDictionary();
    }
    @Test
    public void testConstructorWithIllegalAnswer() {
        assertThrows(IllegalWordException.class,
                () -> new GameState("QZXYX"));
    }
    @Test
    public void testIfGameEndsWhenGuessIs5IfWrong() {
        wordleDictionary.addWordsFromFileByFilename("src/main/resources/dictionary.txt");
        testGame = new GameState("Basic", wordleDictionary, guessNum, gameStatus);
        testGame.submitGuess("Happy");
        assertTrue(testGame.isGameOver());
    }
    @Test
    public void testIfGameEndsWhenGuessIs7() {
        guessNum = 7;
        wordleDictionary.addWordsFromFileByFilename("src/main/resources/dictionary.txt");
        testGame = new GameState("Basic", wordleDictionary, guessNum, gameStatus);
        assertThrows(GameAlreadyOverException.class,
                () -> testGame.submitGuess("Hello"));
    }

    @Test
    public void testIfGameDoesntEndsWhenGuessIsLessThan5() {
        guessNum = 3;
        wordleDictionary.addWordsFromFileByFilename("src/main/resources/dictionary.txt");
        testGame = new GameState("Basic", wordleDictionary, guessNum, gameStatus);
        testGame.submitGuess("Happy");
        assertFalse(testGame.isGameOver());
    }

     @Test
     public void testIfWhenValidGuessThenGuessCounterIncreases() {
        guessNum = 3;
        wordleDictionary.addWordsFromFileByFilename("src/main/resources/dictionary.txt");
        testGame = new GameState("Basic", wordleDictionary, guessNum, gameStatus);
        testGame.submitGuess("Happy");
        assertEquals(4,testGame.getGuessCount());
     }

    @Test
    public void testIfThrowsExceptionForIllegalWord() {
        guessNum = 4;
        wordleDictionary.addWordsFromFileByFilename("src/main/resources/dictionary.txt");
        testGame = new GameState("Basic", wordleDictionary, guessNum, gameStatus);
        testGame.submitGuess("Happy");
        assertThrows(IllegalWordException.class,
                () -> testGame.submitGuess("FSJNS"));
    }


    // test for if num of guesses is at 7 the game stops
    //  if the answer we are trying to guess is valid
    // if the state changes
    // if the input guess is not valid throw illegal word exception
    // check it the num of guess increases when invalid answer -- it
    // Cannot submit if the game is over --  should throw gameAlreadyOverException

}