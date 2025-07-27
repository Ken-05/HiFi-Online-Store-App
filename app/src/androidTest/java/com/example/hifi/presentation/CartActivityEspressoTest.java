package com.example.hifi.presentation;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.hifi.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CartActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<CartActivity> activityRule =
            new ActivityScenarioRule<>(CartActivity.class);
    
    @Test
    public void checkoutButtonVisible() {
        onView(withText("Checkout")).check(matches(isDisplayed()));
    }
}
