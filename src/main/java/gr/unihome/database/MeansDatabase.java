import java.util.List;
import java.util.Random;

public class MeansDatabase {
    private static final String DB_URL = "jdbc:sqlite:means.db";
    private static Random random1 = new Random();

    private static List<String> nameMeans = List.of(
        "Στάση Λεωφορείου", "Στάση Μετρό γραμμή 1", "Στάση Μετρό γραμμή 2", "Στάση Μετρό γραμμή 3", "Στάση Προαστιακού", "Στάση Τραμ"
    );

    public static void initialize() {
        String dropTableSQL = "DROP TABLE IF EXISTS means;"; // σαμε
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS means (
                CodeMeans INT PRIMARY KEY,
                MeansName VARCHAR(100),
                Latitude INT,
                Longitude INT
              );
              """;
        
        DBConnection.executeUpdate(DB_URL, dropTableSQL);  // διαγραφή παλιού πίνακα
        DBConnection.executeUpdate(DB_URL, createTableSQL);
    }

    public static void insertRandomMeans(int numberOfMeans) {
        for (int i = 0; i < numberOfMeans; i++) {
            int CodeMeans = i + 1;
            String name = nameMeans.get(random1.nextInt(nameMeans.size()));
            int latitude = 36 + random1.nextInt(15);  // πλάτος
            int longitude = 22 + random1.nextInt(10);  // μήκος

            //ΕΛΕΥΘΕΡΙΑ ΑΥΤΑ ΣΤΑ ΣΧΟΛΙΑ ΧΡΗΣΙΜΟΠΟΙΗΣΕ ΚΑΙ ΘΑ ΤΟ ΔΙΟΡΘΩΣΩ ΣΤΟΝ ΚΩΔΙΚΑ ΜΟΥ
            // Περιορισμός γεωγραφικών συντεταγμένων στην Αττική
            //double latitude = 37.8 + (random1.nextDouble() * (38.2 - 37.8));  // Πλάτος: 37.8 έως 38.2
            //double longitude = 23.5 + (random1.nextDouble() * (24.0 - 23.5));  // Μήκος: 23.5 έως 24.0

            insertMeans(CodeMeans, name, latitude, longitude);
        }
    }

    public static void insertMeans(int CodeMeans, String name, int latitude, int longitude) {
        String insertSQL = String.format("""
            INSERT INTO means (CodeMeans, MeansName, Latitude, Longitude)
            VALUES (%d, '%s', %d, %d);
        """,CodeMeans, name, latitude, longitude);
        DBConnection.executeUpdate(DB_URL, insertSQL);
        System.out.println("Στάση " + CodeMeans + " εισήχθη με επιτυχία!");

    }
}
