import io.jenetics.DoubleGene;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.DoubleRange;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;

public class MatchingEngine {
    private List<HousingOption> housingOptions; 
    private Map<String, Double> weights;
    public static final int RECCOMENDED_COUNT = 10;
    // number of houses to return
    public MatchingEngine(List<HousingOption> housingOptions, Map<String, Double> weights) {
        this.housingOptions = housingOptions;
        this.weights = weights;
    }
    /**
     * Calculate the score of a house based on weights and its characteristics
     */

    public double evaluate(HousingOption ho, Student student) {
        double score = 0.0;
        score += weights.getOrDefault("cost", 0.0) * (1.0 / ho.getCost());
        score += weights.getOrDefault("size", 0.0) * ho.getSize();
        score += weights.getOrDefault("distanceFromUni", 0.0) * ho.getDistanceFromUni();
        score += weights.getOrDefault("distanceFromMeans", 0.0) * ho.getDistanceFromMeans();
        //if statements for penalties in the score
        // Proportional penalties based on student's preferances
        if (ho.getCost() > student.getMaxCost()) {
            double violationRatio = ho.getCost() / student.getCost();
            score =Math.max(1.0 - (weights.getOrDefault("cost", 0.0) * (violationRatio - 1.0)), 0.0);
        }
        if (ho.getDistanceFromMeans() > student.getDistanceFromMeans()) {
            double violationRatio = ho.getDistanceFromMeans() /  student.getDistanceFromMeans();
            score *= Math.max(1.0 - (weights.getOrDefault("distanceFromMeans", 0.0)), 0.0);
        }
        if (ho.getDistanceFromUni() > student.getDistanceFromUni()) {
            double violationRatio = ho.getDistanceFromUni() / student.getDistanceFromUni();
            score *= Math.max(1.0 - (weights.getOrDefault("distanceFromUni", 0.0)), 0.0);
        }
        if (ho.getSize() < student.getSize()) {
            double violationRatio = ho.getSize() / student.getSize();
            score *= Math.max(1.0 - (weights.getOrDefault("size", 0.0)) , 0.0);
        }

        return Math.max(score, 0.0);
    }

   /**
    * Optimize and return the best house
    * Engine builds a genetic algorithm
    * individual like a list index (0 for the 1st house)
    * Codecs.ofScalar defines the range of the gene's values (0  to the size of list)
    * limit(100): Genetic Optimization for 100 generations
    * collect(EvolutionResult.toBestPhenotype()) : returns the best atom (house with geatest score)
    * return housingOptions.get(...): converts result into house of the list
    */
   
    public HousingOption optimize(Student student) {
        Engine<DoubleGene, Double> engine = Engine.builder(
            individual -> evaluate(housingOptions.get((int) Math.floor(individual.getdoubleValue())), student),
            Codecs.ofScalar(DoubleRange.of(0, housingOptions.size()))
        ).build();

        Phenotype<DoubleGene, Double> best = engine.stream()
                    .limit(100)
                    .collect(EvolutionResult.toBestPhenotype());

        return housingOptions.get(best.genotype().gene().allele().intValue()); 
    }

/**
 * Find houses with similar score to the best and return them
 */

  public List<HousingOption> findOtherBestSolutions(double treshold) {
    // calculate score for all the houses
    Map<HousingOption, Double> scores = housingOptions.stream()
                    .collect(Collectors.toMap(option -> option, ho -> evaluate(housingOptions, student)));
        //Find max score
        double maxScore = scores.values().stream()
                    .max(Double :: compare)
                    .orElse(0.0);
        // Choices with the best score
        List<HousingOption> topChoices = housingOptions.stream()
                    .filter(option -> scores.get(option) == maxScore)
                    .collect(Collectors.toList());
        // choices between maxScore and treshold(limit)
        List<HousingOption> similarOptions = housingOptions.stream()
                      .filter(option -> {
                         double scoree = scores.get(option);
                         return scoree < maxScore && scoree > maxScore - treshold;
                      }) 
                      .sorted(Comparator.comparingDouble(scores :: get).reversed())
                      .collect(Collectors.toList()); 
        // combination and constraint at RECOMMENDED_COUNT houses
        List<HousingOption> result = new ArrayList<>(topChoices);
        similarOptions.stream()
            .limit(RECCOMENDED_COUNT - result.size())
            .forEach(result :: add);
        
        return result;
  }
}
