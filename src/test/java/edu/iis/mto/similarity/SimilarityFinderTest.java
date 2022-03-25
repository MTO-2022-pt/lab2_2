package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SequenceSearcher searcherStub;

    @BeforeEach
    void beforeEach(){
        searcherStub = (elem, sequence) -> {
            for(int i = 0; i < sequence.length; i++){
                if(sequence[i] == elem)
                    return SearchResult.builder().withFound(true).withPosition(i).build();
            }
            return SearchResult.builder().withFound(false).build();
        };
    }

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

}
