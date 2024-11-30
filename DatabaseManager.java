import java.sql.*;

public class DatabaseManager {

    // Σύνδεση στη βάση δεδομένων για τα σπίτια
    private static final String HOUSES_DB_URL = "jdbc:sqlite:houses.db";
    
    // Σύνδεση στη βάση δεδομένων για τα πανεπιστήμια
    private static final String UNIVERSITIES_DB_URL = "jdbc:sqlite:universities.db";
    
    // Σύνδεση στη βάση δεδομένων για τις στάσεις ΜΜΜ
    private static final String MEANS_DB_URL = "jdbc:sqlite:means.db";
    
    public DatabaseManager() {
    }

    /**
     * Ανάκτηση συντεταγμένων για το σπίτι με βάση τη διεύθυνση
     * @param address Η διεύθυνση του σπιτιού
     * @return Πίνακας με latitude και longitude
     */
    public double[] getCoordinatesForHouse(String address) throws SQLException {
        String query = "SELECT Latitude, Longitude FROM houses WHERE Address = ?";
        try (Connection conn = DriverManager.getConnection(HOUSES_DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, address);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new double[]{rs.getDouble("Latitude"), rs.getDouble("Longitude")};
                } else {
                    throw new SQLException("House not found.");
                }
            }
        }
    }

    /**
     * Ανάκτηση συντεταγμένων για ένα πανεπιστήμιο με βάση το όνομα
     * @param universityName Το όνομα του πανεπιστημίου
     * @return Πίνακας με latitude και longitude
     */
    public double[] getCoordinatesForUniversity(String universityName) throws SQLException {
        String query = "SELECT Latitude, Longitude FROM universities WHERE UniversityName = ?";
        try (Connection conn = DriverManager.getConnection(UNIVERSITIES_DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, universityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new double[]{rs.getDouble("Latitude"), rs.getDouble("Longitude")};
                } else {
                    throw new SQLException("University not found.");
                }
            }
        }
    }

    /**
     * Ανάκτηση συντεταγμένων για την πλησιέστερη στάση ΜΜΜ με βάση τις συντεταγμένες του σπιτιού
     * @param houseLatitude Το latitude του σπιτιού
     * @param houseLongitude Το longitude του σπιτιού
     * @return Πίνακας με latitude και longitude
     */
    public double[] getCoordinatesForNearestTransport(double houseLatitude, double houseLongitude) throws SQLException {
        String query = "SELECT Latitude, Longitude FROM means ORDER BY (Latitude - ?)^2 + (Longitude - ?)^2 LIMIT 1";
        try (Connection conn = DriverManager.getConnection(MEANS_DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, houseLatitude);
            stmt.setDouble(2, houseLongitude);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new double[]{rs.getDouble("Latitude"), rs.getDouble("Longitude")};
                } else {
                    throw new SQLException("Transport stop not found.");
                }
            }
        }
    }

}
