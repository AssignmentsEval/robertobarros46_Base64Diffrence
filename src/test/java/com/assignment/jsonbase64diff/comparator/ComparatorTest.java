package com.assignment.jsonbase64diff.comparator;

import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Base64InputType;
import com.assignment.jsonbase64diff.model.Difference;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.model.ResultType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ComparatorTest {

    @Autowired
    private Comparator comparator;

    @Test
    void compareTestSizeMismatch() {
        final String expectedLeftId = "123456";
        final String expectedLeftValueId = "1";
        final String expectedLeftValue = "V0FFUwo=";
        final Base64InputType expectedLeftBaseInputType = Base64InputType.LEFT;

        final String expectedRightId = "654321";
        final String expectedRightValueId = "1";
        final String expectedRightValue = "V0FFU19ST0NLUwo=";
        final Base64InputType expectedRightBaseInputType = Base64InputType.RIGHT;

        final Base64Input leftBase64Input = Base64Input.builder()
                .id(expectedLeftId)
                .valueId(expectedLeftValueId)
                .value(expectedLeftValue)
                .base64InputType(expectedLeftBaseInputType)
                .build();
        final Base64Input rightBase64Input = Base64Input.builder()
                .id(expectedRightId)
                .valueId(expectedRightValueId)
                .value(expectedRightValue)
                .base64InputType(expectedRightBaseInputType)
                .build();
        final Result result = comparator.compare(leftBase64Input, rightBase64Input);
        assertEquals(expectedLeftValueId, result.getId());
        assertEquals(ResultType.SIZE_MISMATCH, result.getResultType());
        assertNull(result.getDifferences());
    }

    @Test
    void compareTestEqual() {
        final String expectedLeftId = "123456";
        final String expectedLeftValueId = "1";
        final String expectedLeftValue = "V0FFUwo=";
        final Base64InputType expectedLeftBaseInputType = Base64InputType.LEFT;

        final String expectedRightId = "654321";
        final String expectedRightValueId = "1";
        final String expectedRightValue = "V0FFUwo=";
        final Base64InputType expectedRightBaseInputType = Base64InputType.RIGHT;

        final Base64Input leftBase64Input = Base64Input.builder()
                .id(expectedLeftId)
                .valueId(expectedLeftValueId)
                .value(expectedLeftValue)
                .base64InputType(expectedLeftBaseInputType)
                .build();
        final Base64Input rightBase64Input = Base64Input.builder()
                .id(expectedRightId)
                .valueId(expectedRightValueId)
                .value(expectedRightValue)
                .base64InputType(expectedRightBaseInputType)
                .build();
        final Result result = comparator.compare(leftBase64Input, rightBase64Input);
        assertEquals(expectedLeftValueId, result.getId());
        assertEquals(ResultType.EQUAL, result.getResultType());
        assertNull(result.getDifferences());
    }

    @Test
    void compareTestValueMismatch() {
        final String expectedLeftId = "123456";
        final String expectedLeftValueId = "1";
        final String expectedLeftValue = "V0FFUwo=";
        final Base64InputType expectedLeftBaseInputType = Base64InputType.LEFT;

        final String expectedRightId = "654321";
        final String expectedRightValueId = "1";
        final String expectedRightValue = "V0FFUww=";
        final Base64InputType expectedRightBaseInputType = Base64InputType.RIGHT;

        final Base64Input leftBase64Input = Base64Input.builder()
                .id(expectedLeftId)
                .valueId(expectedLeftValueId)
                .value(expectedLeftValue)
                .base64InputType(expectedLeftBaseInputType)
                .build();
        final Base64Input rightBase64Input = Base64Input.builder()
                .id(expectedRightId)
                .valueId(expectedRightValueId)
                .value(expectedRightValue)
                .base64InputType(expectedRightBaseInputType)
                .build();
        final Result result = comparator.compare(leftBase64Input, rightBase64Input);
        assertEquals(expectedLeftValueId, result.getId());
        assertEquals(ResultType.VALUE_MISMATCH, result.getResultType());
        final List<Difference> differences = result.getDifferences();

        assertEquals(differences.get(0).getOffset(), 6);
        assertEquals(differences.get(0).getLength(), 1);
    }
}