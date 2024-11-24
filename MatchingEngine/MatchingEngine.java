import io.jenetics.*;
import io.jenetics.engine.*;
import java.util.List;
import java.util.Map;

public class MatchingEngine {
    private List<HousingOption> housingOptions; 
    private Map<String, Double> weights;
    public static final int TOP_N = 10;
    // number of houses to return
    public MatchingEngine(List<HousingOption> housingOptions, Map<String, Double> weights) {
        this.housingOptions = housingOptions;
        this.weights = weights;
    }
    /**
     * Calculate the score of a house based on weights and its characteristics
     */

    public double evaluate(HousingOption ho) {
        double score = 0.0;
        score += weights.getOrDefault("cost", 0.0) * (1.0 / ho.getCost());
        score += weights.getOrDefault("size", 0.0) * ho.getSize();
        score += weights.getOrDefault("distanceFromUni", 0.0) * ho.getDistanceFromUni();
        score += weights.getOrDefault("distanceFromMeans", 0.0) * ho.getDistanceFromMeans();
        return score;
    }

   /**
    * Optimize and return the best house
    */
   
    public HousingOption optimize() {
        Engine<DoubleGene, Double> engine = Engine.builder(
            individual -> evaluate(housingOptions.get((int) Math.floor(individual.getdoubleValue()))),
            Codecs.ofScalar(DoubleRange.of(0, housingOptions.size()))
        ).build();

        Phenotype<DoubleGene, Double> best = engine.stream()
                    .limit(100)
                    .collect(EvolutionResult.toBestPhenotype());
                    
        return housingOptions.get(best.genotype().gene().allele().intValue());
         
        
    }

}
