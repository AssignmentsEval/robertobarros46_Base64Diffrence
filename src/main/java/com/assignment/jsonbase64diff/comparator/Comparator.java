package com.assignment.jsonbase64diff.comparator;

import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Difference;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.model.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class Comparator {

    /**
     * Compare two Base64 strings and returns the result.
     * @param leftBase64Input left input
     * @param rightBase64Input right input
     * @return Returns the result of the comparison between both strings
     */
    public Result compare(Base64Input leftBase64Input, Base64Input rightBase64Input) {
        log.info("Started comparing both left and right input: {} -- {}",
                leftBase64Input.getValue(), rightBase64Input.getValue());
        Result.ResultBuilder resultBuilder = Result.builder().id(leftBase64Input.getValueId());

        if (leftBase64Input.getValue().equals(rightBase64Input.getValue())) {
            resultBuilder.resultType(ResultType.EQUAL);
        } else if (leftBase64Input.getValue().length() != rightBase64Input.getValue().length()) {
            resultBuilder.resultType(ResultType.SIZE_MISMATCH);
        } else {
            resultBuilder.resultType(ResultType.VALUE_MISMATCH);
            resultBuilder.differences(processDifferences(leftBase64Input.getValue(), rightBase64Input.getValue()));
        }
        log.info("Finished comparing both left and right input, result is: {} ",
                resultBuilder);
        return resultBuilder.build();
    }

    /**
     * Processes the differences between two strings of same size, but its values has a mismatch,
     * calculating the offset and length of that difference
     * @param leftValue left value
     * @param rightValue right value
     * @return List of differences calculated
     */
    private List<Difference> processDifferences(String leftValue, String rightValue) {
        List<Difference> differences = new LinkedList<>();

        int length = 0;
        int offset = -1;
        for (int i = 0; i <= leftValue.length(); i++) {
            if (i < leftValue.length()
                    && leftValue.charAt(i) != rightValue.charAt(i)) {
                length++;
                if (offset < 0) {
                    offset = i;
                }
            } else if (offset != -1) {
                final Difference difference = Difference.builder().offset(offset).length(length).build();
                differences.add(difference);
                length = 0;
                offset = -1;
            }
        }
        return differences;
    }
}
