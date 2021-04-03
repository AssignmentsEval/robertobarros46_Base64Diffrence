package com.assignment.jsonbase64diff.controller;

import com.assignment.jsonbase64diff.exception.MissingValueException;
import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Base64InputType;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.service.IJsonBase64Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/diff")
@Slf4j
public class JsonDiffController {

    private final IJsonBase64Service jsonBase64Service;

    @Autowired
    public JsonDiffController(IJsonBase64Service jsonBase64Service) {
        this.jsonBase64Service = jsonBase64Service;
    }

    /**
     * Left json endpoint.
     *
     * I have an idea here, instead of passing the id via Path variable, maybe since it is a post method, would be
     * better to pass directly through the body. But, will still follow how the requisites were sent to me.
     *
     * @param id Operation id.
     * @param base64Input JSON-based data input
     * @return HTTP Response: 201 - Created.
     */
    @PostMapping("/{id}/left")
    public ResponseEntity<?> addLef(@PathVariable String id, @RequestBody @Validated Base64Input base64Input) {
        if (base64Input.getValue() == null || base64Input.getValue().isEmpty()) {
            throw new MissingValueException();
        }
        log.info("Request made to left post endpoint with id: {} and value {}", id, base64Input.getValue());
        jsonBase64Service.saveJsonBase64(base64Input, id, Base64InputType.LEFT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Right json endpoint.
     *
     * @param id Operation id.
     * @param base64Input JSON-based data input
     * @return HTTP Response: 201 - Created.
     */
    @PostMapping("/{id}/right")
    public ResponseEntity<?> addRight(@PathVariable String id, @RequestBody @Validated Base64Input base64Input) {
        if (base64Input.getValue() == null || base64Input.getValue().isEmpty()) {
            throw new MissingValueException();
        }
        jsonBase64Service.saveJsonBase64(base64Input, id, Base64InputType.RIGHT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Get differences endpoint.
     *
     * @param id Operation id.
     * @return HTTP Response: 200 - with the result of comparison.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result> getDifferences(@PathVariable String id) {
        final Result result = jsonBase64Service.getJsonBase64Diffs(id);
        return ResponseEntity.ok().body(result);
    }
}
