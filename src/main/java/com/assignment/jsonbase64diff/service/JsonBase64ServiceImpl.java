package com.assignment.jsonbase64diff.service;

import com.assignment.jsonbase64diff.comparator.Comparator;
import com.assignment.jsonbase64diff.exception.Base64InputNotFoundException;
import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Base64InputType;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.repository.IJsonBase64Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Interface implementation responsible to manipulate the data in repository
 */
@Service
public class JsonBase64ServiceImpl implements IJsonBase64Service {

    private final IJsonBase64Repository jsonBase64Repository;
    private final Comparator comparator;

    @Autowired
    public JsonBase64ServiceImpl(IJsonBase64Repository jsonBase64Repository, Comparator comparator) {
        this.jsonBase64Repository = jsonBase64Repository;
        this.comparator = comparator;
    }

    @Override
    public void saveJsonBase64(Base64Input base64Input, String id, Base64InputType base64InputType ) {
        final Base64Input.Base64InputBuilder inputBuilder = Base64Input.builder();
        final Optional<Base64Input> base64InputOptional =
                jsonBase64Repository.findByValueIdAndBase64InputType(id, base64InputType);
        base64InputOptional.ifPresent(input -> inputBuilder.id(input.getId()));
        final Base64Input input = inputBuilder
                .valueId(id)
                .value(base64Input.getValue())
                .base64InputType(base64InputType)
                .build();
        jsonBase64Repository.save(input);
    }

    @Override
    public Result getJsonBase64Diffs(String id) {
        final Optional<Base64Input> leftBase64InputOptional =
                jsonBase64Repository.findByValueIdAndBase64InputType(id, Base64InputType.LEFT);
        final Optional<Base64Input> rightBase64InputOptional =
                jsonBase64Repository.findByValueIdAndBase64InputType(id, Base64InputType.RIGHT);
        final Base64Input leftBase64Input = leftBase64InputOptional.orElseThrow(() -> new Base64InputNotFoundException(id));
        final Base64Input rightBase64Input = rightBase64InputOptional.orElseThrow(() -> new Base64InputNotFoundException(id));
        return comparator.compare(leftBase64Input, rightBase64Input);
    }
}
