import java.util.List;
import java.util.Random;

public class UniversitiesDatabase {
    private static final String DB_URL = "jdbc:sqlite:universities.db";
    private static Random random1 = new Random();

    private static List<String> universityNames = List.of(
        "Οικονομικό Πανεπιστήμιο Αθηνών", "Εθνικό και Καποδιστριακό Πανεπιστήμιο Αθηνών", "Εθνικό Μετσόβιο Πολυτεχνείο",
        "Πανεπιστήμιο Πειραιώς", "Πανεπιστήμιο Δυτικής Αττικής", "Γεωπονικό Πανεπιστήμιο Αθηνών"
);
    public static void initialize() {
        String dropTableSQL = "DROP TABLE IF EXISTS universities;"; // σαμε
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS universities (
                UniversityName VARCHAR(100) PRIMARY KEY,
                Latitude INT,
                Longitude INT
              );
              """;
        
        DBConnection.executeUpdate(DB_URL, dropTableSQL);  // διαγραφή παλιού πίνακα
        DBConnection.executeUpdate(DB_URL, createTableSQL);
    }

    public static void insertRandomUniversities(int numberOfUniversities) {
        for (int i = 0; i < numberOfUniversities; i++) {
            String name = universityNames.get(i);
            int latitude = 36 + random1.nextInt(15);  // πλάτος
            int longitude = 22 + random1.nextInt(10);  // μήκος

            //ΕΛΕΥΘΕΡΙΑ ΑΥΤΑ ΣΤΑ ΣΧΟΛΙΑ ΧΡΗΣΙΜΟΠΟΙΗΣΕ ΚΑΙ ΘΑ ΤΟ ΔΙΟΡΘΩΣΩ ΣΤΟΝ ΚΩΔΙΚΑ ΜΟΥ
            // Περιορισμός γεωγραφικών συντεταγμένων στην Αττική
            //double latitude = 37.8 + (random1.nextDouble() * (38.2 - 37.8));  // Πλάτος: 37.8 έως 38.2
            //double longitude = 23.5 + (random1.nextDouble() * (24.0 - 23.5));  // Μήκος: 23.5 έως 24.0

            insertUniversity(name, latitude, longitude);
        }
    }

    public static void insertUniversity(String name, int latitudeH, int longitudeH) {
        String insertSQL = String.format("""
            INSERT INTO universities (UniversityName, Latitude, Longitude)
            VALUES ('%s', '%d', '%d');
        """, name, latitudeH, longitudeH);
        DBConnection.executeUpdate(DB_URL, insertSQL);
        System.out.println("Πανεπιστήμιο " + name + " εισήχθη με επιτυχία!");

    }
}
