package com.nextgenqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    private String action;
    private String xPath;
    private String value;

    @Override
    public String toString() {
        return "Step{" +
                "action='" + action + '\'' +
                ", xPath='" + xPath + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
