package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SequenceSearcher alwaysFalseSearcher = (elem, sequence) -> SearchResult.builder().withFound(false).build();
    int counter;

    //testy stanu
    @Test
    void similarityNotFoundTest(){
        int[] arr1 = {1, 2, 3}; int[] arr2 = {4, 5, 6};
        SimilarityFinder similarityFinder = new SimilarityFinder(alwaysFalseSearcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0);
    }

    @Test
    void sameArraySimilarityTest(){
        int[] arr1 = {1, 2, 3};
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) -> SearchResult.builder().withFound(true).withPosition(elem - 1).build());
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr1);
        assertEquals(similarity, 1);
    }

    @Test
    void oneInThreeSimilarityTest(){
        int[] arr1 = {2, 3, 4}; int[] arr2 = {1, 2, 5};
        SequenceSearcher searcher = (elem, sequence) -> {
            if(elem == 2)
                return SearchResult.builder().withFound(true).withPosition(1).build();
            else
                return SearchResult.builder().withFound(false).build();
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.2);
    }

    @Test
    void oneInFourSimilarityTest(){
        int[] arr1 = {1, 2, 3, 6}; int[] arr2 = {4, 5, 6, 7};
        SequenceSearcher searcher = (elem, sequence) -> {
            if(elem == 1)
                return SearchResult.builder().withFound(true).withPosition(2).build();
            else
                return SearchResult.builder().withFound(false).build();
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.14285714285714285);
    }

    @Test
    void firstTwoInThreeSimilarityTest(){
        int[] arr1 = {1, 2, 3}; int[] arr2 = {1, 2, 5};
        SequenceSearcher searcher = (elem, sequence) -> {
            if(elem == 1 || elem == 2)
                return SearchResult.builder().withFound(true).withPosition(elem - 1).build();
            else
                return SearchResult.builder().withFound(false).build();
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.5);
    }


    @Test
    void lastTwoInFourSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4}; int[] arr2 = {5, 6, 3, 4};
        SequenceSearcher searcher = (elem, sequence) -> {
            if(elem == 1 || elem == 2)
                return SearchResult.builder().withFound(true).withPosition(elem - 1).build();
            else
                return SearchResult.builder().withFound(false).build();
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.3333333333333333);
    }

    @Test
    void emptySequenceOneSimilarityTest(){
        int[] arr1 = {}; int[] arr2 = {5, 6, 3, 4};
        SimilarityFinder similarityFinder = new SimilarityFinder(alwaysFalseSearcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0);
    }

    @Test
    void emptySequenceTwoSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4}; int[] arr2 = {};
        SimilarityFinder similarityFinder = new SimilarityFinder(alwaysFalseSearcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0);
    }

    @Test
    void emptySequencesSimilarityTest(){
        int[] arr1 = {}; int[] arr2 = {};
        SimilarityFinder similarityFinder = new SimilarityFinder(alwaysFalseSearcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 1);
    }

    @Test
    void fourInFiveSequenceSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4, 5}; int[] arr2 = {1, 2, 3, 4, 6};
        SequenceSearcher searcher = (elem, sequence) -> {
            if(elem == 5 || elem == 6)
                return SearchResult.builder().withFound(false).build();
            else
                return SearchResult.builder().withFound(true).withPosition(elem - 1).build();
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.6666666666666666);
    }

    //testy zachowania
    @Test
    void fourInFiveSequenceSearchTwiceSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4, 5}; int[] arr2 = {1, 2, 3, 4, 6};
        SequenceSearcher searcher = (elem, sequence) -> {
            if(elem == 5 || elem == 6)
                return SearchResult.builder().withFound(false).build();
            else
                return SearchResult.builder().withFound(true).withPosition(elem - 1).build();
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        double similarity1 = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        double similarity2 = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity1, 0.6666666666666666);
        assertEquals(similarity1, similarity2);
    }

    @Test
    void searchMethodRunsCounterFirstArrayLongerTest(){
        counter = 0;
        int[] arr1 = {1, 2, 3, 4, 5, 6}; int[] arr2 = {4, 5, 6};
        SequenceSearcher searcher = (elem, sequence) -> {
            counter++;
            if(elem < 6)
                return SearchResult.builder().withFound(true).withPosition(elem - 1).build();
            else
                return SearchResult.builder().withFound(false).build();
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(counter, arr1.length);
    }

    @Test
    void searchMethodRunsCounterSecondArrayLongerTest(){
        counter = 0;
        int[] arr1 = {1, 2, 3, 4, 5}; int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
        SequenceSearcher searcher = (elem, sequence) -> {
            counter++;
            if(elem < 6)
                return SearchResult.builder().withFound(true).withPosition(elem - 1).build();
            else
                return SearchResult.builder().withFound(false).build();
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(searcher);
        similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(counter, arr1.length);
    }

}

