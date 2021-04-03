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
    void compareTestDifferentSize() {
        final String expectedLeftId = "123456";
        final String expectedLeftValueId = "1";
        final String expectedLeftValue = "test";
        final Base64InputType expectedLeftBaseInputType = Base64InputType.LEFT;

        final String expectedRightId = "654321";
        final String expectedRightValueId = "1";
        final String expectedRightValue = "testest";
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
        assertEquals(ResultType.DIFFERENT_SIZE, result.getResult());
        assertNull(result.getDifferences());
    }

    @Test
    void compareTestEqual() {
        final String expectedLeftId = "123456";
        final String expectedLeftValueId = "1";
        final String expectedLeftValue = "test";
        final Base64InputType expectedLeftBaseInputType = Base64InputType.LEFT;

        final String expectedRightId = "654321";
        final String expectedRightValueId = "1";
        final String expectedRightValue = "test";
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
        assertEquals(ResultType.EQUAL, result.getResult());
        assertNull(result.getDifferences());
    }

    @Test
    void compareTestDifferent() {
        final String expectedLeftId = "123456";
        final String expectedLeftValueId = "1";
        final String expectedLeftValue = "test11";
        final Base64InputType expectedLeftBaseInputType = Base64InputType.LEFT;

        final String expectedRightId = "654321";
        final String expectedRightValueId = "1";
        final String expectedRightValue = "test12";
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
        assertEquals(ResultType.DIFFERENT, result.getResult());
        final List<Difference> differences = result.getDifferences();

        assertEquals(differences.get(0).getOffset(), 5);
        assertEquals(differences.get(0).getLength(), 1);
    }
}