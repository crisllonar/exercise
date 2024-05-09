package org.example.api.person.dataBuilder;

import org.example.api.person.mapper.CreatePersonPayload;
import org.example.api.person.mapper.PatchPersonPayload;

import java.util.List;

public class personBuilder {

    private static final String firstName = "default firstname";
    private static final String lastName = "default lastname";
    private static final List<String> phones = List.of("9999999991");
    private static final List<String> addresses = List.of("default address 01");


    public static CreatePersonPayload.CreatePersonPayloadBuilder getCreatePersonPayloadBuilder(String email) {
        return CreatePersonPayload.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .phones(phones)
                .addresses(addresses);
    }

    public static PatchPersonPayload.PatchPersonPayloadBuilder getPatchPersonPayloadBuilder() {
        return PatchPersonPayload.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phones(phones)
                .addresses(addresses);
    }

}
