package com.assignment.jsonbase64diff.repository;

import com.assignment.jsonbase64diff.model.Base64Input;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IJsonBase64Repository extends MongoRepository<Base64Input, String> {
    Optional<List<Base64Input>> findByValueId(String valueId);
    void deleteByValueId(String valueId);
}
