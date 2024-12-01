package gr.unihome.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
public class HousingOption {

        private String id; // Μοναδικό αναγνωριστικό για κάθε σπίτι
        private String location; // Περιγραφή τοποθεσίας
        private String address; // Διεύθυνση
        private double cost; // Ενοίκιο
        private int floor; // Όροφος
        private double size; // Τετραγωνικά μέτρα
        private double distanceFromUni; // Απόσταση από το πανεπιστήμιο
        private double distanceFromMeans; // Απόσταση από μέσα μαζικής μεταφοράς
        private int numberofbed; //Αριθμός υπνοδωματίων
        private boolean furnished; // επιπλωμέμο/ή όχι
        private double latitude; // γεωγραφικο πλατοσ
        private double longitude; // γεωγραφικο μηκοσ

        private DistanceCalculator distanceCalculator;

    public HousingOption(String id, String location, String address, double cost, int floor, double size, double distanceFromUni, double distanceFromMeans, int numberofbed, boolean furnished, double latitude, double longitude) {
        this.id = id;
        this.location = location;
        this.address = address;
        this.cost = cost;
        this.floor = floor;
        this.size = size;
        this.numberofbed = numberofbed;
        this.furnished = furnished;
        this.longitude = longitude;
        this.latitude = latitude;

        this.distanceCalculator = new DistanceCalculator();
        Map<String, Double> distances = this.distanceCalculator.calculateDistances(address);
        this.distanceFromUni = distances.get("uni");
        this.distanceFromMeans = distances.get("means");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getDistanceFromUni() {
        return distanceFromUni;
    }

    public void setDistanceFromUni(double distanceFromUni) {
        this.distanceFromUni = distanceFromUni;
    }

    public double getDistanceFromMeans() {
        return distanceFromMeans;
    }

    public void setDistanceFromMeans(double distanceFromMeans) {
        this.distanceFromMeans = distanceFromMeans;
    }

    public int getNumberofbed() {
        return numberofbed;
    }

    public void setNumberOfBed(int numberofbed) {
        this.numberofbed = numberofbed;
    }

    public boolean getFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void loadFromDatabase(String houseId) {
        String query = "SELECT * FROM Houses WHERE Id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:houses.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, houseId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                setId(rs.getString("Id"));
                setLocation(rs.getString("LocationH"));
                setAddress(rs.getString("AddressH"));
                setCost(rs.getDouble("CostH"));
                setFloor(rs.getInt("FloorH"));
                setSize(rs.getDouble("SizeH"));
                setDistanceFromUni(rs.getDouble("DistanceFromUni"));
                setDistanceFromMeans(rs.getDouble("DistanceFromMeans"));
                setNumberOfBed(rs.getInt("NumberOfBed"));
                setFurnished(rs.getInt("Furnished") == 1);
                setLatitude(rs.getDouble("LatitudeH"));
                setLongitude(rs.getDouble("LongitudeH"));

                // Καλούμε τον DistanceCalculator για την ενημέρωση των αποστάσεων
                Map<String, Double> distances = distanceCalculator.calculateDistances(getAddress());
                setDistanceFromUni(distances.get("uni"));
                setDistanceFromMeans(distances.get("means"));

                System.out.println("Επιτυχής φόρτωση δεδομένων για το σπίτι με ID: " + houseId);
            } else {
                System.out.println("Δεν βρέθηκε σπίτι με το ID: " + houseId);
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση δεδομένων από τη βάση: " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "HousingOption{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", address=" + address +
                ", cost=" + cost +
                ", floor=" + floor +
                ", size=" + size +
                ", distanceFromUni=" + distanceFromUni +
                ", distanceFromMeans=" + distanceFromMeans +
                ", numberodbed=" + numberofbed +
                ", furnished=" + furnished +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
