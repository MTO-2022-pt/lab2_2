package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;

class SimilarityFinderTest {

    @Test
    void shouldReturnOneWhenEmptySequences() {
        //given
        int[] seq1 = {};
        int[] seq2 = {};

        //when
        double result = SimilarityFinderMock.getSimilarityFinder((elem, seq) -> SimilarityFinderMock.getResult(true)).calculateJackardSimilarity(seq1, seq2);

        //then
        assertEquals(1.0d, result);
    }

    @Test
    void shouldReturnOneWhenEqualSequences() {
        //given
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {1, 2, 3};

        //when
        double result = SimilarityFinderMock.getSimilarityFinder((elem, seq) -> SimilarityFinderMock.getResult(true)).calculateJackardSimilarity(seq1, seq2);

        //then
        assertEquals(1.0d, result);
    }

    @Test
    void shouldReturnZeroWhenNoTheSameElementsInSequences() {
        //given
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {10, 20, 30};

        //when
        double result = SimilarityFinderMock.getSimilarityFinder((elem, seq) -> SimilarityFinderMock.getResult(false)).calculateJackardSimilarity(seq1, seq2);

        //then
        assertEquals(0d, result);
    }

    @Test
    void shouldReturnZeroWhenFirstSequenceIsEmpty() {
        //given
        int[] seq1 = {};
        int[] seq2 = {10, 20, 30};

        //when
        double result = SimilarityFinderMock.getSimilarityFinder((elem, seq) -> SimilarityFinderMock.getResult(false)).calculateJackardSimilarity(seq1, seq2);

        //then
        assertEquals(0d, result);
    }

    @Test
    void shouldReturnZeroWhenFirstSecondIsEmpty() {
        //given
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {};

        //when
        double result = SimilarityFinderMock.getSimilarityFinder((elem, seq) -> SimilarityFinderMock.getResult(false)).calculateJackardSimilarity(seq1, seq2);

        //then
        assertEquals(0d, result);
    }

    @Test
    void shouldReturnOneHalfWhenTotalLengthNineAndThreeCommonElements() {
        //given
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {8, 2, 1, 4, 6, 3};

        //when
        double result = SimilarityFinderMock.getSimilarityFinder((elem, seq) -> {
            return elem == 1 || elem == 2 || elem == 3 ? SimilarityFinderMock.getResult(true) : SimilarityFinderMock.getResult(false);
        }).calculateJackardSimilarity(seq1, seq2);

        //then
        assertEquals(0.5d, result);
    }

    @Test
    void shouldInvokeSearchMethodTwoTimes() {
        //given
        int[] seq1 = {3, 5};
        int[] seq2 = {1, 2, 3, 4, 6};
        Counter.reset();

        //when
        SequenceSearcher searcherMock = (elem, sequence) -> {
            Counter.countIn();
            return SimilarityFinderMock.getResult(true);
        };

        SimilarityFinder finder = new SimilarityFinder(searcherMock);
        finder.calculateJackardSimilarity(seq1, seq2);

        //then
        assertEquals(2, Counter.getValue());
    }

    @Test
    void shouldNotInvokeSearchMethod() {
        //given
        int[] seq1 = {};
        int[] seq2 = {3, 4, 4, 5};
        Counter.reset();

        //when
        SequenceSearcher searcherMock = (elem, sequence) -> {
            Counter.countIn();
            return null;
        };

        SimilarityFinder finder = new SimilarityFinder(searcherMock);
        finder.calculateJackardSimilarity(seq1, seq2);

        //then
        assertEquals(0, Counter.getValue());
    }

    @Test
    void shouldFindTwoTheSameElementsFromFirstInSecondSequence() {
        //given
        int[] seq1 = {8, 9};
        int[] seq2 = {1, 9, 3, 8, 5};
        Counter.reset();

        //when
        SequenceSearcher searcherMock = (elem, sequence) -> {
            if (elem == 8 || elem == 9) {
                Counter.countIn();
                return SimilarityFinderMock.getResult(true);
            } else {
                return SimilarityFinderMock.getResult(false);
            }
        };

        //then
        SimilarityFinder finder = new SimilarityFinder(searcherMock);
        finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(2, Counter.getValue());
    }

    private static class SimilarityFinderMock {

        static SearchResult getResult(boolean isFound) {
            return SearchResult.builder().withFound(isFound).build();
        }

        static SimilarityFinder getSimilarityFinder(SequenceSearcher searcher) {
            return new SimilarityFinder(searcher);
        }
    }

    private static class Counter {
        private static int counter = 0;

        static void countIn() {
            counter++;
        }

        static void reset() {
            counter = 0;
        }

        static int getValue() {
            return counter;
        }
    }

}
