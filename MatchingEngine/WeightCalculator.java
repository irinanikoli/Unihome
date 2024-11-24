import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;


public class WeightCalculator {
    /**
     *  calculates the weigths of students' preferences
     *  weghts are normalized so that they add to 1
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
                               .map(priorities -> 1.0/priorities)
                               .collect(Collectors.toList()) ;
        // sum of all inverses
        double sum = inverses.stream()
                     .mapToDouble(Double :: doubleValues)
                     .sum(); 
        // normalization
        List<Double> normalizedWeights = inverses.stream()
                                         .map(inverses -> inverses/sum)
                                         .collect(Collectors.toList());
        Map<String, Double> weights = new HashMap<>;
        for (int i =0; i < criteria.size(); i++) {
            weights.put(criteria.get(1), normalizedWeights.get(i));
        }
        return weights;
    }
}
