package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    SimilarityFinder testFinder;

    @Test
    void emptySequences() {
        int[] arr1 = {};
        int[] arr2 = {};
        testFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(true).build()));
        assertEquals(1.0, testFinder.calculateJackardSimilarity(arr1, arr2));
    }

    @Test
    void oneEmptySequenceFirst(){
        int[] arr1 = {};
        int[] arr2 = {1, 2};
        testFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(false).build()));
        assertEquals(0.0, testFinder.calculateJackardSimilarity(arr1, arr2));
    }

    @Test
    void oneEmptySequenceSecond(){
        int[] arr2 = {};
        int[] arr1 = {1, 2};
        testFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(false).build()));
        assertEquals(0.0, testFinder.calculateJackardSimilarity(arr1, arr2));
    }

    @Test
    void sameSequences(){
        int[] arr1 = {1, 2, 3};
        testFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(true).build()));
        assertEquals(1.0, testFinder.calculateJackardSimilarity(arr1, arr1));
    }

    @Test
    void differentSequences(){
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {4, 5, 6};
        testFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().withFound(false).build()));
        assertEquals(0.0, testFinder.calculateJackardSimilarity(arr1, arr2));
    }

    @Test
    void twoSameElementsLenEight(){
        // total len = 8, 2 same elements therefore index = 2/(8-2) = 2/6
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {1, 2, 5, 6};
        testFinder = new SimilarityFinder(((elem, sequence) -> {
            switch(elem){
                case 1:
                case 2:
                    return SearchResult.builder().withFound(true).build();
                case 3:
                case 4:
                    return SearchResult.builder().withFound(false).build();
                default:
                    return null;
            }
        }));
        assertEquals((double)2/6, testFinder.calculateJackardSimilarity(arr1, arr2));
    }

    @Test
    void fourSameElementsLenNine(){
        // total len = 9, 4 same elements therefore index = 4/(9-4) = 4/5
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {1, 2, 3, 4, 5};
        testFinder = new SimilarityFinder(((elem, sequence) -> {
            switch(elem){
                case 1:
                case 2:
                case 3:
                case 4:
                    return SearchResult.builder().withFound(true).build();
                case 5:
                    return SearchResult.builder().withFound(false).build();
                default:
                    return null;
            }
        }));
        assertEquals((double)4/5, testFinder.calculateJackardSimilarity(arr1, arr2));
    }

    @Test
    void duplicateElementsInFirstArray(){
        // total len = 7, same elements = 2 therefore index = 2/(7-2) = 2/5
        int[] arr1 = {1, 1, 2, 3};
        int[] arr2 = {1, 4, 5};
        testFinder = new SimilarityFinder(((elem, sequence) -> {
            switch(elem){
                case 1:
                    return SearchResult.builder().withFound(true).build();
                case 2:
                case 3:
                case 4:
                case 5:
                    return SearchResult.builder().withFound(false).build();
                default:
                    return null;
            }
        }));
        assertEquals((double)2/5, testFinder.calculateJackardSimilarity(arr1, arr2));
    }

    @Test
    void duplicateElementsInSecondArray(){
        // total len = 7, same elements = 2 therefore index = 2/(7-2) = 2/5
        int[] arr2 = {1, 1, 2, 3};
        int[] arr1 = {1, 4, 5};
        testFinder = new SimilarityFinder(((elem, sequence) -> {
            switch(elem){
                case 1:
                    return SearchResult.builder().withFound(true).build();
                case 2:
                case 3:
                case 4:
                case 5:
                    return SearchResult.builder().withFound(false).build();
                default:
                    return null;
            }
        }));
        assertEquals((double)2/5, testFinder.calculateJackardSimilarity(arr1, arr2));
    }
}
