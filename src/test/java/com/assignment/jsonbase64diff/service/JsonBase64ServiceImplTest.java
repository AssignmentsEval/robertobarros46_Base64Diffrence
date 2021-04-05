package com.assignment.jsonbase64diff.service;

import com.assignment.jsonbase64diff.comparator.Comparator;
import com.assignment.jsonbase64diff.exception.Base64InputNotFoundException;
import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Base64InputType;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.model.ResultType;
import com.assignment.jsonbase64diff.repository.IJsonBase64Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Excludes the embedded mongo configuration to make unit tests instead of integration one
 */
@JsonTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class JsonBase64ServiceImplTest {

    @Autowired
    private IJsonBase64Service jsonBase64Service;

    @MockBean
    private IJsonBase64Repository jsonBase64Repository;

    @MockBean
    private Comparator comparator;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void saveJsonBase64Test() {
        Base64Input base64Input = Base64Input.builder()
                .value("test")
                .build();

        jsonBase64Service.saveJsonBase64(base64Input, "1", Base64InputType.LEFT);

        verify(jsonBase64Repository).save(any(Base64Input.class));
    }

    @Test
    public void getJsonBase64DiffsTest() {
        final String expectedValueId = "1";
        final Base64Input leftBase64Input = Base64Input.builder()
                .id("123456")
                .valueId("1")
                .value("V0FFUwo=")
                .base64InputType(Base64InputType.LEFT)
                .build();
        final Base64Input rightBase64Input = Base64Input.builder()
                .id("654321")
                .valueId("1")
                .value("V0FFU19ST0NLUwo=")
                .base64InputType(Base64InputType.LEFT)
                .build();
        final Optional<Base64Input> leftBase64InputOptional = Optional.ofNullable(leftBase64Input);
        final Optional<Base64Input> rightBase64InputOptional = Optional.ofNullable(rightBase64Input);

        when(jsonBase64Repository.findByValueIdAndBase64InputType(expectedValueId, Base64InputType.LEFT))
                .thenReturn(leftBase64InputOptional);
        when(jsonBase64Repository.findByValueIdAndBase64InputType(expectedValueId, Base64InputType.RIGHT))
                .thenReturn(rightBase64InputOptional);

        final Result expectedResult = Result.builder()
                .id("1")
                .resultType(ResultType.SIZE_MISMATCH)
                .build();

        when(comparator.compare(leftBase64Input, rightBase64Input)).thenReturn(expectedResult);
        final Result result = jsonBase64Service.getJsonBase64Diffs("1");
        assertEquals(expectedResult, result);
    }

    @Test
    public void getJsonBase64DiffsTestWithException() {
        final String expectedValueId = "1";
        when(jsonBase64Repository.findByValueIdAndBase64InputType(expectedValueId, Base64InputType.LEFT))
                .thenReturn(Optional.empty());
        when(jsonBase64Repository.findByValueIdAndBase64InputType(expectedValueId, Base64InputType.RIGHT))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(Base64InputNotFoundException.class, () -> {
            jsonBase64Service.getJsonBase64Diffs("1");
        });
    }
}