package com.assignment.jsonbase64diff.repository;

import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Base64InputType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Repository interface to access Mongo features.
 */
@Component
public interface IJsonBase64Repository extends MongoRepository<Base64Input, String> {
    Optional<Base64Input> findByValueIdAndBase64InputType(String valueId, Base64InputType base64InputType);
    void deleteByValueId(String valueId);
}
