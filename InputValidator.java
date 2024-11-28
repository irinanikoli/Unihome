import java.util.Map;

public class InputValidator {
    public boolean validateBudget(double budget) {
        return budget>0;
    }

    public static boolean validatePreferences(Map<String,Integer> preferences) {
        if (preferences == null) {
            System.out.println("Οι προτιμήσεις είναι άκυρες");
            return false;
        }

        for (Map.Entry<String,Integer>entry:preferences.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (key == null || value == null) {
                System.out.println("Οι προτιμήσεις ή η σειρά κατάταξης τους είναι άκυρες");
                return false;
            }

            if (value<0) {
                System.out.println("Παρακαλώ εισάγετε θετική σειρά κατάταξης");
                return false;
            }

        }

        System.out.println("Όλες οι τιμές είναι έγκυρες");
            return true;
    }

}
