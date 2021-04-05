package com.assignment.jsonbase64diff.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultTest {

    @Test
    public void builderTest() {
        final String expectedId = "1";
        final ResultType expectedResultType = ResultType.EQUAL;
        final List<Difference> expectedDifferences = new ArrayList<>();
        final Integer expectedOffset = 4;
        final Integer expectedLength = 1;

        final Difference difference = Difference.builder()
                .offset(expectedOffset)
                .length(expectedLength)
                .build();
        expectedDifferences.add(difference);
        final Result result = Result.builder()
                .id(expectedId)
                .resultType(expectedResultType)
                .differences(expectedDifferences)
                .build();

        assertEquals(expectedId, result.getId());
        assertEquals(expectedResultType, result.getResultType());
        assertEquals(expectedDifferences, result.getDifferences());
    }

}