package com.assignment.jsonbase64diff.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Base64InputTest {

    @Test
    public void builderTest() {
        final String expectedId = "123456";
        final String expectedValueId = "1";
        final String expectedValue = "test";
        final Base64InputType expectedBase64InputType = Base64InputType.LEFT;
        Base64Input base64Input = Base64Input.builder()
                .id(expectedId)
                .valueId(expectedValueId)
                .value(expectedValue)
                .base64InputType(expectedBase64InputType)
                .build();
        assertEquals(expectedId, base64Input.getId());
        assertEquals(expectedValueId, base64Input.getValueId());
        assertEquals(expectedValue, base64Input.getValue());
        assertEquals(expectedBase64InputType, base64Input.getBase64InputType());
    }
}