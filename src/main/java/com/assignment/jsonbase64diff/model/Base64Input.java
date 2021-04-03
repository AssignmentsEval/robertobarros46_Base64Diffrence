package com.assignment.jsonbase64diff.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Base64Input {

    @Id
    private String id;
    private String valueId;
    private String value;
    private Base64InputType base64InputType;
}
