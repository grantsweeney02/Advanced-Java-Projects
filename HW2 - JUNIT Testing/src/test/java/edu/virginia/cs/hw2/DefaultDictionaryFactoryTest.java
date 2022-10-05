package edu.virginia.cs.hw2;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultDictionaryFactoryTest {
    DefaultDictionaryFactory testFactory;

    @BeforeEach
    public void setupTestFactory() {
        testFactory = new DefaultDictionaryFactory();
    }

    @Test
    public void testGetDefaultGuessesDictionary() {
        WordleDictionary defaultGuessesDictionary = testFactory.getDefaultGuessesDictionary();
        assertNotNull(defaultGuessesDictionary);
    }

    @Test
    @DisplayName("Guesses test")
    public void testGuessesInDefaultDictionary() {
        testFactory = new DefaultDictionaryFactory();
        WordleDictionary defaultGuessesDictionary = testFactory.getDefaultGuessesDictionary();
        int guesses = defaultGuessesDictionary.getDictionarySize();
        assertEquals(15920, guesses);
    }

    @Test
    @DisplayName("Answers test")
    public void testAnswersInDefaultDictionary() {
        testFactory = new DefaultDictionaryFactory();
        WordleDictionary defaultAnswersDictionary = testFactory.getDefaultAnswersDictionary();
        int answers =  defaultAnswersDictionary.getDictionarySize();
        assertEquals(2315, answers);
    }

}
