package gr.unihome.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // Initialize databases
        HousesDatabase.initialize();
        UniversitiesDatabase.initialize();
        MeansDatabase.initialize();
        HousesDatabase.insertRandomHouses(100);
        UniversitiesDatabase.insertRandomUniversities(8);
        MeansDatabase.insertRandomMeans(5);

        // Show the list of universities for the user to choose
        List<String> universityNames = UniversitiesDatabase.getUniversitiesFromDB();
        if (universityNames.isEmpty()) {
            System.out.println("Η λίστα των πανεπιστημίων είναι κενή. Ελέγξτε τη βάση δεδομένων.");
            return;
        }

        System.out.println("Επιλέξτε Πανεπιστήμιο από τη λίστα:");
        for (int i = 0; i < universityNames.size(); i++) {
            System.out.println((i + 1) + ". " + universityNames.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        String selectedUniversity = null;

        while (selectedUniversity == null) {
            System.out.print("\nΕισάγετε τον αριθμό που αντιστοιχεί στο πανεπιστήμιο: ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= universityNames.size()) {
                    selectedUniversity = universityNames.get(choice - 1);
                } else {
                    System.out.println("Ο αριθμός πρέπει να είναι μεταξύ 1 και " + universityNames.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη είσοδος. Πληκτρολογήστε έναν αριθμό.");
            }
        }

        System.out.println("Επιλέξατε το Πανεπιστήμιο: " + selectedUniversity);

        // Calculate distances
        DistanceCalculator.calculateDistancesBetweenHousesAndUni(selectedUniversity);
        DistanceCalculator.calculateDistancesBetweenHousesAndMeans();

        List<HousingOption> housingOptions = HousingOption.fetchHousingOptionsFromDB();

        // Enter priorities
        List<String> criteria = List.of("budget", "distanceFromUniversity", "size", "distanceFromMeans");
        List<Integer> priorities = getUserPriorities(scanner, criteria);

        double threshold = 2;

        // Enter data of the user
        Criteria studentCriteria = getUserCriteria(scanner, selectedUniversity);

        RecommendationService recommendationService = new RecommendationService(housingOptions, criteria, priorities);

        HousingOption bestHouse = recommendationService.getBestHouse(studentCriteria);

        List<HousingOption> recommendations = recommendationService.getBestRecommendationList(studentCriteria, threshold, bestHouse);

        if (bestHouse != null) {
            System.out.println("\nΤο καλύτερο σπίτι είναι: " + bestHouse.toString());
        } else {
            System.out.println("\nΔεν βρέθηκε κατάλληλο σπίτι.");
        }

        if (!recommendations.isEmpty()) {
            System.out.println("\nΠροτεινόμενα σπίτια:");
            for (HousingOption option : recommendations) {
                System.out.println(option);
            }
        } else {
            System.out.println("\nΔεν βρέθηκαν προτάσεις.");
        }
    }

    // Method to get user priorities
    private static List<Integer> getUserPriorities(Scanner scanner, List<String> criteria) {
        System.out.println("\nΚαθορίστε τις προτεραιότητες σας για τα παρακάτω κριτήρια (1-4, χωρίς επαναλήψεις):");
        for (int i = 0; i < criteria.size(); i++) {
            System.out.println((i + 1) + ". " + criteria.get(i));
        }

        List<Integer> priorities = new ArrayList<>();
        while (priorities.size() < criteria.size()) {
            String currentCriterion = criteria.get(priorities.size());
            System.out.print("Προτεραιότητα για " + currentCriterion + " : ");
            String input = scanner.nextLine().trim();

            try {
                int priority = Integer.parseInt(input);
                if (priority >= 1 && priority <= 4 && !priorities.contains(priority)) {
                    priorities.add(priority);
                } else {
                    System.out.println("Η προτεραιότητα πρέπει να είναι μοναδικός αριθμός από 1 έως 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη είσοδος. Πληκτρολογήστε έναν αριθμό.");
            }
        }
        return priorities;
    }

    // Method to get user criteria
    private static Criteria getUserCriteria(Scanner scanner, String universityName) {
        System.out.println("\nΕισάγετε τα κριτήριά σας:");

        System.out.print("Όνομα φοιτητή: ");
        String name = scanner.nextLine().trim();

        int budget = getValidIntInput(scanner, 250, 2500, "Μέγιστο κόστος (€) πρέπει να είναι μεταξύ 250 και 2500: ");
        int size = getValidIntInput(scanner, 15, 150, "Ελάχιστα τετραγωνικά μέτρα πρέπει να είναι μεταξύ 15 και 150: ");
        int maxDistanceFromUniversity = getValidIntInput(scanner, 30, 150000, "Μέγιστη απόσταση από το Πανεπιστήμιο (μέτρα) πρέπει να είναι μεταξύ 30 και 150000: ");
        int maxDistanceFromMeans = getValidIntInput(scanner, 30, 15000, "Μέγιστη απόσταση από ΜΜΜ (μέτρα) πρέπει να είναι μεταξύ 30 και 15000: ");

        return new Criteria(name, universityName, budget, size, maxDistanceFromUniversity, maxDistanceFromMeans);
    }

    // Validate input
    private static int getValidIntInput(Scanner scanner, int min, int max, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Η τιμή πρέπει να είναι μεταξύ " + min + " και " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη είσοδος. Πληκτρολογήστε έναν αριθμό.");
            }
        }
    }
}
