package com.assignment.jsonbase64diff.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferenceTest {

    @Test
    public void builderTest() {
        final Integer expectedOffset = 4;
        final Integer expectedLength = 1;

        final Difference difference = Difference.builder()
                .offset(expectedOffset)
                .length(expectedLength)
                .build();

        assertEquals(expectedOffset, difference.getOffset());
        assertEquals(expectedLength, difference.getLength());
    }
}