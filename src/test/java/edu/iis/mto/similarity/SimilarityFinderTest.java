package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeAll;
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
    public void zeroLengthSequences() {
        int[] seq1 = {};
        int[] seq2 = {};

        SimilarityFinder finder = new SimilarityFinder((elem, sequence) -> null);

        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    public void identicalSequences(){
        int[] seq1 = {4, 5};
        int[] seq2 = {4, 5};

        double result = finderTrue.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    public void completelyDifferentElementsSameLength() {
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {4, 5, 6};

        double result = finderFalse.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0, result);
    }

    @Test
    public void firstSequenceEmpty() {
        int[] seq1 = {};
        int[] seq2 = {1, 2, 3};

        double result = finderFalse.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0, result);
    }

    @Test
    public void secondSequenceEmpty() {
        int[] seq1 = {3, 6};
        int[] seq2 = {};

        double result = finderFalse.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0, result);
    }
}
