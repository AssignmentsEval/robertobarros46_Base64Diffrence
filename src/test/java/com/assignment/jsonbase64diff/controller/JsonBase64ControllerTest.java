package com.assignment.jsonbase64diff.controller;

import com.assignment.jsonbase64diff.exception.Base64InputNotFoundException;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.model.ResultType;
import com.assignment.jsonbase64diff.repository.IJsonBase64Repository;
import com.assignment.jsonbase64diff.service.IJsonBase64Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JsonBase64Controller.class, excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class JsonBase64ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IJsonBase64Service jsonBase64Service;

    @MockBean
    private IJsonBase64Repository jsonBase64Repository;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void testGetDifferences() throws Exception {
        final Result expectedResult = Result.builder()
                .id("1")
                .resultType(ResultType.EQUAL)
                .build();
        when(jsonBase64Service.getJsonBase64Diffs("1")).thenReturn(expectedResult);

        this.mvc.perform(get("/v1/diff/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":\"1\",\"resultType\":\"EQUAL\"}"));
    }

    @Test
    public void testCreateLeft() throws Exception {
        this.mvc.perform(post("/v1/diff/1/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": \"V0FFUwo=\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateRight() throws Exception {
        this.mvc.perform(post("/v1/diff/1/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": \"V0FFUwo=\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateLeftBadRequest() throws Exception {
        this.mvc.perform(post("/v1/diff/1/left").contentType(MediaType.APPLICATION_JSON)
                .content("{\"wrongValue\": \"V0FFUwo=\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateRightBadRequest() throws Exception {
        this.mvc.perform(post("/v1/diff/1/right").contentType(MediaType.APPLICATION_JSON)
                .content("{\"wrongValue\": \"V0FFUwo=\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetDifferencesNotFoundException() throws Exception {
        when(jsonBase64Service.getJsonBase64Diffs("1")).thenThrow(Base64InputNotFoundException.class);

        this.mvc.perform(get("/v1/diff/1"))
                .andExpect(status().isNotFound());
    }
}
