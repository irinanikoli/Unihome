import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Random;

public class CreateInsert {
    // gia thn ελευθεριααααααα
    //public static void main(String[] args) throws Exception {
      //  createHouses();
        //createUniversities();
       // insertDataHouses(100);
        //insertDataUniversities();
    //}
    
    private static void createHouses() {
        String sql = """
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
                Furnished INT CHECK (Furnished IN (0, 1)),
                Latitude DECIMAL(10, 8),
                Longitude DECIMAL(11, 8)
            );
            """;
        try (Connection conn = DatabaseConnection.connect();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Ο πίνακας Houses δημιουργήθηκε");
        } catch (Exception e) {
            System.out.println("Σφάλμα " + e.getMessage());
        }
    }

    private static void createUniversities() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Universities (
                UniversityName VARCHAR(100) PRIMARY KEY,
                Latitude DECIMAL(10, 8),
                Longitude DECIMAL(11, 8)
//            );
//            """;

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Ο πίνακας 'Universities' δημιουργήθηκε");
        } catch (Exception e) {
            System.out.println("Σφάλμα " + e.getMessage());
        }
    }

    private static void insertDataHouses(int numHouses) {
        // SQL για τον έλεγχο ύπαρξης ενός Id
        String sqlCheckExistence = """
            SELECT COUNT(*) FROM Houses WHERE Id = ?;
        """;
        
        // SQL για την εισαγωγή νέων δεδομένων
        String sqlInsert = """
            INSERT INTO Houses (
                Id, LocationH, AddressH, CostH, FloorH, SizeH, DistanceFromUni,
                DistanceFromMeans, NumberOfBed, Furnished, Latitude, Longitude
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

            //String sqlCheckExistence = "SELECT COUNT(*) FROM Houses WHERE Id = ?;"; // Ελέγξτε αν υπάρχει το Id

            List<String> athensLocations = List.of(
                "Κέντρο", "Παγκράτι", "Κολωνάκι", "Αμπελόκηποι", "Ζωγράφου", "Κηφισιά", 
                "Νέα Σμύρνη", "Πειραιάς", "Μαρούσι", "Γλυφάδα", "Περιστέρι", "Καλλιθέα", 
                "Ηλιούπολη", "Βύρωνας", "Χαλάνδρι", "Αιγάλεω", "Πετρούπολη", "Νέα Ιωνία", 
                "Νίκαια", "Μεταμόρφωση", "Άλιμος", "Αργυρούπολη", "Ηράκλειο", "Γαλάτσι", 
                "Πεντέλη", "Βριλήσσια", "Αγία Παρασκευή", "Χολαργός", "Παλαιό Φάληρο", 
                "Δάφνη", "Κερατσίνι", "Νέα Φιλαδέλφεια", "Μοσχάτο", "Ταύρος", "Πετράλωνα", 
                "Ψυρρή", "Θησείο", "Πλάκα", "Μακρυγιάννη", "Εξάρχεια", "Λυκαβηττός", 
                "Γκάζι", "Σεπόλια", "Ρουφ", "Καμάτερο", "Ζεφύρι", "Αγία Βαρβάρα", 
                "Αχαρνές", "Καπανδρίτι", "Σπάτα", "Παιανία"
            );
            
            List<String> athensAddresses = List.of(
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
            
        Random random = new Random();

        try (Connection conn = DatabaseConnection.connect();
            //PreparedStatement pstmt = conn.prepareStatement(sql)) { krata to sxolio
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
                PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheckExistence)) {

            for (int i = 1; i <= numHouses; i++) {
                // απο εδω
                pstmtCheck.setInt(1, i);
                //var rs = pstmtCheck.executeQuery();
                //if (rs.next() && rs.getInt(1) > 0) {
                ResultSet rs = pstmtCheck.executeQuery();
                rs.next();
                
                if (rs.getInt(1) > 0) {
                    System.out.println("Record with Id " + i + " already exists. Skipping insert.");
                    continue;  // Αν υπάρχει, προχωράμε στην επόμενη καταχώρηση
                }
                // μεχρι εδω ελεγχουμε αν υπαρχει ηδη το Ιδ kai proxvrame me thn eisagwgh
                pstmtInsert.setInt(1, i);
                pstmtInsert.setString(2, athensLocations.get(random.nextInt(athensLocations.size())));
                pstmtInsert.setString(3, athensAddresses.get(random.nextInt(athensAddresses.size())));
                pstmtInsert.setInt(4, random.nextInt(150000) + 50000);
                pstmtInsert.setInt(5, random.nextInt(6) + 1);
                pstmtInsert.setInt(6, random.nextInt(451) + 50);
                pstmtInsert.setInt(7, random.nextInt(40001));
                pstmtInsert.setInt(8, random.nextInt(4001));
                pstmtInsert.setInt(9, random.nextInt(10) + 1);
                pstmtInsert.setInt(10, random.nextInt(2));
                pstmtInsert.setDouble(11, -90 + (90 - (-90)) * random.nextDouble());
                pstmtInsert.setDouble(12, -180 + (180 - (-180)) * random.nextDouble());

                pstmtInsert.executeUpdate();
                System.out.println("Inserted record " + i);
            }
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την εισαγωγή δεδομένων: " + e.getMessage());
        }
    }

    private static void insertDataUniversities() {
        String sql = """
            INSERT INTO Universities (UniversityName, Latitude, Longitude)
            VALUES (?, ?, ?);
            """;

        List<String> universities = List.of(
            "Εθνικό Μετσόβιο Πολυτεχνείο",
            "Πανεπιστήμιο Αθηνών",
            "Πάντειο Πανεπιστήμιο",
            "Οικονομικό Πανεπιστήμιο Αθηνών",
            "Πανεπιστήμιο Πειραιώς"
        );

        List<double[]> coordinates = List.of(
            new double[] {37.9732, 23.7657},  // emp
            new double[] {37.9847, 23.7282},  // ekpa
            new double[] {37.9633, 23.7179},  // panteio
            new double[] {37.9954, 23.7327},  // opa
            new double[] {37.9405, 23.6531}   // papei
        );

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < universities.size(); i++) {
                pstmt.setString(1, universities.get(i));
                pstmt.setDouble(2, coordinates.get(i)[0]);
                pstmt.setDouble(3, coordinates.get(i)[1]);
                pstmt.executeUpdate();
                System.out.println("Inserted university: " + universities.get(i));
            }
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την εισαγωγή δεδομένων πανεπιστημίων: " + e.getMessage());
        }
    }
}
