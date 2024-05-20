package org.example.helper.restAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

public class RequestSpecificationBuilder extends RequestSpecBuilder {

    private RequestSpecificationBuilder() {
        super();
    }

    public static RequestSpecificationBuilder createCommonRequestSpecificationBuilder(String baseUrl) {
        RequestSpecificationBuilder requestSpecificationBuilder = new RequestSpecificationBuilder();
        return requestSpecificationBuilder
                .setBaseURI(baseUrl)
                .addRequestLoggingFilter()
                .addResponseLoggingFilter();
    }

    private RequestSpecificationBuilder setBaseURI(String uri) {
        this.setBaseUri(uri);
        return this;
    }

    private RequestSpecificationBuilder addResponseLoggingFilter() {
        this.addFilter(new ResponseLoggingFilter());
        return this;
    }

    private RequestSpecificationBuilder addRequestLoggingFilter() {
        this.addFilter(new RequestLoggingFilter());
        return this;
    }

}
