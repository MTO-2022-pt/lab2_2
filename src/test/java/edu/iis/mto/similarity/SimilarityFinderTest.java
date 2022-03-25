package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SimilarityFinder finderTrue;
    SimilarityFinder finderFalse;

    @BeforeEach
    public void createFinders() {
        finderTrue = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(true).build()));
        finderFalse = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(false).build()));
    }

    @Test
    public void shouldReturnOneWhenSequencesLengthsAreZero() {
        int[] seq1 = {};
        int[] seq2 = {};

        SimilarityFinder finder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return null;
            }
        });

        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    public void shouldReturnOneWhenBothSequencesHaveEqualLengthAndHaveSameElements() {
        int[] seq1 = {4, 5};
        int[] seq2 = {4, 5};

        double result = finderTrue.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    public void shouldReturnZeroWhenSequencesHaveEqualLengthAndHaveDifferentElements() {
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {4, 5, 6};

        double result = finderFalse.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, result);
    }
}
