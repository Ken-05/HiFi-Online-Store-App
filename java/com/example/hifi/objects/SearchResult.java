package com.example.hifi.objects;

import java.util.ArrayList;
import java.util.List;

public class SearchResult<T> implements Comparable<SearchResult<T>> {
    private final T value;
    private int rank = 0;
    private MatchType matchType;
    
    public SearchResult(T value) {
        this.value = value;
    }
    
    public T getValue() {
        return value;
    }
    
    public int getRank() {
        return rank;
    }
    
    public MatchType getMatchType() {
        return matchType;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public void incrementRank() {
        rank++;
    }
    
    public void incrementRank(int amount) { rank += amount; }
    
    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }
    
    @Override
    public int compareTo(SearchResult<T> o) {
        // if matches are both of the same type, order by strength
        if (this.matchType == o.matchType) {
            return o.rank - this.rank;
        }
        // else, order by match type
        return this.matchType.ordinal() - o.matchType.ordinal();
    }
    
    /**
     * Converts a list of SearchResults into a list of the SearchResults value type
     * @param searchResultList A list search results
     * @return A list of the search results values
     */
    public static <T> List<T> getValueList(List<SearchResult<T>> searchResultList) {
        List<T> values = new ArrayList<>();
    
        for (SearchResult<T> result : searchResultList) {
            values.add(result.getValue());
        }
    
        return values;
    }
    
    public enum MatchType {
        Keyword,
        Name,
        Description
    }
}
