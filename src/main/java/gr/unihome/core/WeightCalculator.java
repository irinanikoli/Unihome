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

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;


public class WeightCalculator {
    /**
     *  calculates the weigths of students' preferences
     *  weights are normalized so that they add to 1
     * 
     * @param criteria Names of citeria (cost, size etc)
     * @param priorities Map of priorities (1= higher, n = lower)
     * @return Map of weights for each parameter
     */
    public static Map<String, Double> calculator(List<String> criteria, List<Integer> priorities) {
        if (criteria.size() != priorities.size()) {
            throw new IllegalArgumentException
               ("Criteria and parameters should have the same size");
        } 
        //calculate inverse of priorities
        List<Double> inverses = priorities.stream()
                     .map(priority -> 1.0/priority)
                     .collect(Collectors.toList()) ;
        // sum of all inverses
        double sum = inverses.stream()
                     .mapToDouble(Double :: doubleValue)
                     .sum(); 
        // normalization
        List<Double> normalizedWeights = inverses.stream()
                     .map(inverse -> inverse/sum)
                     .collect(Collectors.toList());
        Map<String, Double> weights = new HashMap<>();
        for (int i =0; i < criteria.size(); i++) {
            weights.put(criteria.get(i), normalizedWeights.get(i));
        }
        return weights;
    }
}
