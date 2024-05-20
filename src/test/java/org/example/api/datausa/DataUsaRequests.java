package org.example.api.datausa;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.helper.restAssured.RequestSpecificationBuilder;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.base.Configuration.BASE_URL;
import static org.example.base.Endpoints.*;

public class DataUsaRequests {

    static RequestSpecification requestSpecification = RequestSpecificationBuilder.createCommonRequestSpecificationBuilder(BASE_URL).build();



    public static Response getDataByParamRequest(Map<String, ?> queryParams) {

        return given()
                .spec(requestSpecification)
                .queryParams(queryParams)
                .get(DATA_USA);
    }

}
