package com.example.hifi;

import com.example.hifi.presentation.CartActivityEspressoTest;
import com.example.hifi.presentation.HomeActivity;
import com.example.hifi.presentation.HomeActivityEspressoTest;
import com.example.hifi.presentation.UserSignInActivityEspressoTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CartActivityEspressoTest.class,
        HomeActivityEspressoTest.class,
        UserSignInActivityEspressoTest.class
})
public class AllAcceptanceTests {
}
