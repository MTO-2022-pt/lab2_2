package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    int[] empty = {};
    int[] oneToThree = {1, 2, 3};
    int[] oneToSix = {1, 2, 3, 4, 5, 6};
    int[] threeToOne = {3, 2, 1};
    @Test
    void emptySequences() {
        SimilarityFinder similarityFinder = new SimilarityFinder((seq1, seq2) -> SearchResult.builder().build());
        assert similarityFinder.calculateJackardSimilarity(empty, empty) == 1;
    }

    @Test
    void equalSequences() {
        SimilarityFinder similarityFinder = new SimilarityFinder((seq1, seq2) -> SearchResult.builder().withFound(true).build());
        assert similarityFinder.calculateJackardSimilarity(oneToThree, oneToThree) == 1;
    }

    @Test
    void halfEqualSequences() {
        SimilarityFinder similarityFinder = new SimilarityFinder((seq1, seq2) -> SearchResult.builder().withFound(true).build());
        assert similarityFinder.calculateJackardSimilarity(oneToThree, oneToSix) == 0.5;
    }

    @Test
    void ascendingDescending() {
        SimilarityFinder similarityFinder = new SimilarityFinder((seq1, seq2) -> SearchResult.builder().withFound(true).build());
        assert similarityFinder.calculateJackardSimilarity(oneToThree, threeToOne) == 1;
    }

    @Test
    void emptyWithNotEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder((seq1, seq2) -> SearchResult.builder().withFound(true).build());
        assert similarityFinder.calculateJackardSimilarity(empty, oneToThree) == 0;
    }

    @Test
    void notEmptyWithEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder((seq1, seq2) -> SearchResult.builder().withFound(false).build());
        assert similarityFinder.calculateJackardSimilarity(oneToThree, empty) == 0;
    }

}
