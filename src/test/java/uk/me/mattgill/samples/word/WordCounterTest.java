package uk.me.mattgill.samples.word;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uk.me.mattgill.samples.word.controller.WordCounter;

public class WordCounterTest {

        @Test
        public void integrationTest() {

                final String result = new WordCounter() //
                                .read("Hello world & good morning. The date is 18/05/2016") //
                                .summarise();

                assertEquals("""
                                Word count = 9
                                Average word length = 4.556
                                Number of words of length 1 is 1
                                Number of words of length 2 is 1
                                Number of words of length 3 is 1
                                Number of words of length 4 is 2
                                Number of words of length 5 is 2
                                Number of words of length 7 is 1
                                Number of words of length 10 is 1
                                The most frequently occurring word length is 2, for word lengths of 4 & 5""", result);
        }

}
