package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import edu.iis.mto.searcher.SearchResult;

class SimilarityFinderTest {
    public static final int[] empySequance = {};

    // Testy stanu
    @Test
    void checkIfReturnedOneIfBothSeqAreEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> SearchResult.builder().build()));
        assertEquals(1.0, similarityFinder.calculateJackardSimilarity(empySequance, empySequance));
    }

}
