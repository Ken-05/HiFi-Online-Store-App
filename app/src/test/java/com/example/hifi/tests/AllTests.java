package com.example.hifi.tests;

import com.example.hifi.tests.business.SearchProductsTest;
import com.example.hifi.tests.objects.ProductTest;
import com.example.hifi.tests.objects.SearchResultTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SearchProductsTest.class,
        ProductTest.class,
        SearchResultTest.class
})
public class AllTests {
}
