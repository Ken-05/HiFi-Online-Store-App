package com.example.hifi.presentation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hifi.R;
import com.example.hifi.application.Services;
import com.example.hifi.business.AccessProducts;
import com.example.hifi.business.UserService;

public class HomeActivity extends MenuActivity implements RecyclerViewInterface, PopupMenu.OnMenuItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_home);
        
        AccessProducts accessProducts = new AccessProducts();

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        Resources resources = getResources();
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(this, accessProducts, this, resources);
        
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    // when any button is clicked about a product
    public void buttonOnClick(View v) {
        Intent productDescriptionActivityIntent = new Intent(HomeActivity.this, ProductDescriptionActivity.class);        // pass some data to signify the ID of the product to display
        productDescriptionActivityIntent.putExtra("productID", v.getTag().toString());
        startActivity(productDescriptionActivityIntent);
    }
    
    @Override
    public void onClick(int position) {
        Intent productDescriptionActivityIntent = new Intent(HomeActivity.this, ProductDescriptionActivity.class);
        
        productDescriptionActivityIntent.putExtra("productID", "" + position);
        startActivity(productDescriptionActivityIntent);
    }
    
    
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }
    
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Computers:
                setContentView(R.layout.computer_cat);
                break;
            case R.id.Gadgets:
                setContentView(R.layout.gadgets_cat);
                break;
            case R.id.headphones:
                setContentView(R.layout.headphones_cat);
            case R.id.smartphones:
                setContentView(R.layout.smartphone_cat);
                return true;
            default:
                return false;
            
        }
        return true;
    }
}
