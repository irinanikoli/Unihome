package gr.unihome.core;

import org.junit.Test;
import org.junit.internal.ComparisonCriteria;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MatcingEngineTest {
    private MatchingEngine matchingEngine;
    private List<HousingOption> housingOptions;
    private Criteria student ;
    private Map<String, Double> weights;
    @Before
    public void setUp() {
        housingOptions = Arrays.asList(
            new HousingOption("H1", "City Center", "123 Main St", 500, 2, 30.0, 2.0, 1.0, 2, true, 40.7128, -74.0060),
            new HousingOption("H2", "Suburbs", "456 Elm St", 600, 3, 40.0, 1.5, 0.8, 3, false, 40.7306, -73.9352),
            new HousingOption("H3", "Downtown", "789 Oak St", 450, 1, 25.0, 3.0, 1.2, 1, true, 40.7580, -73.9855)
        );
        student = new Criteria(500, 1.0, 2.0, 25);
        weights = new HashMap<>();
        weights.put("cost", 0.4);
        weights.put("size", 0.3);
        weights.put("distanceFromUni", 0.2);
        weights.put("distanceFromMeans", 0.1);


    }

}
