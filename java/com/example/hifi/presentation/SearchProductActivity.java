package com.example.hifi.presentation;

import com.example.hifi.R;
import com.example.hifi.R.layout;
import com.example.hifi.business.AccessProducts;
import com.example.hifi.business.SearchProducts;
import com.example.hifi.objects.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class SearchProductActivity extends MenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_search_product);
        
        String query = getIntent().getStringExtra("query");
        TextView searchQueryView = findViewById(R.id.SearchedProduct);
        
        searchQueryView.setText(String.format(getString(R.string.search_result), query));
        
        SearchProducts searchProducts = new SearchProducts();
        List<Product> products = searchProducts.getProducts(query);
        
        if (products.size() == 0) {
            showNoResultsText();
            return;
        }
        
        populateListView(products);
    }
    
    private void populateListView(List<Product> products) {
        ProductListAdapter productListAdapter = new ProductListAdapter(getApplicationContext(),
                layout.product_search_result, products,
                product -> {
                    // onClick function used for each search result layout
                    Intent itemDescriptionActivityIntent = new Intent(SearchProductActivity.this,
                            ProductDescriptionActivity.class);
                    itemDescriptionActivityIntent.putExtra("productID", product.getProductID());
                    startActivity(itemDescriptionActivityIntent);
                });
        
        ListView listView = findViewById(R.id.search_result_list);
        listView.setAdapter(productListAdapter);
    }
    
    private void showNoResultsText() {
        TextView noItemText = findViewById(R.id.noItemText);
        noItemText.setVisibility(View.VISIBLE);
        
        View bottomDivider = findViewById(R.id.bottom_divider);
        bottomDivider.setVisibility(View.GONE);
    }
}
