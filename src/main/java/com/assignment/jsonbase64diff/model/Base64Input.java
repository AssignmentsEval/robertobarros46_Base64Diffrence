package com.assignment.jsonbase64diff.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Object that holds the Base64Input, which the user will pass to be compared later
 */
@Document
@Data
@Builder
public class Base64Input {

    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    @ApiModelProperty(hidden = true)
    private String valueId;
    private String value;
    @ApiModelProperty(hidden = true)
    private Base64InputType base64InputType;
}
