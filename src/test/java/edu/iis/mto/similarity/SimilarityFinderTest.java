package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    public static final int[] emptySeq = {};
    public static final int[] fourElementSeq = {1, 2, 3, 4};
    public static final int[] anotherFourElementSeqTwoSimilar = {1, 3, 5, 6};

    /*
    ### Testy stanu ###
     */

    @Test
    void bothSeqAreEmptyExpectingOne() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        assertEquals(1.0, simFinder.calculateJackardSimilarity(emptySeq, emptySeq));
    }

    @Test
    void firstSeqIsEmptyExpectingZero() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(false).build()));
        assertEquals(0.0, simFinder.calculateJackardSimilarity(emptySeq, fourElementSeq));
    }

    @Test
    void secondSeqIsEmptyExpectingZero() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(false).build()));
        assertEquals(0.0, simFinder.calculateJackardSimilarity(fourElementSeq, emptySeq));
    }

}
