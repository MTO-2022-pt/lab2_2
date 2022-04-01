package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    public static final int[] emptySeq = {};
    public static final int[] fiveElementSeq = {1, 2, 3, 4, 7};
    public static final int[] anotherFiveElementSeqTwoSimilar = {1, 3, 5, 6, 8};
    public static final int[] fourElementSeq = {10, 2, 3, 1};
    public static final int[] fourElementSeqDuplicates = {1, 2, 2, 3};

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
        assertEquals(0.0, simFinder.calculateJackardSimilarity(emptySeq, fiveElementSeq));
    }

    @Test
    void secondSeqIsEmptyExpectingZero() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(false).build()));
        assertEquals(0.0, simFinder.calculateJackardSimilarity(fiveElementSeq, emptySeq));
    }

    @Test
    void sameLenSeqExpectingPointTwentyFive() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> {
            if (elem == 1 || elem == 3) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));
        //2 similar
        assertEquals(0.25, simFinder.calculateJackardSimilarity(fiveElementSeq, anotherFiveElementSeqTwoSimilar));
    }

    @Test
    void diffLenSeqExpectingPointFifty() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> {
            if (elem == 1 || elem == 2 || elem == 3) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));
        // 3 similar
        assertEquals(0.5, simFinder.calculateJackardSimilarity(fiveElementSeq, fourElementSeq));
    }

    @Test
    void sameSeqExpectingOne() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> {
            if (elem == 1 ||elem == 2 || elem == 3 || elem == 10) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));
        //4 similar
        assertEquals(1, simFinder.calculateJackardSimilarity(fourElementSeq, fourElementSeq));
    }

    @Test //Bug?
    void sameLenSeqFirstWithDuplicatesExpectingSeqASimSeqBEqualsSeqBSimA() {
        SimilarityFinder simFinder = new SimilarityFinder(((elem, sequence) -> {
            if (elem == 1 ||elem == 2 || elem == 3) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));
        assertEquals(
                simFinder.calculateJackardSimilarity(fourElementSeqDuplicates,fourElementSeq),
                simFinder.calculateJackardSimilarity(fourElementSeq, fourElementSeqDuplicates)
        );
    }

    /*
    ### Testy zachowania ###
     */




}
