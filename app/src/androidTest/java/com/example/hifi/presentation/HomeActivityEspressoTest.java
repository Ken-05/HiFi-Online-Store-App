package com.example.hifi.presentation;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.KeyEvent;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.hifi.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);
    
    @Test
    public void searchGoesToResults() {
        onView(withId(R.id.search_menu_item))
                .perform(click())
                .perform(clearText(), typeText("test"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER));
    
        Intents.init();
        intended(hasComponent(SearchProductActivity.class.getName()));
    }
}
