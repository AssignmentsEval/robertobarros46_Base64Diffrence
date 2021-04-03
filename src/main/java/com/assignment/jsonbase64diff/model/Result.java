package com.assignment.jsonbase64diff.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
public class Result {

    private String id;
    private ResultType result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Difference> differences;
}
