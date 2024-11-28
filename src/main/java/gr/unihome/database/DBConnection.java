import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public static Connection connect(String dbUrl) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά τη σύνδεση με τη βάση δεδομένων: " + e.getMessage());
        }
        return conn;
    }

    public static void executeUpdate(String dbUrl, String sql) {
        try (Connection conn = connect(dbUrl)) {
                if (conn == null) {
                    System.out.println("Η σύνδεση απέτυχε. Δεν μπορεί να εκτελεστεί η εντολή SQL.");
                    return; // τερματισμος μεθοδου αν δεν υπαρχει συνδεση
                }
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(sql);
                    System.out.println("Η SQL εντολή εκτελέστηκε επιτυχώς.");
                }
        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά την εκτέλεση SQL: " + e.getMessage());
        }
    }
}
