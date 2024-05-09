package org.example.api.person;

import io.restassured.response.Response;
import org.example.api.person.mapper.CreatePersonPayload;
import org.example.base.BaseApiTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.example.api.person.PersonRequests.createPersonRequest;
import static org.example.api.person.PersonRequests.deletePersonRequest;
import static org.example.api.person.dataBuilder.personBuilder.getCreatePersonPayloadBuilder;
import static org.example.helper.common.Helper.verifyErrorMessage;
import static org.example.helper.common.Helper.verifyList;
import static org.example.helper.common.Utils.getCurrentDateFormatted;
import static org.hamcrest.Matchers.*;

public class PostPersonTests extends BaseApiTest {

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
            POST_PERSON },
            testName = "POST create person default fields")
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
    }

    @Test(groups = {
            ALL_PERSON,
            POST_PERSON },
            testName = "POST create person custom data")
    public void createPersonCustomData() throws InterruptedException {

        String email = "test-email" + UUID.randomUUID()+"@test.com";

        List<String> addresses = new ArrayList<>();
        addresses.add("new address custom 1");

        List<String> phones = new ArrayList<>();
        phones.add("848438288");

        CreatePersonPayload createPersonPayload = getCreatePersonPayloadBuilder(email)
                .firstName("armando")
                .lastName("casas")
                .phones(phones)
                .addresses(addresses)
                .build();

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
    }


    /**
     * NEGATIVE TEST
     */

    @Test(groups = {
            ALL_PERSON,
            POST_PERSON },
            testName = "POST create person null values")
    public void createPersonNullValues() {

        CreatePersonPayload createPersonPayload = getCreatePersonPayloadBuilder(null)
                .firstName(null)
                .lastName(null)
                .phones(null)
                .addresses(null)
                .build();

        Response response = createPersonRequest(createPersonPayload);

        String message = "One of the following keys is missing or is empty in request body: 'first_name', 'last_name', 'email', 'phones', 'addresses'";
        int statusCode = 400;

        verifyErrorMessage(message, statusCode, response);

    }

    @Test(groups = {
            ALL_PERSON,
            POST_PERSON },
            testName = "POST create person same email twice")
    public void createPersonSameEmailTwice() throws InterruptedException {

        String email = "test-email" + UUID.randomUUID()+"@test.com";

        CreatePersonPayload createPersonPayload = getCreatePersonPayloadBuilder(email).build();

        Response response = createPersonRequest(createPersonPayload);
        response.then().assertThat().statusCode(201);
        String personId = response.getBody().path("id");
        personIdList.add(personId);

        Thread.sleep(2000);

        createPersonPayload = getCreatePersonPayloadBuilder(email).build();

        response = createPersonRequest(createPersonPayload);

        String message = "Person with the email: '"+email+"' already exists";
        int statusCode = 400;

        verifyErrorMessage(message, statusCode, response);

    }

}
