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
     */

     public double evaluate(HousingOption ho, Criteria student) {
        double baseScore = weights.getOrDefault("cost", 0.0) * (1.0 / ho.getCost())
                         + weights.getOrDefault("size", 0.0) * ho.getSize()
                         + weights.getOrDefault("distanceFromUni", 0.0) * ho.getDistanceFromUni()
                         + weights.getOrDefault("distanceFromMeans", 0.0) * ho.getDistanceFromMeans();
    
        double costPenalty = (ho.getCost() > student.getBudget())
            ? Math.max(1.0 - weights.getOrDefault("cost", 0.0) * ((ho.getCost() / student.getBudget()) - 1.0), 0.0)
            : 1.0;
    
        double distanceMeansPenalty = (ho.getDistanceFromMeans() > student.getMaxDistanceFromMeans())
            ? Math.max(1.0 - weights.getOrDefault("distanceFromMeans", 0.0) * ((ho.getDistanceFromMeans() / student.getMaxDistanceFromMeans()) - 1.0), 0.0)
            : 1.0;
    
        double distanceUniPenalty = (ho.getDistanceFromUni() > student.getMaxDistanceFromUni())
            ? Math.max(1.0 - weights.getOrDefault("distanceFromUni", 0.0) * ((ho.getDistanceFromUni() / student.getMaxDistanceFromUni()) - 1.0), 0.0)
            : 1.0;
    
        double sizePenalty = (ho.getSize() < student.getMinSqMeters())
            ? Math.max(1.0 - weights.getOrDefault("size", 0.0) * (1.0 - (ho.getSize() / student.getMinSqMeters())), 0.0)
            : 1.0;
    
        double finalScore = baseScore * costPenalty * distanceMeansPenalty * distanceUniPenalty * sizePenalty;
        return Math.max(finalScore, 0.0);
    }
    


   /**
    * Optimize and return the best house
    * Engine builds a genetic algorithm
    * genotype like a potential solution in genetic algorithm's population
    * Codecs.ofScalar defines the range of the gene's values (0  to the size of list)
    * limit(100): Genetic Optimization for 100 generations
    * collect(EvolutionResult.toBestPhenotype()) : returns the best atom (house with geatest score)
    * return housingOptions.get(...): converts result into house of the list
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
 */

  public List<HousingOption> findOtherBestSolutions(Criteria student, double treshold, HousingOption bestOption) {
        // calculate score for all the houses
        try {
            Map<HousingOption, Double> scores = housingOptions.stream()
                    .collect(Collectors.toMap(option -> option, ho -> evaluate(ho, student)));
                 
            //Find max score
            double maxScore = scores.values().stream()
                    .max(Double :: compare)
                    .orElse(0.0);
            // Choices with the best score
            List<HousingOption> topChoices = housingOptions.stream()
                    .filter(option -> Double.compare(scores.get(option), maxScore) == 0)
                    .limit(RECOMMENDED_COUNT)
                    .collect(Collectors.toList());
            // choices between maxScore and treshold(limit)
            List<HousingOption> similarOptions = housingOptions.stream()
                    .filter(option -> {
                        double scoree = scores.get(option);
                        //exclude the best option and listen to the conditdion
                        return !option.equals(bestOption) && scoree <= maxScore && scoree > maxScore - treshold;
                    }) 
                    .sorted(Comparator.comparingDouble(scores :: get).reversed())
                    .collect(Collectors.toList()); 
            // combination and constraint at RECOMMENDED_COUNT houses
            List<HousingOption> result = new ArrayList<>(topChoices);
                similarOptions.stream()
                    .limit(Math.max(0, RECOMMENDED_COUNT - result.size()))
                    .forEach(result :: add);
        
            return result;
        } catch(NullPointerException e) {
            logger.severe("NullPointerException occured: problem with the list : " + e.getMessage()); 
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
            logger.severe("Error finding other best solutions : " + e.getMessage());
        } 
    return new ArrayList<>(); //returns an empty ArrayList in case of an error
  }
}
