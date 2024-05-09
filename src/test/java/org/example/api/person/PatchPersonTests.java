package org.example.api.person;

import io.restassured.response.Response;
import org.example.api.person.mapper.CreatePersonPayload;
import org.example.api.person.mapper.PatchPersonPayload;
import org.example.base.BaseApiTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.example.api.person.PersonRequests.*;
import static org.example.api.person.dataBuilder.personBuilder.getCreatePersonPayloadBuilder;
import static org.example.api.person.dataBuilder.personBuilder.getPatchPersonPayloadBuilder;
import static org.example.helper.common.Helper.verifyList;
import static org.example.helper.common.Utils.getCurrentDateFormatted;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PatchPersonTests extends BaseApiTest {

    private static final List<String> personIdList = new ArrayList<>();

    @AfterClass
    public void afterClassTearDown() {
        for (String personId : personIdList) {
            deletePersonRequest(personId);
        }
    }


    /***
     * POSITIVE TESTS
     */

    @Test(groups = {
            ALL_PERSON,
            PATCH_PERSON},
            testName = "PATCH update person all fields")
    public void createPersonDefaultFields() throws InterruptedException {

        String email = "test-email" + UUID.randomUUID()+"@test.com";

        CreatePersonPayload createPersonPayload = getCreatePersonPayloadBuilder(email).build();

        Response response = createPersonRequest(createPersonPayload);
        response.then().assertThat().statusCode(201);
        String personId = response.getBody().path("id");
        personIdList.add(personId);

        response.then().assertThat()
                .body("email",equalTo(createPersonPayload.getEmail()))
                .body("first_name", equalTo(createPersonPayload.getFirstName()))
                .body("last_name",equalTo(createPersonPayload.getLastName()))
                .body("createdAt", containsString(getCurrentDateFormatted("M/d/yyyy")))
                .body("updatedAt", containsString(getCurrentDateFormatted("M/d/yyyy")));

        verifyList(createPersonPayload.getAddresses(),response,"addresses");
        verifyList(createPersonPayload.getPhones(),response,"phones");

        Thread.sleep(2000);

       PatchPersonPayload patchPersonPayload = getPatchPersonPayloadBuilder()
               .firstName("patched first name")
               .lastName("patched last name")
               .build();

       response = patchPersonRequest(patchPersonPayload, personId);
       response.then().assertThat().statusCode(200);
       response.then().assertThat()
                .body("email",equalTo(createPersonPayload.getEmail()))
                .body("first_name", equalTo(patchPersonPayload.getFirstName()))
                .body("last_name",equalTo(patchPersonPayload.getLastName()))
                .body("createdAt", containsString(getCurrentDateFormatted("M/d/yyyy")))
                .body("updatedAt", containsString(getCurrentDateFormatted("M/d/yyyy")));

       response = getPersonByIdRequest(personId);
       response.then().assertThat().statusCode(200);
       response.then().assertThat()
                .body("email",equalTo(createPersonPayload.getEmail()))
                .body("first_name", equalTo(patchPersonPayload.getFirstName()))
                .body("last_name",equalTo(patchPersonPayload.getLastName()))
                .body("createdAt", containsString(getCurrentDateFormatted("M/d/yyyy")))
                .body("updatedAt", containsString(getCurrentDateFormatted("M/d/yyyy")));

    }


}
