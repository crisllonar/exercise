package org.example.api.person.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
public class PatchPersonPayload {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private List<String> phones;

    private List<String> addresses;

}
