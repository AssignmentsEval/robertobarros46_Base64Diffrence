package com.assignment.jsonbase64diff.repository;

import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Base64InputType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
public class JsonBase64RepositoryTest {

    @Autowired
    private IJsonBase64Repository jsonBase64Repository;

    @BeforeEach
    public void before() {
        jsonBase64Repository.deleteByValueId("1");
    }

    @Test
    public void repositorySaveAndFindTest() {
        final String expectedValueId = "1";
        final String expectedValue = "test";
        final Base64InputType expectedBase64InputType = Base64InputType.LEFT;
        final Base64Input base64Input = Base64Input.builder()
                .valueId(expectedValueId)
                .value(expectedValue)
                .base64InputType(expectedBase64InputType)
                .build();
        jsonBase64Repository.save(base64Input);
        final Optional<Base64Input> base64InputTypeOptional =
                jsonBase64Repository.findByValueIdAndBase64InputType(expectedValueId, expectedBase64InputType);
        assertTrue(base64InputTypeOptional.isPresent());
        final Base64Input base64InputFound = base64InputTypeOptional.get();
        assertEquals(base64InputFound.getValueId(), expectedValueId);
        assertEquals(base64InputFound.getValue(), expectedValue);
        assertEquals(base64InputFound.getBase64InputType(), expectedBase64InputType);
    }

    @Test
    public void testDeleteByValueId() {
        final String expectedValueId = "1";
        final String expectedValue = "test";
        final Base64InputType expectedBase64InputType = Base64InputType.LEFT;
        final Base64Input base64Input = Base64Input.builder()
                .valueId(expectedValueId)
                .value(expectedValue)
                .base64InputType(expectedBase64InputType)
                .build();
        jsonBase64Repository.save(base64Input);
        jsonBase64Repository.deleteByValueId(expectedValueId);
        final Optional<Base64Input> base64InputTypeOptional =
                jsonBase64Repository.findByValueIdAndBase64InputType(expectedValueId, expectedBase64InputType);
        assertFalse(base64InputTypeOptional.isPresent());
    }
}
