package org.example.api.person;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.api.person.mapper.CreatePersonPayload;
import org.example.api.person.mapper.PatchPersonPayload;
import org.example.helper.restAssured.RequestSpecificationBuilder;

import static io.restassured.RestAssured.given;
import static org.example.base.Configuration.BASE_URL;
import static org.example.base.Endpoints.PERSON;
import static org.example.base.Endpoints.PERSON_BY_ID;

public class PersonRequests {

    static RequestSpecification requestSpecification = RequestSpecificationBuilder.createCommonRequestSpecificationBuilder(BASE_URL).build();

    public static Response createPersonRequest(CreatePersonPayload requestPayload) {

        return given()
                .spec(requestSpecification)
                .body(requestPayload)
                .post(PERSON);
    }

    public static Response deletePersonRequest(String personId) {

        return given()
                .spec(requestSpecification)
                .pathParam("personId",personId)
                .delete(PERSON_BY_ID);
    }

    public static Response patchPersonRequest(PatchPersonPayload requestPayload, String personId) {

        return given()
                .spec(requestSpecification)
                .body(requestPayload)
                .pathParam("personId",personId)
                .patch(PERSON_BY_ID);
    }

    public static Response getPersonByIdRequest(String personId) {

        return given()
                .spec(requestSpecification)
                .pathParam("personId",personId)
                .get(PERSON_BY_ID);
    }

}
