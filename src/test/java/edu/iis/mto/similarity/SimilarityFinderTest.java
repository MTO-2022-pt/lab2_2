package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;


import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    public static final int[] emptySequance = {};
    public static final int[] fiveElementSequance = {1, 2, 3, 4, 5};
    public static final int[] secondFiveElementSequance = {1, 2, 7, 8, 9};
    public static final int[] fourElementSeq = {8, 2, 4, 1};
    public static final int[] fourDifferentSequance = {3, 5, 6, 7};
    
    // Testy stanu
    @Test
    void checkIfReturnedOneIfBothSeqAreEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> SearchResult.builder().build()));
        assertEquals(1.0, similarityFinder.calculateJackardSimilarity(emptySequance, emptySequance));
    }

    @Test
    void checkIfReturnedZeroIfFirstSeqIsEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> SearchResult.builder().build()));
        assertEquals(0.0, similarityFinder.calculateJackardSimilarity(emptySequance, fiveElementSequance));
    }

    @Test
    void checkIfReturnedZeroIfSecondSeqIsEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> SearchResult.builder().build()));
        assertEquals(0.0, similarityFinder.calculateJackardSimilarity(fiveElementSequance, emptySequance));
    }

    @Test
    void sameLengthSequanceExpectingPointTwentyFive() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> {
            if (elem == 1 || elem == 2) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));
        
        assertEquals(0.25, similarityFinder.calculateJackardSimilarity(fiveElementSequance, secondFiveElementSequance));
    }

    @Test
    void differentLengthSequanceExpectingPointFive() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> {
            if (elem == 1 || elem == 2 || elem == 4) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));
        
        assertEquals(0.5, similarityFinder.calculateJackardSimilarity(fiveElementSequance, fourElementSeq));
    }

    @Test
    void sameSequanceExpectingOne() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> {
            if (elem == 1 || elem == 2 || elem == 4 || elem == 8) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));
        
        assertEquals(1, similarityFinder.calculateJackardSimilarity(fourElementSeq, fourElementSeq));
    }

    @Test
    void differentSequanceExpectingZero() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> SearchResult.builder().withFound(false).build()));
        
        assertEquals(0, similarityFinder.calculateJackardSimilarity(fourElementSeq, fourDifferentSequance));
    }


    // Testy zachowania
    @Test
    void  sameLengthSequanceExpectingInvokingFiveTimes() {
        final int[] count = {0};
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> {
            count[0]++;
            if (elem == 1 || elem == 2 || elem == 3 || elem == 4 || elem == 5) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));

        similarityFinder.calculateJackardSimilarity(fiveElementSequance, secondFiveElementSequance);
        assertEquals(fiveElementSequance.length, count[0]);
    }

    @Test
    void  differentLengthSequanceExpectingInvokingFourTimes() {
        final int[] count = {0};
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> {
            count[0]++;
            if (elem == 1 || elem == 2 || elem == 4) return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        }));

        similarityFinder.calculateJackardSimilarity(fourElementSeq, fiveElementSequance);
        assertEquals(fourElementSeq.length, count[0]);
    }

    @Test
    void  emptySequanceExpectingInvokingZeroTimes() {
        final int[] count = {0};
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequance) -> {
            count[0]++;
            return SearchResult.builder().withFound(false).build();
        }));

        similarityFinder.calculateJackardSimilarity(emptySequance, emptySequance);
        assertEquals(emptySequance.length, count[0]);
    }
}
