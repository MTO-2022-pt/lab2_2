package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;

import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    public void isSequenceLengthsZero() {
        int[] seq1 = {};
        int[] seq2 = {};

        SimilarityFinder finder = new SimilarityFinder(new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return null;
            }
        });

        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }
}
