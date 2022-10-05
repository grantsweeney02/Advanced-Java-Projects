package edu.virginia.cs.hw2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryTest {
    private static final String ONE_WORD_DICTIONARY_FILENAME = "/one_word_dictionary.txt";
    private static final String WORDS_DICTIONARY_FILENAME = "/testDictionary.txt";
    WordleDictionary testDictionary;

    @BeforeEach
    public void setupTestDictionary() {
        testDictionary = new WordleDictionary();
    }

    @Test
    public void testOneWordDictionary() {
        WordleDictionary testDictionary = new WordleDictionary();
        InputStream inputStream = WordleDictionaryTest.class.getResourceAsStream(ONE_WORD_DICTIONARY_FILENAME);
        testDictionary.addWordsFromInputStream(inputStream);
        assertEquals(1, testDictionary.getDictionarySize());
        assertTrue(testDictionary.containsWord("BALDY"));
    }

    //https://en.wikipedia.org/wiki/List_of_Unicode_characters
    //Learned that chars were being converted to Ascii numerical value to compare.
    @Test
    @DisplayName("Testing Incorrect Characters")
    public void testNumbersIsIllegalWordleWord() {
        boolean testString = testDictionary.isLegalWordleWord("12345");
        assertFalse(testString);
    }

    @Test
    @DisplayName("Testing Length of Word")
    public void testTooLongIsLegalWordleWord() {
        boolean testString = testDictionary.isLegalWordleWord("abcdefghijklm");
        assertFalse(testString);
    }

    @Test
    @DisplayName("Testing No word")
    public void testNullWordIsLegalWordleWord() {
        boolean testString = testDictionary.isLegalWordleWord(null);
        assertFalse(testString);
    }

    @Test
    @DisplayName("Testing Upperbound of Characters")
    public void testUpperBoundIsLegalWordleWord() {
        boolean testString = testDictionary.isLegalWordleWord("Zebra");
        assertTrue(testString);
    }

    @Test
    @DisplayName("Testing Lowercase Letters")
    public void testLowerCaseLettersIsLegalWordleWord() {
        boolean testString = testDictionary.isLegalWordleWord("abcde");
        assertTrue(testString);
    }

    @Test
    @DisplayName("Testing Special Characters")
    public void testSpecialCharactersIsLegalWordleWord() {
        boolean testString = testDictionary.isLegalWordleWord(" %^~)");
        assertFalse(testString);
    }

    @Test
    @DisplayName("Testing Contains Method word")
    public void testAddAndContainsWord() {
        testDictionary.addWord("abcde");
        assertTrue(testDictionary.containsWord("ABCDE"));
        assertEquals(1,testDictionary.getDictionarySize());
    }
    @Test
    @DisplayName("Testing adding an Illegal Word")
    public void testAddAnIllegalWord() {
        assertThrows(IllegalWordException.class, () -> testDictionary.addWord("asdfadsfasdf"));
        assertEquals(0,testDictionary.getDictionarySize());
    }
    @Test
    @DisplayName("Testing Contains Method with no Word")
    public void testDoesNotContainWord() {
        assertFalse(testDictionary.containsWord("#$%^&"));
    }
}
