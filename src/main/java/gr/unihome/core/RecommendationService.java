package gr.unihome.core;

import java.util.List;
import java.util.Map;


public class RecommendationService {

    /* A class to recommend houses based on MatchingEngine.java's results
     * It plays the connecting role between other classes and MatchingEngine
     * Returns the best house and the list of recommendations for the student    
     */

    private MatchingEngine matchingEngine;
    public RecommendationService(List<HousingOption> housingOptions, Map<String, Double> weights) {
        //initialization based on the list of housing and student's weights of preference
        this.matchingEngine = new MatchingEngine(housingOptions, weights);
    }

    //Provides the best house for each student
    public HousingOption getBestHouse(Criteria studentCriteria) {
        return matchingEngine.optimize(studentCriteria);
    } 
    /*
     * Provides the list with the best recommendations
     * @param studentCriteria as the criteria of each student
     * @param treshold as the limit of divergence between maxScore and scores of the recommended houses
     * @return list of recommedations  
     */

    public List<HousingOption> getBestRecommendationList(Criteria studentCriteria, double treshold) {
        return matchingEngine.findOtherBestSolutions(studentCriteria, treshold);
    } 

    
}
