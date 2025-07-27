package com.example.hifi.tests.objects;

import com.example.hifi.objects.SearchResult;

import org.junit.Assert;
import org.junit.Test;

public class SearchResultTest {
    @Test
    public void testSearchResult()
    {
        SearchResult<?> searchResult;
        String value = "value of search result";
        
        System.out.println("Starting testSearchResult");
    
        searchResult = new SearchResult<>(value);
        Assert.assertNotNull(searchResult);
        Assert.assertEquals("value of search result", searchResult.getValue());
        Assert.assertEquals(0, searchResult.getRank());
        Assert.assertNull("matchType defaults to null", searchResult.getMatchType());
        
        searchResult.setMatchType(SearchResult.MatchType.Name);
        Assert.assertEquals(SearchResult.MatchType.Name, searchResult.getMatchType());
        
        searchResult.incrementRank();
        Assert.assertEquals(1, searchResult.getRank());
        
        searchResult.setRank(52);
        Assert.assertEquals(52, searchResult.getRank());
        
        System.out.println("Finished testSearchResult\n");
    }
}
