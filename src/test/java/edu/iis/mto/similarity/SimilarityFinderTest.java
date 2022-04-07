package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void arrayIsTheSame() {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return SearchResult.builder().withFound(true).withPosition(elem).build();
            }
        });
        int[] seq1 = {1,2,3,4,5};
        int[] seq2 = {1,2,3,4,5};
        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }
    @Test
    void  arrayIsDifferent() {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                if(elem%2 ==0)
                    return SearchResult.builder().withFound(true).withPosition(elem).build();
                else
                    return SearchResult.builder().withFound(false).withPosition(-1).build();
            }
        });
        int[] seq1 = {2,4,6};
        int[] seq2 = {1,2,3,4,5,6};
        assertEquals(0.5, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }
    @Test
    void arrayIsDifferent2() {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return SearchResult.builder().withFound(false).withPosition(-1).build();
            }
        });
        int[] seq1 = {4,5,6,7};
        int[] seq2 = {0,1,2,3};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }
    @Test
    void arrayIsEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return SearchResult.builder().withFound(false).withPosition(-1).build();
            }
        });
        int[] seq1 = {};
        int[] seq2 = {};
        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

}
