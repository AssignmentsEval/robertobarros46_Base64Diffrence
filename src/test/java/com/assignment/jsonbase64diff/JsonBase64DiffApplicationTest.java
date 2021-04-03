package com.assignment.jsonbase64diff;

import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Difference;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.model.ResultType;
import com.assignment.jsonbase64diff.repository.IJsonBase64Repository;
import com.assignment.jsonbase64diff.service.IJsonBase64Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonBase64DiffApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IJsonBase64Repository jsonBase64Repository;

    @Autowired
    private IJsonBase64Service jsonBase64Service;

    @BeforeEach
    public void beforeEach() {
        jsonBase64Repository.deleteByValueId("1");
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void basicTest() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).contains("Not Found");
    }

    @Test
    public void createLeftJson() {
        Base64Input base64Input = Base64Input.builder()
                .value("test")
                .build();

        final ResponseEntity<?> response = this.restTemplate.postForEntity("/v1/diff/1/left", base64Input, ResponseEntity.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void createRightJson() {
        Base64Input base64Input = Base64Input.builder()
                .value("test")
                .build();

        final ResponseEntity<?> response = this.restTemplate.postForEntity("/v1/diff/1/right", base64Input, ResponseEntity.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void createGetDifferencesEqual() {
        Base64Input leftBase64Input = Base64Input.builder()
                .value("test")
                .build();

        Base64Input rightBase64Input = Base64Input.builder()
                .value("test")
                .build();

        this.restTemplate.postForEntity("/v1/diff/1/left", leftBase64Input, ResponseEntity.class);
        this.restTemplate.postForEntity("/v1/diff/1/right", rightBase64Input, ResponseEntity.class);
        final ResponseEntity<Result> response = this.restTemplate.getForEntity("/v1/diff/1", Result.class);

        Result result = response.getBody();
        assertEquals(result.getResult(), ResultType.EQUAL);
    }

    @Test
    public void createGetDifferenceSize() {
        Base64Input leftBase64Input = Base64Input.builder()
                .value("testtest")
                .build();

        Base64Input rightBase64Input = Base64Input.builder()
                .value("test")
                .build();

        this.restTemplate.postForEntity("/v1/diff/1/left", leftBase64Input, ResponseEntity.class);
        this.restTemplate.postForEntity("/v1/diff/1/right", rightBase64Input, ResponseEntity.class);
        final ResponseEntity<Result> response = this.restTemplate.getForEntity("/v1/diff/1", Result.class);

        Result result = response.getBody();
        assertEquals(result.getResult(), ResultType.DIFFERENT_SIZE);
    }

    @Test
    public void createGetDifference() {
        Base64Input leftBase64Input = Base64Input.builder()
                .value("test1")
                .build();

        Base64Input rightBase64Input = Base64Input.builder()
                .value("test2")
                .build();

        this.restTemplate.postForEntity("/v1/diff/1/left", leftBase64Input, ResponseEntity.class);
        this.restTemplate.postForEntity("/v1/diff/1/right", rightBase64Input, ResponseEntity.class);
        final ResponseEntity<Result> response = this.restTemplate.getForEntity("/v1/diff/1", Result.class);

        Result result = response.getBody();
        assertEquals(result.getResult(), ResultType.DIFFERENT);
        final List<Difference> differences = result.getDifferences();
        Integer expectedOffset = 4;
        Integer expectedLength = 1;
        assertEquals(differences.get(0).getOffset(), expectedOffset);
        assertEquals(differences.get(0).getLength(), expectedLength);
    }

    @Test
    public void createLeftWithoutCorrectBody() {
        Base64Input leftBase64Input = Base64Input.builder()
                .value("")
                .build();

        final ResponseEntity<Object> response = this.restTemplate.postForEntity("/v1/diff/1/left", leftBase64Input, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getDifferenceWithNotFoundException() {
        Base64Input leftBase64Input = Base64Input.builder()
                .value("test")
                .build();

        this.restTemplate.postForEntity("/v1/diff/2/left", leftBase64Input, Object.class);
        final ResponseEntity<Result> result = this.restTemplate.getForEntity("/v1/diff/1", Result.class);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
