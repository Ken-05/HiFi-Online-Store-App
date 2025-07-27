package com.example.hifi.presentation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hifi.R;
import com.example.hifi.application.Services;
import com.example.hifi.business.UserService;

public abstract class MenuActivity extends AppCompatActivity {
    @Override
    @CallSuper
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu, menu);
        
        MenuItem searchMenuItem = menu.findItem(R.id.search_menu_item);
        
        if (searchMenuItem != null) {
            SearchView searchView = (SearchView) searchMenuItem.getActionView();
            
            if (searchView != null) {
                searchView.setOnQueryTextListener(
                        new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                Intent searchProductActivityIntent = new Intent(MenuActivity.this,
                                        SearchProductActivity.class);
                                searchProductActivityIntent.putExtra("query", query);
                                startActivity(searchProductActivityIntent);
                                return true;
                            }
                            
                            @Override
                            public boolean onQueryTextChange(String newText) {
                                return false;
                            }
                        });
            }
        }
        
        return true;
    }
    
    @Override
    @CallSuper
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_in_menu_item:
                Intent accountIntent;
                UserService userService = Services.getUserService();
                if (!userService.isLoggedIn()) {
                    accountIntent = new Intent(this, UserSignInActivity.class);
                } else {
                    accountIntent = new Intent(this, EditProfileActivity.class);
                }
                startActivity(accountIntent);
                break;
            case R.id.cart_menu_item:
                Intent cartActivityIntent = new Intent(this, CartActivity.class);
                startActivity(cartActivityIntent);
                break;
            case R.id.order_history_menu_item:
                Intent orderHistoryActivityIntent = new Intent(this, OrderHistoryActivity.class);
                startActivity(orderHistoryActivityIntent);
                break;
            case R.id.wishlist_menu_item:
                Intent wishlistActivityIntent = new Intent(this, WishListActivity.class);
                startActivity(wishlistActivityIntent);
                break;
            default:
                return false;
        }
        return true;
    }
}
