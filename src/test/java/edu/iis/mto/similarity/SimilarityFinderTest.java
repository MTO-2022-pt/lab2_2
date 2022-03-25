package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    SimilarityFinder finderTrue;
    SimilarityFinder finderFalse;
    SearchResult found;
    SearchResult notFound;

    @BeforeEach
    public void setUp() {
        finderTrue = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(true).build()));
        finderFalse = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(false).build()));
        found = SearchResult.builder().withFound(true).build();
        notFound = SearchResult.builder().withFound(false).build();
    }

    @Test
    public void zeroLengthSequences() {
        int[] seq1 = {};
        int[] seq2 = {};

        double result = finderTrue.calculateJackardSimilarity(seq1, seq2);
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
    public void completelyDifferentElements() {
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

    @Test
    public void totalLengthTenTwoSameElements() {
        int[] seq1 = {5, 6, 7, 8};
        int[] seq2 = {1, 2, 3, 4, 5, 6};

        SimilarityFinder finder = new SimilarityFinder((elem, sequence) -> {
            switch (elem) {
                case 5: case 6: return found;
                case 7: case 8: return notFound;
                default: return null;
            }
        });

        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.25d, result);
    }

    @Test
    public void totalLengthNineThreeSameElements() {
        int[] seq1 = {3, 4, 5};
        int[] seq2 = {1, 2, 3, 4, 5, 6};

        SimilarityFinder finder = new SimilarityFinder((elem, sequence) -> elem == 3 || elem == 4 || elem == 5 ? found : null);

        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.5d, result);
    }

    @Test
    public void invokeFourTimes() {
        int[] seq1 = {3, 4, 5, 6};
        int[] seq2 = {3, 4, 5, 6};
        final int[] invokeCounter = {0};

        SequenceSearcher searcherMock = (elem, sequence) -> {
            invokeCounter[0]++;
            return found;
        };

        SimilarityFinder finder = new SimilarityFinder(searcherMock);
        finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(4, invokeCounter[0]);
    }

    @Test
    public void searchMethodNotInvoked() {
        int[] seq1 = {};
        int[] seq2 = {};
        final int[] invokeCounter = {0};

        SequenceSearcher searcherMock = (elem, sequence) -> {
            invokeCounter[0]++;
            return null;
        };

        SimilarityFinder finder = new SimilarityFinder(searcherMock);
        finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, invokeCounter[0]);
    }
}
