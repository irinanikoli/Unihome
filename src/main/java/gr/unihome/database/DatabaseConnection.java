import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:houses.db";
    //private static final String URL = "C:/Users/tadam/OneDrive/Έγγραφα/σχολή/3ο εξαμηνο/java εργασια/HOUSEdb/HOUSEdb/houses.db";
    //αυτο ειναι το path για το αρχειο της βασης στον ΔΙΚΟ ΜΟΥ ΥΠΟΛΟΓΙΣΤΗ ΜΟΝΟ
    public static Connection connect() {
        Connection conn = null;
        try {
            
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            System.out.println("Επιτυχής σύνδεση με την βαση δεδομενων");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load SQLite JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Σφάλμα στην σύνδεση" + e.getMessage());
        }
        return conn;
    }
}
