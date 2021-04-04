package com.assignment.jsonbase64diff.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Object that holds the differences if the base64 input are equal in size but has different characters
 */
@Document
@Data
@Builder
public class Difference {

    private Integer offset;
    private Integer length;
}
