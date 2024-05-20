package org.example.base;

import org.testng.annotations.BeforeSuite;

import static org.example.base.Configuration.*;

public class BaseApiTest {


    @BeforeSuite(alwaysRun = true)
    protected void beforeSuiteSetUp() {
        BASE_URL = "https://datausa.io/api";
    }

}
