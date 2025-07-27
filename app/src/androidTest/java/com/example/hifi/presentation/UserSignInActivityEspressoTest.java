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

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.hifi.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserSignInActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<UserSignInActivity> activityRule =
            new ActivityScenarioRule<>(UserSignInActivity.class);
    
    @Test
    public void typeInCredentials() {
        onView(withId(R.id.enterEmail))
                .perform(clearText(), typeText("admin@comp3350.ca"));
        onView(withId(R.id.enterPassword))
                .perform(clearText(), typeText("test"));
        onView(withId(R.id.signIn))
                .perform(click());
    }
}
