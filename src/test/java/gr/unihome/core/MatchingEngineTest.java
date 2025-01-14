/*
 * Copyright 2025 [Your Name or Your Organization]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package gr.unihome.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MatchingEngineTest {
    private MatchingEngine matchingEngine;
    private List<HousingOption> housingOptions;
    private Criteria student;
    private List<String> criteria;
    private List<Integer> priorities;

    @BeforeEach
    public void setUp() {
        housingOptions = Arrays.asList(
            new HousingOption(1, "City Center", "123 Main St", 500, 2, 30, 2.0, 1.0, 2, 1, 40.7128, -74.0060),
            new HousingOption(2, "Suburbs", "456 Elm St", 600, 3, 40, 1.5, 0.8, 3, 0, 40.7306, -73.9352),
            new HousingOption(3, "Downtown", "789 Oak St", 450, 1, 25, 3.0, 1.2, 1, 1, 40.7580, -73.9855)
        );
        student = new Criteria("John", "ΕΚΠΑ", 500, 100, 2.0, 25);
        criteria = Arrays.asList("cost", "size", "distanceFromUni", "distanceFromMeans");
        priorities = Arrays.asList(1, 2, 3, 4);
        matchingEngine = new MatchingEngine(housingOptions, criteria, priorities);
    }

    @Test
    public void testOptimize() {
        HousingOption bestOption = matchingEngine.optimize(student);
        assertNotNull(bestOption, "Best option should not be null");
        assertTrue(housingOptions.contains(bestOption), "Best option should be a part of HousingOption");
    }

    @Test
    public void testFindOtherBestSolutions() {
        HousingOption bestOption = matchingEngine.optimize(student);
        List<HousingOption> solutions = matchingEngine.findOtherBestSolutions(student, 0.2, bestOption);
        assertNotNull(solutions, "Solutions should not be null");
        assertTrue(solutions.size() <= MatchingEngine.RECOMMENDED_COUNT, 
                   "Solutions should not exceed the RECOMMENDED_COUNT");
    }

    @Test
    public void testWeightsCalculation() {
        Map<String, Double> expectedWeights = WeightCalculator.calculator(criteria, priorities);
        Map<String, Double> weights = matchingEngine.getWeights();
        assertNotNull(weights, "Weights should not be null");
        assertEquals(1.0, weights.values().stream().mapToDouble(Double::doubleValue).sum(), 0.001,
                     "Retrieved weights should sum to 1");

        expectedWeights.forEach((key, value) -> {
            assertEquals(value, weights.get(key), 0.001,
                         "Weight for " + key + " should match expected value");
        });
    }
}

