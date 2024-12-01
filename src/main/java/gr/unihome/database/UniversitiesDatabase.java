import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UniversitiesDatabase {
    private static final String DB_URL = "jdbc:sqlite:universities.db";

    private static List<String> universityNames = List.of(
        "Οικονομικό Πανεπιστήμιο Αθηνών", "Εθνικό και Καποδιστριακό Πανεπιστήμιο Αθηνών", "Εθνικό Μετσόβιο Πολυτεχνείο",
        "Πανεπιστήμιο Πειραιώς", "Πανεπιστήμιο Δυτικής Αττικής", "Γεωπονικό Πανεπιστήμιο Αθηνών",
        "Χαροκόπειο Πανεπιστήμιο", "Πάντειο Πανεπιστήμιο Κοινωνικών και Πολιτικών Επιστημών"
    );
    private static List<Double> uniLatitudes = List.of(
        37.994072, // opa
        37.980752, // ekpa
        37.977753, // emp
        37.941605, // papei
        38.002515, // dapa
        37.983425, // geoponiko
        37.961236, // xarokopeio
        37.959529  // panteio
    );
    private static List<Double> uniLongitudes = List.of(
        23.732115, // opa
        23.733766, // ekpa
        23.783781, // emp
        23.652987, // papei
        23.674893, // pada
        23.703813, // geoponiko
        23.708280, // xarokopeio
        23.719392  // panteio
    );

    public static void initialize() {
        String dropTableSQL = "DROP TABLE IF EXISTS universities;";
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS universities (
                UniversityName VARCHAR(100) PRIMARY KEY,
                Latitude REAL,
                Longitude REAL
              );
              """;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement dropStmt = conn.prepareStatement(dropTableSQL);
            PreparedStatement createStmt = conn.prepareStatement(createTableSQL)) {
                dropStmt.executeUpdate();
                createStmt.executeUpdate();
                // διαγραφή παλιού πίνακα και δημιουργία νέου
                System.out.println("Ο πίνακας uni δημιουργήθηκε με επιτυχία!");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εκτέλεση SQL στο uni: " + e.getMessage());
        }
    }

    public static void insertRandomUniversities(int numberOfUniversities) {
        for (int i = 0; i < numberOfUniversities; i++) {
            String name = universityNames.get(i);
            double latitude = uniLatitudes.get(i);
            double longitude = uniLongitudes.get(i);

            insertUniversity(name, latitude, longitude);
        }
    }
    
    public static void insertUniversity(String name, double latitude, double longitude) {
        String insertSQL ="""
            INSERT INTO universities (UniversityName, Latitude, Longitude)
            VALUES (?, ?, ?);
        """;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            //ορισμος παραμετρων
            pstmt.setString(1, name);
            pstmt.setDouble(2, latitude);
            pstmt.setDouble(3, longitude);
            
            pstmt.executeUpdate();
            //σύνδεση με την βάση και εισαγωγή των δεδομένων
            System.out.println(name + " εισήχθη με επιτυχία!");
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εκτέλεση SQL στο uni: " + e.getMessage());
        }
    }
}
