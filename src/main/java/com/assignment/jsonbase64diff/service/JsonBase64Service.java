package com.assignment.jsonbase64diff.service;

import com.assignment.jsonbase64diff.comparator.Comparator;
import com.assignment.jsonbase64diff.exception.Base64InputNotFoundException;
import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Base64InputType;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.repository.IJsonBase64Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JsonBase64Service implements IJsonBase64Service {

    private final IJsonBase64Repository jsonBase64Repository;

    @Autowired
    public JsonBase64Service(IJsonBase64Repository jsonBase64Repository) {
        this.jsonBase64Repository = jsonBase64Repository;
    }

    @Override
    public void saveJsonBase64(Base64Input base64Input, String id, Base64InputType base64InputType ) {
        final Base64Input input = Base64Input.builder()
                .valueId(id)
                .value(base64Input.getValue())
                .base64InputType(base64InputType)
                .build();
        jsonBase64Repository.save(input);
    }

    @Override
    public Result getJsonBase64Diffs(String id) {
        final Optional<List<Base64Input>> base64InputOptional = jsonBase64Repository.findByValueId(id);
        final List<Base64Input> base64Inputs = base64InputOptional.orElseThrow(() -> new Base64InputNotFoundException(id));
        final Optional<Base64Input> leftBase64Input = base64Inputs.stream()
                .filter(base64Input -> Base64InputType.LEFT.equals(base64Input.getBase64InputType()))
                .findFirst();
        final Optional<Base64Input> rightBase64Input = base64Inputs.stream()
                .filter(base64Input -> Base64InputType.RIGHT.equals(base64Input.getBase64InputType()))
                .findFirst();
        if (leftBase64Input.isPresent() && rightBase64Input.isPresent()) {
            Comparator comparator = new Comparator(leftBase64Input.get(), rightBase64Input.get());
            return comparator.compare();
        } else {
            throw new Base64InputNotFoundException(id);
        }
    }
}
