package org.example.base;

import org.testng.annotations.BeforeSuite;

import static org.example.base.Configuration.*;

public class BaseApiTest {


    //tags-labels
    public static final String ALL_PERSON = "all-person";
    public static final String PATCH_PERSON = "patch-person";
    public static final String POST_PERSON = "post-person";

    @BeforeSuite(alwaysRun = true)
    protected void beforeSuiteSetUp() {
        BASE_URL = "http://localhost:3000/api";
    }

}
