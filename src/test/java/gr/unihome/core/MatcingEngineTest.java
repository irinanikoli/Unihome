package gr.unihome.core;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MatcingEngineTest {
    private MatchingEngine matchingEngine;
    private List<HousingOption> housingOptions;
    private Criteria student ;
    private List<String> criteria;
    private List<Integer> priorities;
    @Before
    public void setUp() {
        housingOptions = Arrays.asList(
            new HousingOption(1, "City Center", "123 Main St", 500, 2, 30, 2.0, 1.0, 2, 1, 40.7128, -74.0060),
            new HousingOption(2, "Suburbs", "456 Elm St", 600, 3, 40, 1.5, 0.8, 3, 0, 40.7306, -73.9352),
            new HousingOption(3, "Downtown", "789 Oak St", 450, 1, 25, 3.0, 1.2, 1, 1, 40.7580, -73.9855)
        );
        student = new Criteria("John", "EΚΠΑ",500, 1.0, 2.0, 25);
        criteria = Arrays.asList("cost", "size", "distanceFromUni", "distanceFromMeans");
        priorities = Arrays.asList(1, 2, 3, 4);
        matchingEngine = new MatchingEngine(housingOptions, criteria, priorities);
    }
    @Test
    public void testOptimize() {
        HousingOption bestOption = matchingEngine.optimize(student);
        assertNotNull("Best option should not be null", bestOption);
        //testing the existance of a best solution
        assertTrue("best option should be a part of HousingOption", housingOptions.contains(bestOption));
        //testing the existance of a solution in the houses list
    }
    @Test
    public void testFindOtherBestSolutions() {
        List<HousingOption> solutions = matchingEngine.findOtherBestSolutions(student,0.2);
        assertNotNull("solutions should not be null", solutions);
        //testing the existance of solutions
        assertTrue("Solutions should not exceed the RECOMMENDED_COUNT", solutions.size() <= MatchingEngine.RECCOMENDED_COUNT);
        //testing the interval that solutions are accepted
    }
    @Test
    public void testWeightsCalculation() {
        //Calculating preferences based on method setup
        List<String> criteria = Arrays.asList("cost", "size", "distanceFromUni", "distanceFromMeans");
        List<Integer> priorities = Arrays.asList(1, 2, 3, 4); // Προτεραιότητες στο setup
        Map<String, Double> expectedWeights = WeightCalculator.calculator(criteria, priorities);
    
        Map<String, Double> weights = matchingEngine.getWeights();
        assertNotNull("Weights should not be null", weights);
    
        //Testing sum of weights
        assertEquals("Retrieved weights should sum to 1", 1.0, 
            weights.values().stream().mapToDouble(Double::doubleValue).sum(), 0.001);
    
        // Test for each weight
        expectedWeights.forEach((key, value) -> {
            assertEquals("Weight for " + key + " should match expected value", 
                value, weights.get(key), 0.001);
        });
    }

}
