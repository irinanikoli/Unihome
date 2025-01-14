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


package gr.unihome.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class Application {

    public static final String RESET = "\u001B[0m";
	public static final String GREEN = "\u001B[32m";
	public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        // Initialize databases
        HousesDatabase.initialize();
        UniversitiesDatabase.initialize();
        MeansDatabase.initialize();
        HousesDatabase.insertRandomHouses(100);
        UniversitiesDatabase.insertRandomUniversities(8);
        MeansDatabase.insertRandomMeans(5);

        // Show the list of universities for the user to select one
        List<String> universityNames = UniversitiesDatabase.getUniversitiesFromDB();
        if (universityNames.isEmpty()) {
            System.out.println(RED + "Η λίστα των πανεπιστημίων είναι κενή. Ελέγξτε τη βάση δεδομένων." + RESET);
            return;
        }

        System.out.println(YELLOW + "Επιλέξτε Πανεπιστήμιο από τη λίστα:" + RESET);
        for (int i = 0; i < universityNames.size(); i++) {
            System.out.println((i + 1) + ". " + universityNames.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        String selectedUniversity = null;

        // Validate the user's university selection
        while (selectedUniversity == null) {
            System.out.print(CYAN + "\nΕισάγετε τον αριθμό που αντιστοιχεί στο πανεπιστήμιο: " + RESET);
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= universityNames.size()) {
                    selectedUniversity = universityNames.get(choice - 1);
                } else {
                    System.out.println(RED + "Ο αριθμός πρέπει να είναι μεταξύ 1 και " + universityNames.size() + "." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Μη έγκυρη είσοδος. Πληκτρολογήστε έναν αριθμό." + RESET);
            }
        }

        System.out.println("Επιλέξατε το Πανεπιστήμιο: " + GREEN + selectedUniversity + RESET);

        // Calculate distances
        DistanceCalculator.calculateDistancesBetweenHousesAndUni(selectedUniversity);
        DistanceCalculator.calculateDistancesBetweenHousesAndMeans();

        List<HousingOption> housingOptions = HousingOption.fetchHousingOptionsFromDB();

        // Enter priorities
        List<String> criteria = List.of("budget", "distanceFromUniversity", "size", "distanceFromMeans");
        List<Integer> priorities = getUserPriorities(scanner, criteria);

        double threshold = 3;

        // Collect user-specific criteria for house recommendations
        Criteria studentCriteria = getUserCriteria(scanner, selectedUniversity);

        String name = studentCriteria.getStName();

        RecommendationService recommendationService = new RecommendationService(housingOptions, criteria, priorities);

        HousingOption bestHouse = recommendationService.getBestHouse(studentCriteria);

        // Generate a list of recommended houses within the threshold
        List<HousingOption> recommendations = recommendationService.getBestRecommendationList(studentCriteria, threshold, bestHouse);

        if (bestHouse != null) {
            System.out.println("\n" + name + ", το καλύτερο σπίτι για εσάς είναι: \n" + GREEN + bestHouse.toString() + RESET);
        } else {
            System.out.println(RED + "\nΔεν βρέθηκε κατάλληλο σπίτι." + RESET);
        }

        if (!recommendations.isEmpty()) {
            System.out.println("\nΠροτεινόμενα σπίτια:");
            for (HousingOption option : recommendations) {
                System.out.println(option);
            }
        } else {
            System.out.println(RED + "\nΔεν βρέθηκαν προτάσεις." + RESET);
        }
    }

    /**
     * Method to collect and validate user-defined priorities for house selection criteria.
     * 
     * @param scanner  The Scanner object for user input.
     * @param criteria The list of criteria to prioritize.
     * @return A list of user-defined priorities.
     */
    private static List<Integer> getUserPriorities(Scanner scanner, List<String> criteria) {
        System.out.println(YELLOW + "\nΚαθορίστε τις προτεραιότητες σας για τα παρακάτω κριτήρια (1-4, χωρίς επαναλήψεις):" + RESET);
        for (int i = 0; i < criteria.size(); i++) {
            System.out.println((i + 1) + ". " + criteria.get(i));
        }

        List<Integer> priorities = new ArrayList<>();
        while (priorities.size() < criteria.size()) {
            String currentCriterion = criteria.get(priorities.size());
            System.out.print(CYAN + "Προτεραιότητα για " + currentCriterion + " : " + RESET);
            String input = scanner.nextLine().trim();

            try {
                int priority = Integer.parseInt(input);
                if (priority >= 1 && priority <= 4 && !priorities.contains(priority)) {
                    priorities.add(priority);
                } else {
                    System.out.println(RED + "Η προτεραιότητα πρέπει να είναι μοναδικός αριθμός από 1 έως 4." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Μη έγκυρη είσοδος. Πληκτρολογήστε έναν αριθμό." + RESET);
            }
        }
        return priorities;
    }

    /**
     * Method to collect and validate user-specific criteria for house recommendations.
     * 
     * @param scanner         The scanner object for user input.
     * @param universityName  The selected university name.
     * @return A criteria object with user's values.
     */
    private static Criteria getUserCriteria(Scanner scanner, String universityName) {
        System.out.println(CYAN + "\nΕισάγετε τα κριτήριά σας:" + RESET);

        String name = getValidStringInput(scanner, "Παρακαλώ εισάγετε το όνομά σας (μόνο λατινικοί χαρακτήρες): ", "[a-zA-Z ]+");

        int budget = getValidIntInput(scanner, 250, 2500, "Το μέγιστο κόστος (ευρώ) πρέπει να είναι μεταξύ 250 και 2500: ");
        int size = getValidIntInput(scanner, 15, 150, "Τα ελάχιστα τετραγωνικά μέτρα πρέπει να είναι μεταξύ 15 και 150: ");
        int maxDistanceFromUniversity = getValidIntInput(scanner, 30, 150000, "Η μέγιστη απόσταση από το Πανεπιστήμιο (μέτρα) πρέπει να είναι μεταξύ 30 και 150000: ");
        int maxDistanceFromMeans = getValidIntInput(scanner, 30, 15000, "Η μέγιστη απόσταση από ΜΜΜ (μέτρα) πρέπει να είναι μεταξύ 30 και 15000: ");

        return new Criteria(name, universityName, budget, size, maxDistanceFromUniversity, maxDistanceFromMeans);
    }

    /**
     * Method to collect and validate user input as a string based on a regular expression.
     * 
     * @param scanner      The scanner object for user input.
     * @param prompt       The message displayed to the user.
     * @param regexPattern The regular expression for input validation.
     * @return A valid string input.
     */
    private static String getValidStringInput(Scanner scanner, String prompt, String regexPattern) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                if (input.matches(regexPattern)) {
                    return input;
                } else {
                    System.out.println(RED + "Το όνομα πρέπει να γραφεί με λατινικούς χαρακτήρες μόνο." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Μη έγκυρη είσοδος. Πληκτρολογήστε έναν αριθμό." + RESET);
            }
        }
    }

    /**
     * Method to collect and validate user input as an integer within a specified range.
     * 
     * @param scanner The scanner object for user input.
     * @param min     The minimum valid value.
     * @param max     The maximum valid value.
     * @param prompt  The message displayed to the user.
     * @return A valid integer input.
     */
    private static int getValidIntInput(Scanner scanner, int min, int max, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println(RED + "Η τιμή πρέπει να είναι μεταξύ " + min + " και " + max + "." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Μη έγκυρη είσοδος. Πληκτρολογήστε έναν αριθμό." + RESET);
            }
        }
    }
}
