package gr.unihome.core;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        HousesDatabase.initialize();
        UniversitiesDatabase.initialize();
        MeansDatabase.initialize();
        HousesDatabase.insertRandomHouses(15);
        UniversitiesDatabase.insertRandomUniversities(8);
        MeansDatabase.insertRandomMeans(5);
        String universityName = "Οικονομικό Πανεπιστήμιο Αθηνών";
        DistanceCalculator.calculateDistancesBetweenHousesAndUni(universityName);

        System.out.println("Υπολογισμός αποστάσεων μεταξύ σπιτιών και μέσων μεταφοράς:");
        DistanceCalculator.calculateDistancesBetweenHousesAndMeans();

        List<HousingOption> housingOptions = HousingOption.fetchHousingOptionsFromDB();
        if (!housingOptions.isEmpty()) {
            System.out.println("Λίστα με διαθέσιμα σπίτια");
            for (HousingOption house : housingOptions) {
                System.out.println("ID: " + house.getId() + ", Τοποθεσία: " + house.getLocation() +
                    ", Απόσταση από μέσα: " + house.getDistanceFromMeans() +
                    " Απόσταση από το Πανεπιστήμιο: " + house.getDistanceFromUni() +
                    ", Κόστος: " + house.getCost() + " ευρώ");
            }
        } else {
            System.out.println("Δεν υπάρχουν διαθέσιμα σπίτια στη βάση δεδομένων.");

        }
        List<String> criteria = new ArrayList<>();
        criteria.add("Budget");
        criteria.add("DistanceFromUniversity");
        criteria.add("Size");

        List<Integer> priorities = new ArrayList<>();
        priorities.add(3); // Υψηλή προτεραιότητα για Budget
        priorities.add(2); // Μεσαία προτεραιότητα για DistanceFromUniversity
        priorities.add(1); // Χαμηλή προτεραιότητα για Size
        double threshold = 5;
        MatchingEngine objMaching = new MatchingEngine(housingOptions, criteria, priorities);
 
                 // Δημιουργία αντικειμένου Criteria με τις απαραίτητες τιμές
                 Criteria studentCriteria = new Criteria(
                     "Γιάννης",
                     "Οικονομικό Πανεπιστήμιο Αθηνών",
                     500,
                     30,
                     20000,
                     30500
                 );
                 
         RecommendationService recommendationService = new RecommendationService(housingOptions, criteria, priorities);
 
         HousingOption bestHouse = recommendationService.getBestHouse(studentCriteria);
 
 
         List<HousingOption> recommendations = recommendationService.getBestRecommendationList(studentCriteria, threshold, bestHouse);
 
 
 
 
         if (bestHouse != null) {
             System.out.println("Το καλύτερο σπίτι είναι: " + bestHouse.toString());
         } else {
             System.out.println("Δεν βρέθηκε κατάλληλο σπίτι.");
         }
         if (!recommendations.isEmpty()) {
            for (HousingOption option : recommendations) {
                System.out.println(option);
            }
        } else {
            System.out.println("Δεν βρέθηκαν προτάσεις.");
        }

        for (HousingOption ho : housingOptions) {
        double scoressss = objMaching.evaluate(ho, studentCriteria);
        System.out.println(scoressss);
        }

        System.out.println("Δεδομένα εισήχθησαν με επιτυχία! Τέλος main"); 




















       

    }

}
