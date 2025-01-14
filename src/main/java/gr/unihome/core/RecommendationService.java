/*
 * Copyright 2025 Unihome
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RecommendationService {

    /* A class to recommend houses based on MatchingEngine.java's results
     * It plays the connecting role between other classes and MatchingEngine
     * Returns the best house and the list of recommendations for the student    
     */
    private static final Logger logger = AppLogger.getLogger();
    //Downloading Logger from AppLogger 
    private MatchingEngine matchingEngine;
    public RecommendationService(List<HousingOption> housingOptions, List<String> criteria, List<Integer> priorities) {
        //initialization based on the list of housing and student's weights of preference
        this.matchingEngine = new MatchingEngine(housingOptions, criteria, priorities);
    }

    /** Provides the best house for each student
     * @param studentCriteria as an object of Criteria
     */ 
    public HousingOption getBestHouse(Criteria studentCriteria) {
        try {

           return matchingEngine.optimize(studentCriteria); 
        } catch(Exception e) {
            logger.severe("Error getting the best house : " + e.getMessage());
            e.printStackTrace();
        }
        return null; //returns null in case of an error
    } 
    /**
     * Provides the list with the best recommendations
     * @param studentCriteria as the criteria of each student
     * @param treshold as the limit of divergence between maxScore and scores of the recommended houses
     * @return list of recommedations  
     */

    public List<HousingOption> getBestRecommendationList(Criteria studentCriteria, double treshold, HousingOption bestOption) {
        try {
           return matchingEngine.findOtherBestSolutions(studentCriteria, treshold, bestOption);
        } catch(Exception e) {
            logger.severe("Error getting the best recommendations : " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>(); //returns an empty ArrayList in case of an error
    }   
}
