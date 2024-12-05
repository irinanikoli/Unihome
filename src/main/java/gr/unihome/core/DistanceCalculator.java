import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DistanceCalculator {
    private static final Logger logger = AppLogger.getLogger();
    
    // url για την σύνδεση με τα αρχεία των βάσεων    
    private static final String DB_HOUSES_URL = "jdbc:sqlite:houses.db";
    private static final String DB_UNI_URL = "jdbc:sqlite:universities.db";
    private static final String DB_MEANS_URL = "jdbc:sqlite:means.db";
    

    
    //Υπολογίζει την απόσταση ανάμεσα σε δύο σημεία βάσει συντεταγμένων (latitude, longitude).
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
            final int R = 6371; // Ακτίνα της γης σε χιλιόμετρα
            
            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                     + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                     * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            
            return R * c; // Επιστρέφει την απόσταση σε χιλιόμετρα
        }
    
            /**
         * @param universityName το πανεπιστήμιο που έχει εισάγει ο χρήστης
         * γινεται σύνδεση με τις βάσεις, παίρνουμε τις συντεταγμενες του πανεπιστημιου και των σπιτιών
         * και για κάθε σπίτι καλεί την calculateDistance για να υπολογισει την μεταξύ τους απόσταση
         * και την εισάγουμε μετά στο table houses στην γραμμή DistanceFromUni
         */
        public static void calculateDistancesBetweenHousesAndUni(String universityName) {
            /** παίρνουμε από τις βάσεις το id και τις συντεταγμένες όλων των σπιτιών
             * και τις συντεταγμένες του πανεπιστημίου που επέλεξε ο χρήστης
             */
            String houseQuery = "SELECT Id, LatitudeH, LongitudeH FROM Houses;";
            String uniQuery = "SELECT UniversityName, Latitude, Longitude FROM universities WHERE UniversityName = ?;";
    
            try (Connection houseConn = DriverManager.getConnection(DB_HOUSES_URL);
                 Connection uniConn = DriverManager.getConnection(DB_UNI_URL);
                 PreparedStatement houseStmt = houseConn.prepareStatement(houseQuery);
                 PreparedStatement uniStmt = uniConn.prepareStatement(uniQuery)) {
    
                // ρυθμιση του preparedstmt για το πανεπιστήμιο
                 uniStmt.setString(1, universityName);
                 try (ResultSet uniRs = uniStmt.executeQuery()) {
                    // συντεταγμενες του συγκεκριμενου πανεπιστημιου
                    double uniLat = uniRs.getDouble("Latitude");
                    double uniLon = uniRs.getDouble("Longitude");
    
                    try (ResultSet houseRs = houseStmt.executeQuery()) {
                        // παίρνουμε απο το table houses για κάθε σπίτι το id και τις συντεταγμένες του
                        while (houseRs.next()) {
                            int houseId = houseRs.getInt("Id");
                            double houseLat = houseRs.getDouble("LatitudeH");
                            double houseLon = houseRs.getDouble("LongitudeH");
                            
                            double distance = calculateDistance(houseLat, houseLon, uniLat, uniLon);
                        updateUniCoordinates(houseConn, houseId, distance);
                        System.out.printf("Η απόσταση του σπιτιού " + houseId + " από το πανεπιστήμιο " + universityName + " είναι %.2f km.%n", distance);
                    }
                }       
             }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την υπολογισμό αποστάσεων: " + e.getMessage());
        }
    }

    /**
     * ανοίγουμε σύνδεση με τις βάσεις και
     * υπολογίζουμε την απόσταση μεταξύ κάθε σπιτιού και κάθε στασης
     * στην συνέχεια καλούμε μέθοδο για τον υπολογισμο της πιο κοντινής στάσης για κάθε σπίτι
     * και την εισάγουμε στο table houses στην γραμμή DistanceFromMeans
     */
    public static void calculateDistancesBetweenHousesAndMeans() {
        /** παίρνουμε από τις βάσεις το id και τις συντεταγμένες όλων των σπιτιών
         * και το code και τις συντεταγμένες όλων των στάσεων
         */
        String selectHousesSQL = "SELECT Id, LatitudeH, LongitudeH FROM houses;";
        String selectMeansSQL = "SELECT CodeMeans, Latitude, Longitude FROM means;";
        
        try (Connection meansConn = DriverManager.getConnection(DB_MEANS_URL);
             Connection houseConn = DriverManager.getConnection(DB_HOUSES_URL);
             PreparedStatement meansStmt = meansConn.prepareStatement(selectMeansSQL)) {
             PreparedStatement houseStmt = houseConn.prepareStatement(selectHousesSQL);
            
            try (ResultSet houseResultSet = houseStmt.executeQuery()) {
                // συντεταγμενες του συγκεκριμενου πανεπιστημιου
                while (houseResultSet.next()) {
                    int houseId = houseResultSet.getInt("Id");
                    double houseLat = houseResultSet.getDouble("LatitudeH");
                    double houseLon = houseResultSet.getDouble("LongitudeH");
                    
                    double minDistance = Double.MAX_VALUE;
                    int closestMeansId = -1;

                    try (ResultSet meansResultSet = meansStmt.executeQuery()) {
                        while (meansResultSet.next()) {
                            int meansId = meansResultSet.getInt("CodeMeans");
                            double meansLat = meansResultSet.getDouble("Latitude");
                            double meansLon = meansResultSet.getDouble("Longitude");
                            
                            double distance = calculateDistance(meansLat, meansLon, houseLat, houseLon);
                            System.out.printf("Απόσταση από το σπίτι " + houseId + " στη στάση " + meansId + ": %.2f km%n", distance);

                            if (distance < minDistance || (distance == minDistance && meansId < closestMeansId)) {
                                minDistance = distance;
                                closestMeansId = meansId;
                            }
                        }
                    }
                    updateMeansCoordinates(houseConn, houseId, minDistance);
                }
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εκτέλεση υπολογισμού αποστάσεων: " + e.getMessage());
        }   
    }

}