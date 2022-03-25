package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    int counter;

    SequenceSearcher searcherStub = (elem, sequence) -> {
        for(int i = 0; i < sequence.length; i++){
            if(sequence[i] == elem)
                return SearchResult.builder().withFound(true).withPosition(i).build();
        }
        return SearchResult.builder().withFound(false).build();
    };

    SequenceSearcher searcherDummyWithCounter = (elem, sequence) -> {
        counter++;
        for(int i = 0; i < sequence.length; i++){
            if(sequence[i] == elem)
                return SearchResult.builder().withFound(true).withPosition(i).build();
        }
        return SearchResult.builder().withFound(false).build();
    };

    //testy stanu
    @Test
    void similarityNotFoundTest(){
        int[] arr1 = {1, 2, 3}; int[] arr2 = {4, 5, 6};
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) -> SearchResult.builder().withFound(false).build());
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
        int[] arr1 = {1, 2, 3}; int[] arr2 = {1, 4, 5};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.2);
    }

    @Test
    void oneInFourSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4}; int[] arr2 = {1, 5, 6, 7};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.14285714285714285);
    }

    @Test
    void twoInThreeSimilarityTest(){
        int[] arr1 = {1, 2, 3}; int[] arr2 = {1, 2, 5};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.5);
    }

    @Test
    void firstTwoInFourSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4}; int[] arr2 = {1, 2, 5, 6};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.3333333333333333);
    }

    @Test
    void lastTwoInFourSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4}; int[] arr2 = {5, 6, 3, 4};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.3333333333333333);
    }

    @Test
    void emptySequenceOneSimilarityTest(){
        int[] arr1 = {}; int[] arr2 = {5, 6, 3, 4};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0);
    }

    @Test
    void emptySequenceTwoSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4}; int[] arr2 = {};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0);
    }

    @Test
    void emptySequencesSimilarityTest(){
        int[] arr1 = {}; int[] arr2 = {};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 1);
    }

    @Test
    void fourInFiveSequenceSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4, 5}; int[] arr2 = {1, 2, 3, 4, 6};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity, 0.6666666666666666);
    }

    //testy zachowania
    @Test
    void fourInFiveSequenceSearchTwiceSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4, 5}; int[] arr2 = {1, 2, 3, 4, 6};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity1 = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        double similarity2 = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(similarity1, 0.6666666666666666);
        assertEquals(similarity1, similarity2);
    }

    @Test
    void threeSequencesSimilarityTest(){
        int[] arr1 = {1, 2, 3, 4, 5}; int[] arr2 = {1, 2, 7, 8, 9}; int[] arr3 = {7, 8, 1, 2 ,3};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherStub);
        double similarity1 = similarityFinder.calculateJackardSimilarity(arr1, arr2);
        double similarity2 = similarityFinder.calculateJackardSimilarity(arr1, arr3);
        double similarity3 = similarityFinder.calculateJackardSimilarity(arr2, arr3);
        assertEquals(similarity1, 0.25);
        assertEquals(similarity2, 0.42857142857142855);
        assertEquals(similarity3, 0.6666666666666666);
    }

    @Test
    void searchMethodRunsCounterTest(){
        counter = 0;
        int[] arr1 = {1, 2, 3}; int[] arr2 = {4, 5, 6};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherDummyWithCounter);
        similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(counter, arr1.length);
    }

    @Test
    void searchMethodRunsCounterSecondArrayLongerTest(){
        counter = 0;
        int[] arr1 = {1, 2, 3, 4, 5}; int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
        SimilarityFinder similarityFinder = new SimilarityFinder(searcherDummyWithCounter);
        similarityFinder.calculateJackardSimilarity(arr1, arr2);
        assertEquals(counter, arr1.length);
    }

}
