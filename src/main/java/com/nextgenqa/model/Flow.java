package com.nextgenqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flow {
    private String name;
    private List<Step> steps;

    @Override
    public String toString() {
        return "Flow{" +
                "name='" + name + '\'' +
                ", steps=" + steps +
                '}';
    }
}
