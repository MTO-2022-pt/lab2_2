package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iis.mto.searcher.SearchResult;

class SimilarityFinderTest {
    public static final int[] emptySequance = {};
    public static final int[] fiveElementSequance = {1, 2, 3, 4, 5};
    
    SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> SearchResult.builder().build()));
    // Testy stanu
    @Test
    void checkIfReturnedOneIfBothSeqAreEmpty() {
        assertEquals(1.0, similarityFinder.calculateJackardSimilarity(emptySequance, emptySequance));
    }

    @Test
    void checkIfReturnedZeroIfFirstSeqIsEmpty() {
        assertEquals(0.0, similarityFinder.calculateJackardSimilarity(emptySequance, fiveElementSequance));
    }

    @Test
    void checkIfReturnedZeroIfSecondSeqIsEmpty() {
        assertEquals(0.0, similarityFinder.calculateJackardSimilarity(fiveElementSequance, emptySequance));
    }

}
