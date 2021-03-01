package uk.me.mattgill.samples.word.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordRecorderTest {

    private static final int WORD_COUNT = 10;

    private WordRecorder recorder;

    @BeforeEach
    public void setup() {
        this.recorder = new WordRecorder();

        // Record 9 random words in reverse length order
        for (int i = WORD_COUNT - 1; i >= 0; i--) {
            final int length = i + 1;
            final String randomWord = getRandomString(length);
            recorder.record(randomWord);
        }
    }

    @Test
    public void assert_word_count_correct() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(WORD_COUNT + i, recorder.getTotalCount(), "Incorrect word count");
            recorder.record(getRandomString(i + 1));
        }
    }

    @Test
    public void assert_average_word_length_correct() {
        final Random random = new Random();

        // Sum of series is n(n+1)/2
        double total = (WORD_COUNT * (WORD_COUNT + 1)) / 2;
        // Check the average is equal to (total word length) / (word count)
        assertEquals(total / WORD_COUNT, recorder.getAverageWordLength(), "Incorrect word length average");

        // Continue to add more words until there are 1000 (checking the average)
        for (int i = WORD_COUNT; i < 1000; i++) {
            // Get random length between 1 and 16
            final int length = random.nextInt(15) + 1;
            total += length;

            // Record a random word with that length
            final String randomWord = getRandomString(length);
            recorder.record(randomWord);

            // Check the average is equal to (total word length) / (word count)
            assertEquals(total / (i + 1), recorder.getAverageWordLength(), "Incorrect word length average");
        }
    }

    @Test
    public void assert_word_lengths_unique() {
        final var wordLengths = recorder.getWordLengths();
        final Set<?> wordLengthSet = new HashSet<>(wordLengths);
        assertEquals(wordLengthSet.size(), wordLengths.size(), "Word lengths were not unique");
        assertEquals(WORD_COUNT, wordLengths.size(), "Wrong number of word lengths");
    }

    @Test
    public void assert_word_lengths_ordered() {
        final Iterator<Integer> wordLengthIterator = recorder.getWordLengths().iterator();

        // Check iterator returns correct order
        for (int i = 0; i < WORD_COUNT; i++) {
            final int length = i + 1;
            final Integer recordedLength = wordLengthIterator.next();

            assertEquals(length, recordedLength, "Wrong order of lengths");
        }
    }

    @Test
    public void assert_word_lengths_correct() {
        // Test existing count of each word
        for (int i = 0; i < WORD_COUNT; i++) {
            assertEquals(1, recorder.getWordsOfLength(i + 1), "Incorrect number of words with length " + i);
        }

        // Add a new word to test the value increases
        recorder.record("Hello");
        assertEquals(2, recorder.getWordsOfLength(5), "Incorrect number of words with length 5");
    }

    @Test
    public void assert_highest_frequency_word_lengths_ordered() {
        var frequencyList = recorder.getHighestFrequencyWordLengths();
        var frequencyIterator = frequencyList.iterator();

        // Check iterator returns correct order
        for (int i = 0; i < WORD_COUNT; i++) {
            final int length = i + 1;
            final Integer recordedLength = frequencyIterator.next();

            assertEquals(length, recordedLength, "Wrong order of frequency list");
        }

        // Add a new word to test the value changes
        recorder.record("Hello");
        frequencyList = recorder.getHighestFrequencyWordLengths();
        frequencyIterator = frequencyList.iterator();

        assertEquals(5, frequencyIterator.next(), "Highest frequency word length not found");

        // Add a new highest frequency word to test the value changes
        recorder.record("Test");
        frequencyList = recorder.getHighestFrequencyWordLengths();
        frequencyIterator = frequencyList.iterator();

        assertEquals(4, frequencyIterator.next(), "Highest frequency word length not found");
        assertEquals(5, frequencyIterator.next(), "Highest frequency word length not found");
    }

    @Test
    public void assert_highest_frequency_word_lengths_correct() {
        // Test existing highest frequency words
        var frequencyList = recorder.getHighestFrequencyWordLengths();

        assertEquals(WORD_COUNT, frequencyList.size(), "Incorrect number of highest frequency word lengths");
        for (int i = 0; i < WORD_COUNT; i++) {
            assertTrue(frequencyList.contains(i + 1), i + " wasn't in the highest frequency word lengths");
        }

        // Add a new word to test the value changes
        recorder.record("Test");
        frequencyList = recorder.getHighestFrequencyWordLengths();

        assertEquals(1, frequencyList.size(), "Highest frequency word length didn't update");
        assertTrue(frequencyList.contains(4), "Highest frequency word length not found");

        // Add a new highest frequency word to test the value changes
        recorder.record("Hello");
        frequencyList = recorder.getHighestFrequencyWordLengths();

        assertEquals(2, frequencyList.size(), "Highest frequency words didn't update");
        assertTrue(frequencyList.contains(4), "Highest frequency word length not found");
        assertTrue(frequencyList.contains(5), "Highest frequency word length not found");
    }

    //// Utility methods

    private static String getRandomString(int length) {
        final String randomString = UUID.randomUUID().toString();
        if (length < 1) {
            length = 1;
        } else if (length > randomString.length()) {
            length = 1 + (length % randomString.length());
        }
        return randomString.substring(0, length);
    }

}
