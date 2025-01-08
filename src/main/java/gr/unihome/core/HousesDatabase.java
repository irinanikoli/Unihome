package gr.unihome.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class HousesDatabase {
    private static final String DB_HOUSES_URL = "jdbc:sqlite:data/houses.db";
    private static Random random1 = new Random();

        // List of locations in Athens
    private static List<String> athensLocations = List.of(
        "Κέντρο", "Παγκράτι", "Κολωνάκι", "Αμπελόκηποι", "Ζωγράφου", "Κηφισιά", 
        "Νέα Σμύρνη", "Πειραιάς", "Μαρούσι", "Γλυφάδα", "Περιστέρι", "Καλλιθέα", 
        "Ηλιούπολη", "Βύρωνας", "Χαλάνδρι", "Αιγάλεω", "Πετρούπολη", "Νέα Ιωνία", 
        "Νίκαια", "Μεταμόρφωση", "Άλιμος", "Αργυρούπολη", "Ηράκλειο", "Γαλάτσι", 
        "Πεντέλη", "Βριλήσσια", "Αγία Παρασκευή", "Χολαργός", "Παλαιό Φάληρο", 
        "Δάφνη", "Κερατσίνι", "Νέα Φιλαδέλφεια", "Μοσχάτο", "Ταύρος", "Πετράλωνα", 
        "Ψυρρή", "Θησείο", "Πλάκα", "Μακρυγιάννη", "Εξάρχεια", "Λυκαβηττός", 
        "Γκάζι", "Σεπόλια", "Κυψέλη", "Καμάτερο", "Ίλιον", "Αγία Βαρβάρα", 
        "Αχαρνές", "Καπανδρίτι", "Σπάτα", "Παιανία"
    );
            
        // List of addresses in Athens
    private static List<String> athensAddresses = List.of(
        "Ακαδημίας", "Σταδίου", "Ερμού", "Μιχαλακοπούλου", "Πατησίων", "Αλεξάνδρας", 
        "Σόλωνος", "Πανεπιστημίου", "Βασιλίσσης Σοφίας", "Κηφισίας", "Μεσογείων", 
        "Ιπποκράτους", "Αιόλου", "Αθηνάς", "Πειραιώς", "Αχιλλέως", "Καραϊσκάκη", 
        "Κατεχάκη", "Πεντέλης", "Μαρίνου Αντύπα", "Κηφισιάς", "Βουλιαγμένης", 
        "Αμαρουσίου Χαλανδρίου", "Χαριλάου Τρικούπη", "Ευριπίδου", "Λένορμαν", 
        "Αχιλλέως", "Πέτρου Ράλλη", "Αγίου Κωνσταντίνου", "Θησέως", "Αλίμου", 
        "Δημητσάνας", "Κουμουνδούρου", "Πραξιτέλους", "Σκουφά", "Συγγρού", 
        "Τζαβέλλα", "Τρικούπη", "Φειδίου", "Φιλελλήνων", "Ψαρών", "Αδριανού", 
        "Αιγίνης", "Βασιλέως Κωνσταντίνου", "Ελευθερίου Βενιζέλου", "Ηρώδου Αττικού", 
        "Καλλιρρόης", "Λυσικράτους", "Ομήρου", "Ριζάρη"
    );

    /**
     * Initializes the database by dropping and creating the "Houses" table.
     */
    public static void initialize() {
        String dropTableSQL = "DROP TABLE IF EXISTS Houses;";
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS Houses (
                Id INT PRIMARY KEY,
                LocationH VARCHAR(100),
                AddressH VARCHAR(100),
                CostH INT,
                FloorH INT,
                SizeH INT,
                DistanceFromUni REAL, 
                DistanceFromMeans REAL,
                NumberOfBed INT,
                Furnished INT,
                LatitudeH REAL,
                LongitudeH REAL
            );
        """;
        try (Connection conn = DBConnection.connect(DB_HOUSES_URL);
            PreparedStatement dropStmt = conn.prepareStatement(dropTableSQL);
            PreparedStatement createStmt = conn.prepareStatement(createTableSQL)) {
                dropStmt.executeUpdate();
                createStmt.executeUpdate();               
        } catch (SQLException e) {
            System.err.println("Error executing SQL on houses: " + e.getMessage());
        }
    }

    /**
     * Inserts a specified number of randomly generated houses into the database.
     *
     * @param numOfHouses the number of houses to insert
     */
    public static void insertRandomHouses(int numOfHouses) {
        for (int i = 0; i < numOfHouses; i++) {
            int id = i + 1;
            String location = athensLocations.get(random1.nextInt(athensLocations.size()));
            String address = athensAddresses.get(random1.nextInt(athensAddresses.size())) + " " + (random1.nextInt(200) + 1);
            int cost = random1.nextInt(1751) + 250;
            int floor = random1.nextInt(6);
            int size = random1.nextInt(121) + 30;
            double distanceFromUni = -1;
            double distanceFromMeans = -1;
            int numberOfBed = random1.nextInt(4) + 1;
            int furnished = random1.nextInt(2);  // yes or no
            double latitude = 37.8 + (random1.nextDouble() * (38.2 - 37.8));  // Latitude: 37.8 to 38.2
            double longitude = 23.5 + (random1.nextDouble() * (24.0 - 23.5)); // Longitude: 23.5 to 24.0

            insertHouse(id, location, address, cost, floor, size, distanceFromUni, distanceFromMeans,
                        numberOfBed, furnished, latitude, longitude);
        }
    }

    /**
     * Inserts a single house into the database.
     *
     * @param id              the ID of the house
     * @param location        the location of the house
     * @param address         the address of the house
     * @param cost            the cost of the house
     * @param floor           the floor of the house
     * @param size            the size of the house
     * @param distanceFromUni the distance from the university
     * @param distanceFromMeans the distance from public transport
     * @param numberOfBed     the number of beds in the house
     * @param furnished       whether the house is furnished (1 = yes, 0 = no)
     * @param latitude        the latitude of the house
     * @param longitude       the longitude of the house
     */    public static void insertHouse(
            int id, String location, String address, int cost, int floor, int size, double distanceFromUni, double distanceFromMeans, 
            int numberOfBed, int furnished, double latitude, double longitude) {
                String insertSQL = """
                    INSERT INTO Houses (
                        Id, LocationH, AddressH, CostH, FloorH, SizeH,
                        DistanceFromUni, DistanceFromMeans,
                        NumberOfBed, Furnished, LatitudeH, LongitudeH
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (Connection conn = DBConnection.connect(DB_HOUSES_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            // Set parameters
            pstmt.setInt(1, id);
            pstmt.setString(2, location);
            pstmt.setString(3, address);
            pstmt.setInt(4, cost);
            pstmt.setInt(5, floor);
            pstmt.setInt(6, size);
            pstmt.setDouble(7, distanceFromUni);
            pstmt.setDouble(8, distanceFromMeans);
            pstmt.setInt(9, numberOfBed);
            pstmt.setInt(10, furnished);
            pstmt.setDouble(11, latitude);
            pstmt.setDouble(12, longitude);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL on houses: " + e.getMessage());
        }
    }
}
