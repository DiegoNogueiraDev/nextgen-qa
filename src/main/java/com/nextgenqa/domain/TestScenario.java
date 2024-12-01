package com.nextgenqa.domain;

import lombok.Data;
import java.util.List;

@Data
public class TestScenario {
    private String name;
    private List<TestStep> steps;
    private List<TestStep> fallback;
}
