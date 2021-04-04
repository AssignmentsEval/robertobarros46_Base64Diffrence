package com.assignment.jsonbase64diff.service;

import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Base64InputType;
import com.assignment.jsonbase64diff.model.Result;
import org.springframework.stereotype.Service;

/**
 * Interface responsible to manipulate the data in repository
 */
@Service
public interface IJsonBase64Service {
    void saveJsonBase64(Base64Input base64Input, String id, Base64InputType base64InputType);
    Result getJsonBase64Diffs(String id);
}
