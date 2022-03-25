package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    public static final int[] emptySeq = {};

    /*
    ### Testy stanu ###
     */

    @Test
    void bothSeqAreEmptyExpectingOne() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        assertEquals(1.0, simFinder.calculateJackardSimilarity(emptySeq, emptySeq));
    }

}
