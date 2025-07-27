package com.example.hifi.business;

import com.example.hifi.application.Services;
import com.example.hifi.objects.Product;
import com.example.hifi.objects.SearchResult;
import com.example.hifi.persistence.ProductPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchProducts {
    private static final int MAX_KEYWORD_DISTANCE = 3;
    private final ProductPersistence productPersistence;
    private final Pattern tokenSplitPattern;
    
    public SearchProducts(ProductPersistence productPersistence) {
        this.productPersistence = productPersistence;
        tokenSplitPattern = Pattern.compile("\\s+");
    }
    
    public SearchProducts() {
        this(Services.getProductPersistence());
    }
    
    private String[] tokenizeInput(final String input) {
        return tokenSplitPattern.split(input);
    }
    
    private Pattern tokensToPattern(final String[] tokens) {
        StringBuilder rawPatternStringBuilder = new StringBuilder();
        
        for (int i = 0; i < tokens.length; i++) {
            rawPatternStringBuilder.append("\\Q").append(tokens[i]).append("\\E");
            if (i != tokens.length - 1) {
                rawPatternStringBuilder.append("|");
            }
        }
        
        return Pattern.compile(rawPatternStringBuilder.toString(), Pattern.CASE_INSENSITIVE);
    }
    
    // from https://en.wikipedia.org/wiki/Levenshtein_distance#Iterative_with_two_matrix_rows
    private int levenshteinDistance(String a, String b) {
        int m = a.length(), n = b.length();
        
        if (m == 0) return n;
        if (n == 0) return m;
        
        a = a.toLowerCase();
        b = b.toLowerCase();
        
        int[] row0 = new int[n + 1], row1 = new int[n + 1];
        
        for (int j = 0; j <= n; j++) {
            row0[j] = j;
        }
        
        for (int i = 0; i < m; i++) {
            row1[0] = i + 1;
            for (int j = 0; j < n; j++) {
                row1[j + 1] = Math.min(
                        Math.min(row0[j + 1] + 1, row1[j] + 1),
                        row0[j] + (a.charAt(i) == b.charAt(j) ? 0 : 1)
                );
            }
            int[] r = row0;
            row0 = row1;
            row1 = r;
        }
        
        return row0[n];
    }
    
    private void applySearchStrength(SearchResult<Product> result, Pattern pattern, final String searchText, SearchResult.MatchType matchType) {
        assert result != null && pattern != null;
        if (searchText == null) return;
        Matcher matcher = pattern.matcher(searchText);
        boolean foundMatch = false;
        while (matcher.find()) {
            result.incrementRank();
            foundMatch = true;
        }
        if (foundMatch) {
            result.setMatchType(matchType);
        }
    }
    
    private void applyKeywordSearchStrength(SearchResult<Product> result, final String[] tokens) {
        assert result != null && tokens != null;
        if (tokens.length == 0 || result.getValue().getKeywords().size() == 0) return;
        boolean foundMatch = false;
        for (String token : tokens) {
            for (String keyword : result.getValue().getKeywords()) {
                int distance = levenshteinDistance(token, keyword);
                if (distance <= MAX_KEYWORD_DISTANCE) {
                    result.incrementRank(MAX_KEYWORD_DISTANCE - distance);
                    foundMatch = true;
                }
            }
        }
        if (foundMatch) {
            result.setMatchType(SearchResult.MatchType.Keyword);
        }
    }
    
    /**
     * Finds a list of relevant products based on a user input.
     * @param input string of keywords to search product information for
     * @return A list of {@link SearchResult} that are ordered by relevance to the input query
     */
    public List<SearchResult<Product>> searchProducts(String input) {
        if (input == null || input.length() == 0) return new ArrayList<>();
        
        String[] tokens = tokenizeInput(input);
        Pattern pattern = tokensToPattern(tokens);
        
        List<Product> products = productPersistence.getProductSequential();
        
        List<SearchResult<Product>> searchResults = new ArrayList<>();
        
        for (Product product : products) { // this should be converted to a query
            SearchResult<Product> searchResult = new SearchResult<>(product);
            applySearchStrength(searchResult, pattern, product.getProductDes(), SearchResult.MatchType.Description);
            applySearchStrength(searchResult, pattern, product.getProductName(), SearchResult.MatchType.Name);
            applyKeywordSearchStrength(searchResult, tokens);
            if (searchResult.getRank() > 0) {
                searchResults.add(searchResult);
            }
        }
        
        Collections.sort(searchResults);
        
        return searchResults;
    }
    
    public List<Product> getProducts(String input) {
        return SearchResult.getValueList(searchProducts(input));
    }
}
