package gr.unihome.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * This class manages a database for universities in Athens.
 * It includes the initialization of the database and the insertion of universities with their coordinates.
 */
public class UniversitiesDatabase {
    private static final String DB_UNI_URL = "jdbc:sqlite:universities.db";
    
    // List of university names in Athens
    private static List<String> universityNames = List.of(
        "Οικονομικό Πανεπιστήμιο Αθηνών", "Εθνικό και Καποδιστριακό Πανεπιστήμιο Αθηνών", "Εθνικό Μετσόβιο Πολυτεχνείο",
        "Πανεπιστήμιο Πειραιώς", "Πανεπιστήμιο Δυτικής Αττικής", "Γεωπονικό Πανεπιστήμιο Αθηνών",
        "Χαροκόπειο Πανεπιστήμιο", "Πάντειο Πανεπιστήμιο Κοινωνικών και Πολιτικών Επιστημών"
    );

    // Corresponding latitudes for each university
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

    // Corresponding longitudes for each university
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

    /**
     * Initializes the database by creating the "universities" table.
     * If the table already exists, it is dropped and recreated.
     */
    public static void initialize() {
        String dropTableSQL = "DROP TABLE IF EXISTS universities;";
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS universities (
                UniversityName VARCHAR(100) PRIMARY KEY,
                Latitude REAL,
                Longitude REAL
              );
              """;
      
        try (Connection conn = DBConnection.connect(DB_UNI_URL);  // Establish database connection
            PreparedStatement dropStmt = conn.prepareStatement(dropTableSQL);
            PreparedStatement createStmt = conn.prepareStatement(createTableSQL)) {
                dropStmt.executeUpdate();
                createStmt.executeUpdate();
                System.out.println("The 'universities' table was successfully created!");
            } catch (SQLException e) {
                System.err.println("Error while executing SQL for universities: " + e.getMessage());
            }
    }

    /**
     * Inserts a specific number of universities into the database.
     * The method uses predefined lists of university names, latitudes, and longitudes.
     * @param numberOfUniversities the number of universities to insert.
     */
    public static void insertRandomUniversities(int numberOfUniversities) {
        for (int i = 0; i < numberOfUniversities; i++) {
            // Retrieve the name, latitude, and longitude for each university
            String name = universityNames.get(i);
            double latitude = uniLatitudes.get(i);
            double longitude = uniLongitudes.get(i);

            // Insert the university data into the database
            insertUniversity(name, latitude, longitude);
        }
    }

    /**
     * Inserts a single university into the database.
     * @param name the name of the university.
     * @param latitude the latitude of the university's location.
     * @param longitude the longitude of the university's location.
     */
    public static void insertUniversity(String name, double latitude, double longitude) {
        String insertSQL = """
            INSERT INTO universities (UniversityName, Latitude, Longitude)
            VALUES (?, ?, ?);
        """; // SQL statement for inserting a university

        try (Connection conn = DBConnection.connect(DB_UNI_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            // Set the parameters for the SQL query
            pstmt.setString(1, name);
            pstmt.setDouble(2, latitude);
            pstmt.setDouble(3, longitude);
            
            // Execute the insertion
            pstmt.executeUpdate();
            
            System.out.println(name + " was successfully inserted!");
        } catch (SQLException e) {
            System.err.println("Error while executing SQL for universities: " + e.getMessage());
        }
    }
}
