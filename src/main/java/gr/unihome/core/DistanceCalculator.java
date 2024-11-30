package gr.unihome.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DistanceCalculator {
    private static final Logger logger = AppLogger.getLogger();
    //Downloading Logger from AppLogger
    private DatabaseHandler dbHandler;

    // Κατασκευαστής που δέχεται ένα DatabaseHandler
    public DistanceCalculator() {
        // Αρχικοποιούμε το DatabaseHandler
        this.dbHandler = new DatabaseHandler();
    }

    /**
     * Υπολογίζει τις αποστάσεις από το πανεπιστήμιο και την πλησιέστερη στάση ΜΜΜ.
     * @param address - Η διεύθυνση του σπιτιού (θα πάρουμε τις συντεταγμένες από τη βάση δεδομένων)
     * @return Ένας χάρτης με αποστάσεις προς σημεία ενδιαφέροντος
     */
    // Για το πάνω το ψάχνω λιγο ακόμα πωσ θα το δουλέψω
    public Map<String, Double> calculateDistances(String address) {
        Map<String, Double> distances = new HashMap<>();
        
        try {
            // Παίρνουμε τις συντεταγμένες του σπιτιού από τη βάση δεδομένων με βάση τη διεύθυνσή του
            double[] houseCoordinates = dbHandler.getCoordinatesForAddress(address);
            double houseLatitude = houseCoordinates[0];
            double houseLongitude = houseCoordinates[1];
            
            // Παίρνουμε τις συντεταγμένες του πανεπιστημίου από τη βάση δεδομένων
            double[] uniCoordinates = dbHandler.getCoordinatesForPlace("university");
            double uniLatitude = uniCoordinates[0];
            double uniLongitude = uniCoordinates[1];
            
            // Παίρνουμε τις συντεταγμένες της πλησιέστερης στάσης ΜΜΜ από τη βάση δεδομένων
            double[] meansCoordinates = dbHandler.getCoordinatesForPlace("nearest_transport");
            double meansLatitude = meansCoordinates[0];
            double meansLongitude = meansCoordinates[1];
            
            // Υπολογίζουμε την απόσταση χρησιμοποιώντας τη μέθοδο haversine
            double distanceToUniversity = calculateHaversineDistance(houseLatitude, houseLongitude, uniLatitude, uniLongitude);
            double distanceToTransport = calculateHaversineDistance(houseLatitude, houseLongitude, meansLatitude, meansLongitude);
            
            // Αποθηκεύουμε τις αποστάσεις στον χάρτη
            distances.put("uni", distanceToUniversity);
            distances.put("means", distanceToTransport);
            
        } catch (Exception e) {
            logger.severe("Error during the calculation of distances : " + e.getMessage());
            System.out.println("Σφάλμα κατά τον υπολογισμό αποστάσεων: " + e.getMessage());
        }
        
        return distances;
    }

    
    //Υπολογίζει την απόσταση ανάμεσα σε δύο σημεία βάσει συντεταγμένων (latitude, longitude).
    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Ακτίνα της γης σε χιλιόμετρα
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c; // Επιστρέφει την απόσταση σε χιλιόμετρα
    }
}