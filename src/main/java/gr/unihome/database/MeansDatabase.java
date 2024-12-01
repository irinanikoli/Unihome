import java.util.List;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MeansDatabase {
    private static final String DB_URL = "jdbc:sqlite:means.db";
    private static Random random1 = new Random();

    private static List<String> nameMeans = List.of(
        "Στάση Λεωφορείου", "Στάση Μετρό γραμμή 1", "Στάση Μετρό γραμμή 2", "Στάση Μετρό γραμμή 3", "Στάση Προαστιακού", "Στάση Τραμ"
    );

    public static void initialize() {
        String dropTableSQL = "DROP TABLE IF EXISTS means;"; // διαγραφή παλιού table
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS means (
                CodeMeans INT PRIMARY KEY,
                MeansName VARCHAR(100),
                Latitude REAL,
                Longitude REAL
              );
              """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement dropStmt = conn.prepareStatement(dropTableSQL);
            PreparedStatement createStmt = conn.prepareStatement(createTableSQL)) {
                dropStmt.executeUpdate();
                createStmt.executeUpdate();
                //διαγραφή παλιού table αν υπηρχε και δημιουργία νεου
                System.out.println("Ο πίνακας means δημιουργήθηκε με επιτυχία!");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εκτέλεση SQL στο means: " + e.getMessage());
        }
    }

    public static void insertRandomMeans(int numberOfMeans) {
        for (int i = 0; i < numberOfMeans; i++) {
            int codeMeans = i + 1;
            String name = nameMeans.get(random1.nextInt(nameMeans.size()));
            double latitude = 37.8 + (random1.nextDouble() * (38.2 - 37.8));  // Πλάτος: 37.8 έως 38.2
            double longitude = 23.5 + (random1.nextDouble() * (24.0 - 23.5));  // Μήκος: 23.5 έως 24.0
            // Περιορισμός γεωγραφικών συντεταγμένων στην Αττική
            
            insertMeans(codeMeans, name, latitude, longitude);
        }
    }
    //για την εισαγωγή δεδομένων κάθε στάσης
    public static void insertMeans(int codeMeans, String name, double latitude, double longitude) {
        String insertSQL = """
            INSERT INTO means (CodeMeans, MeansName, Latitude, Longitude)
            VALUES (?, ?, ?, ?);
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            //ορισμος παραμετρων
            pstmt.setInt(1, codeMeans);
            pstmt.setString(2, name);
            pstmt.setDouble(3, latitude);
            pstmt.setDouble(4, longitude);
            
            pstmt.executeUpdate();
            
            System.out.println("Στάση " + codeMeans + " εισήχθη με επιτυχία!");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εκτέλεση SQL στο means: " + e.getMessage());
        }
    }
}
