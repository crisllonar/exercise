package org.example.helper.common;

import io.restassured.response.Response;
import org.testng.Assert;


import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class Helper {

    /**
     *
     * @param elementsExpected
     * @param response
     * @param pathElements
     */
    public static void verifyList(List<String> elementsExpected, Response response, String pathElements) {
        int countElementEquals = 0;
        int numberElements = response.getBody().path(pathElements +".size()");

        for (String element : elementsExpected) {
            for (int i = 0; i < numberElements; i++) {
                if (element.equals(response.getBody().path(pathElements +"["+ i +"]"))) {
                    countElementEquals++;
                    break;
                }
            }
        }
        Assert.assertEquals(countElementEquals, numberElements,"Error: not of all elements were found. \n Expected: "+elementsExpected +" \n Actual: "+ response.getBody().path(pathElements));
    }

    /**
     *
     * @param messageExpected
     * @param statusCodeExpected
     * @param responseActual
     */
    public static void verifyErrorMessage(String messageExpected, int statusCodeExpected, Response responseActual) {
        responseActual.then().assertThat().statusCode(statusCodeExpected);
        responseActual.then().assertThat()
                .body("error",equalTo(messageExpected));

    }

}
