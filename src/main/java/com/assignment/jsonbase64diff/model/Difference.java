package com.assignment.jsonbase64diff.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Difference {

    private Integer offset;
    private Integer length;
}
