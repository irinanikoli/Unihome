//import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class HousesDatabase {
    private static final String DB_URL = "jdbc:sqlite:houses.db";
    private static Random random1 = new Random();

    private static List<String> athensLocations = List.of(
        "Κέντρο", "Παγκράτι", "Κολωνάκι", "Αμπελόκηποι", "Ζωγράφου", "Κηφισιά", 
        "Νέα Σμύρνη", "Πειραιάς", "Μαρούσι", "Γλυφάδα", "Περιστέρι", "Καλλιθέα", 
        "Ηλιούπολη", "Βύρωνας", "Χαλάνδρι", "Αιγάλεω", "Πετρούπολη", "Νέα Ιωνία", 
        "Νίκαια", "Μεταμόρφωση", "Άλιμος", "Αργυρούπολη", "Ηράκλειο", "Γαλάτσι", 
        "Πεντέλη", "Βριλήσσια", "Αγία Παρασκευή", "Χολαργός", "Παλαιό Φάληρο", 
        "Δάφνη", "Κερατσίνι", "Νέα Φιλαδέλφεια", "Μοσχάτο", "Ταύρος", "Πετράλωνα", 
        "Ψυρρή", "Θησείο", "Πλάκα", "Μακρυγιάννη", "Εξάρχεια", "Λυκαβηττός", 
        "Γκάζι", "Σεπόλια", "Κυψέλη", "Καμάτερο", "Ζεφύρι", "Αγία Βαρβάρα", 
        "Αχαρνές", "Καπανδρίτι", "Σπάτα", "Παιανία"
    );
            
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

    public static void initialize() {
        String dropTableSQL = "DROP TABLE IF EXISTS Houses;"; // σαμε  CHECK (Furnished IN (0, 1)),
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS Houses (
                Id INT PRIMARY KEY,
                LocationH VARCHAR(100),
                AddressH VARCHAR(100),
                CostH INT,
                FloorH INT,
                SizeH INT,
                DistanceFromUni INT,
                DistanceFromMeans INT,
                NumberOfBed INT,
                Furnished INT,
                LatitudeH INT,
                LongitudeH INT
            );
        """;

        DBConnection.executeUpdate(DB_URL, dropTableSQL);  // διαγραφή παλιού πίνακα
        DBConnection.executeUpdate(DB_URL, createTableSQL);
    }

    public static void insertRandomHouses(int numOfHouses) {
        for (int i = 0; i < numOfHouses; i++) {
            int id = i + 1;
            String location = athensLocations.get(random1.nextInt(athensLocations.size()));
            String address = athensAddresses.get(random1.nextInt(athensAddresses.size())) + " " + (random1.nextInt(200) + 1);
            int cost = random1.nextInt(2000) + 500;
            int floor = random1.nextInt(5) + 1;
            int size = random1.nextInt(150) + 50;
            int distanceFromUni = random1.nextInt(5000);
            int distanceFromMeans = random1.nextInt(5000);
            int numberOfBed = random1.nextInt(4) + 1;
            int furnished = random1.nextInt(2);  // 0 ή 1 για ναι η οχι
            int latitude = 37 + random1.nextInt();  // πλάτος δοκιμαστικα
            int longitude = 23 + random1.nextInt();  // μήκος δοκιμαστικα


            //ΕΛΕΥΘΕΡΙΑ ΑΥΤΑ ΣΤΑ ΣΧΟΛΙΑ ΧΡΗΣΙΜΟΠΟΙΗΣΕ ΚΑΙ ΘΑ ΤΟ ΔΙΟΡΘΩΣΩ ΣΤΟΝ ΚΩΔΙΚΑ ΜΟΥ
            // Περιορισμός γεωγραφικών συντεταγμένων στην Αττική
            //double latitude = 37.8 + (random1.nextDouble() * (38.2 - 37.8));  // Πλάτος: 37.8 έως 38.2
            //double longitude = 23.5 + (random1.nextDouble() * (24.0 - 23.5));  // Μήκος: 23.5 έως 24.0

            insertHouse(id, location, address, cost, floor, size, 
                        distanceFromUni, distanceFromMeans, numberOfBed, furnished, 
                        latitude, longitude);
        }
    }

    // Για εισαγωγη καθε σπιτιου
    public static void insertHouse(
            int id, String location, String address, int cost, int floor, int size,
            int distanceFromUni, int distanceFromMeans, int numberOfBed, int furnished, int latitude, int longitude) {
                    String insertSQL = String.format("""
                        INSERT INTO Houses (
                            Id, LocationH, AddressH, CostH, FloorH, SizeH, 
                            DistanceFromUni, DistanceFromMeans, NumberOfBed, Furnished, LatitudeH, LongitudeH
                        ) VALUES (%d, '%s', '%s', %d, %d, %d, %d, %d, %d, %d, %d, %d);
                    """, id, location, address, cost, floor, size, distanceFromUni, distanceFromMeans,
                            numberOfBed, furnished, latitude, longitude);

        System.out.println("Εντολή SQL προς εκτέλεση: " + insertSQL);
        
        DBConnection.executeUpdate(DB_URL, insertSQL);
        System.out.println("Δεδομένα εισήχθησαν με επιτυχία!");
    }
}
