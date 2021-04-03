package com.assignment.jsonbase64diff.comparator;

import com.assignment.jsonbase64diff.model.Base64Input;
import com.assignment.jsonbase64diff.model.Difference;
import com.assignment.jsonbase64diff.model.Result;
import com.assignment.jsonbase64diff.model.ResultType;

import java.util.LinkedList;
import java.util.List;

public class Comparator {

    private final Base64Input leftBase64Input;
    private final Base64Input rightBase64Input;

    public Comparator(Base64Input leftBase64Input, Base64Input rightBase64Input) {
        this.leftBase64Input = leftBase64Input;
        this.rightBase64Input = rightBase64Input;
    }

    public Result compare() {

        Result.ResultBuilder resultBuilder = Result.builder().id(leftBase64Input.getValueId());

        if (leftBase64Input.getValue().equals(rightBase64Input.getValue())) {
            resultBuilder.result(ResultType.EQUAL);
        } else if (leftBase64Input.getValue().length() != rightBase64Input.getValue().length()) {
            resultBuilder.result(ResultType.DIFFERENT_SIZE);
        } else {
            resultBuilder.result(ResultType.DIFFERENT);
            resultBuilder.differences(processDiff(leftBase64Input, rightBase64Input));
        }
        return resultBuilder.build();
    }

    private List<Difference> processDiff(Base64Input leftBase64Input, Base64Input rightBase64Input) {
        List<Difference> differences = new LinkedList<>();

        int length = 0;
        int offset = -1;
        for (int i = 0; i < leftBase64Input.getValue().length(); i++) {
            if (leftBase64Input.getValue().charAt(i) != rightBase64Input.getValue().charAt(i)) {
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