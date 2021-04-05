package com.assignment.jsonbase64diff.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * Object that holds the Result of the comparison between left and right objects.
 */
@Document
@Data
@Builder
public class Result {

    private String id;
    private ResultType resultType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Difference> differences;
}
