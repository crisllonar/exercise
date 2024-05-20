package org.example.api.datausa;

import io.restassured.response.Response;
import org.example.base.BaseApiTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.example.api.datausa.DataUsaRequests.*;
import static org.hamcrest.Matchers.*;

public class GetDataTests  extends BaseApiTest {



    /***
     * POSITIVE TESTS
     */

    @Test(testName = "GET get data by all param")
    public void positiveGetDataByParams()  {

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("drilldowns","State");
        queryParams.put("measures","Population");
        queryParams.put("year","latest");

        Response response = getDataByParamRequest(queryParams);
        response.then().assertThat().statusCode(200);

        response.then().assertThat()
                .body("data[0].\"ID State\"", notNullValue())
                .body("data[0].State", notNullValue())
                .body("data[0].\"ID Year\"", notNullValue())
                .body("data[0].Year", notNullValue())
                .body("data[0].Population", notNullValue())
                .body("data[0].\"Slug State\"", notNullValue());

    }

    @Test(testName = "GET get data by param: measures")
    public void positiveGetDataByParamsMeasures()  {

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("measures","Population");

        Response response = getDataByParamRequest(queryParams);
        response.then().assertThat().statusCode(200);

        response.then().assertThat()
                .body("data[0].\"ID State\"", nullValue())
                .body("data[0].State", nullValue())
                .body("data[0].\"ID Year\"", notNullValue())
                .body("data[0].Year", notNullValue())
                .body("data[0].Population", notNullValue())
                .body("data[0].\"Slug State\"", nullValue());

    }

    /***
     * negative test
     */

    @Test(testName = "GET get data by param: drilldowns")
    public void negativeGetDataByParamsDrilldowns()  {

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("drilldowns","State");

        Response response = getDataByParamRequest(queryParams);
        response.then().assertThat().statusCode(200);

        response.then().assertThat()
                .body("error", equalTo("Query must contain at least one measure."));

    }

    @Test(testName = "GET get data by param: year")
    public void negativeGetDataByParamsYear()  {

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("year","latest");

        Response response = getDataByParamRequest(queryParams);
        response.then().assertThat().statusCode(200);

        response.then().assertThat()
                .body("error", equalTo("Query must contain at least one measure."));

    }

    @Test(testName = "GET get data by invalid values params")
    public void negativeGetDataByInvalidValuesParams()  {

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("drilldowns","234234");
        queryParams.put("measures","2342342");
        queryParams.put("year","234234234");

        Response response = getDataByParamRequest(queryParams);
        response.then().assertThat().statusCode(200);

        response.then().assertThat()
                .body("data", hasSize(0))
                .body("source", hasSize(0));

    }


}
