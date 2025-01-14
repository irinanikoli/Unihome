/**
 * Copyright 2025 Unihome

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */



/**
 * This file uses the Jenetics library, licensed under the Apache License, Version 2.0.
 * For more details, see http://www.apache.org/licenses/LICENSE-2.0
 */

package gr.unihome.core;

import io.jenetics.DoubleGene;
import io.jenetics.Phenotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.DoubleRange;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

public class MatchingEngine {
    private static final Logger logger = AppLogger.getLogger();
    //Download of Logger form AppLogger.java
    private List<HousingOption> housingOptions; 
    private Map<String, Double> weights;
    public static final int RECOMMENDED_COUNT = 10;
    // number of houses to return
    public MatchingEngine(List<HousingOption> housingOptions, List<String> criteria, List<Integer> priorities) {
        this.housingOptions = housingOptions;
        this.weights = WeightCalculator.calculator(criteria, priorities);
    }
    // Retrieve the calculated from calculator() weights
     public Map<String, Double> getWeights() {
        return weights;
    }
    /**
     * Calculate the score of a house based on weights and its characteristics
     * @param ho as an object of HousingOption
     * @param student as an object of criteria
     */

     public double evaluate(HousingOption ho, Criteria student) {
        //Basic calculation of scores
        double score = weights.getOrDefault("cost", 0.0) * (1.0 / ho.getCost())
                     + weights.getOrDefault("size", 0.0) * ho.getSize()
                     + weights.getOrDefault("distanceFromUni", 0.0) * (1.0 / ho.getDistanceFromUni())
                     + weights.getOrDefault("distanceFromMeans", 0.0) * (1.0 / ho.getDistanceFromMeans());
    
        // Put penalties
        if (ho.getCost() > student.getBudget()) {
            double violationRatio = ho.getCost() / student.getBudget();
            score -= weights.getOrDefault("cost", 0.0) * (violationRatio - 1.0) * 0.5; // Cost Penalty
        }
    
        if (ho.getDistanceFromUni() > student.getMaxDistanceFromUni()) {
            double violationRatio = ho.getDistanceFromUni() / student.getMaxDistanceFromUni();
            score -= weights.getOrDefault("distanceFromUni", 0.0) * (violationRatio - 1.0) * 0.5; 
            //Distance from univeristy penalty
        }
    
        if (ho.getDistanceFromMeans() > student.getMaxDistanceFromMeans()) {
            double violationRatio = ho.getDistanceFromMeans() / student.getMaxDistanceFromMeans();
            score -= weights.getOrDefault("distanceFromMeans", 0.0) * (violationRatio - 1.0) * 0.5;
             // Distance from means penalty
        }

        if (ho.getSize() < student.getMinSqMeters()) {
            double violationRatio = student.getMinSqMeters() / ho.getSize();
            score -= weights.getOrDefault("size", 0.0) * (1.00 - violationRatio) * 0.5; // Ποινή μεγέθους
        }
    
        // Score should not be negative
        return Math.max(score, 0.0);
    }
    

    


   /**
    * Optimize and return the best house
    * Engine builds a genetic algorithm
    * genotype like a potential solution in genetic algorithm's population
    * Codecs.ofScalar defines the range of the gene's values (0  to the size of list)
    * limit(100): Genetic Optimization for 100 generations
    * collect(EvolutionResult.toBestPhenotype()) : returns the best atom (house with geatest score)
    * return housingOptions.get(...): converts result into house of the list
    * @param student as an object of Criteria
    */
   
   
    public HousingOption optimize(Criteria student) {
        try {
            Engine<DoubleGene, Double> engine = Engine.<DoubleGene, Double>builder(
                genotype -> {
                   DoubleGene gene = genotype.gene(); // Access to the first gene
                   double index = gene.doubleValue(); // Value of gene
                   return evaluate(housingOptions.get((int) Math.floor(index)), student);
                },
                Codecs.ofScalar(DoubleRange.of(0, housingOptions.size() - 1)).encoding() // Genotype factory (generate individuals)
            ).build();
        
        
            Phenotype<DoubleGene, Double> best = engine.stream()
                    .limit(100)
                    .collect(EvolutionResult.toBestPhenotype());

            return housingOptions.get(best.genotype().gene().allele().intValue()); 
        } catch(NullPointerException e) {
            logger.severe("NullPointerException occured: problem with the list : " + e.getMessage()); 
            e.printStackTrace();  
        } catch(Exception e) {
            logger.severe("Error during optimization : " + e.getMessage());  
            e.printStackTrace();
        }
       return null; //returns null in case of an error
    }

/**
 * Find houses with similar score to the best and return them
 * @param student as an object of Criteria
 * @param threshold the limit for the accepted scores in the returned list
 * @param bestOption the best option found from the optimize mehtod and needs to be excluded from the final
 */

  public List<HousingOption> findOtherBestSolutions(Criteria student, double threshold, HousingOption bestOption) {
    try {
        // Calculate scores for all houses
        Map<HousingOption, Double> scores = housingOptions.stream()
                .collect(Collectors.toMap(option -> option, ho -> evaluate(ho, student)));

        // Find max score
        double maxScore = scores.values().stream()
                .max(Double::compare)
                .orElse(0.0);

        // Exclude the best option from both lists
        List<HousingOption> topChoices = housingOptions.stream()
                .filter(option -> !option.equals(bestOption)) // Exclude bestOption
                .filter(option -> Double.compare(scores.get(option), maxScore) == 0)
                .limit(RECOMMENDED_COUNT)
                .collect(Collectors.toList());

        // Choices between maxScore and threshold (excluding bestOption)
        List<HousingOption> similarOptions = housingOptions.stream()
                .filter(option -> !option.equals(bestOption)) // Exclude bestOption
                .filter(option -> {
                    double score = scores.get(option);
                    return score <= maxScore && score > maxScore - threshold;
                })
                .sorted(Comparator.comparingDouble(scores::get).reversed())
                .collect(Collectors.toList());

        // Combine top choices and similar options
        List<HousingOption> result = new ArrayList<>(topChoices);
        similarOptions.stream()
                .limit(Math.max(0, RECOMMENDED_COUNT - result.size())) // Ensure we respect RECOMMENDED_COUNT
                .forEach(result::add);

        return result.stream().distinct().limit(RECOMMENDED_COUNT).toList(); // Remove duplicates and limit size
    } catch (NullPointerException e) {
        logger.severe("NullPointerException occurred: problem with the list : " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        logger.severe("Error finding other best solutions: " + e.getMessage());
        e.printStackTrace();
    }
    return new ArrayList<>(); // Return empty list in case of an error
}

}
