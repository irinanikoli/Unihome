package gr.unihome.core;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class HousingOption {
        private static final Logger logger = AppLogger.getLogger();

        private static final String DB_HOUSES_URL = "jdbc:sqlite:houses.db";
        private int id; // Μοναδικό αναγνωριστικό για κάθε σπίτι
        private String location; // Περιγραφή τοποθεσίας
        private String address; // Διεύθυνση
        private int cost; // Ενοίκιο
        private int floor; // Όροφος
        private int size; // Τετραγωνικά μέτρα
        private double distanceFromUni; // Απόσταση από το πανεπιστήμιο
        private double distanceFromMeans; // Απόσταση από μέσα μαζικής μεταφοράς
        private int numberofbed; //Αριθμός υπνοδωματίων
        private int furnished; // επιπλωμέμο/ή όχι
        private double latitude; // γεωγραφικο πλατοσ
        private double longitude; // γεωγραφικο μηκοσ

    // constructor
    public HousingOption(int id, String location, String address, int cost, int floor,
            int size, double distanceFromUni, double distanceFromMeans, int numberofbed,
            int furnished, double latitude, double longitude) {
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
        this.distanceFromMeans = distanceFromMeans;
        this.distanceFromUni = distanceFromUni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
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

    public int getFurnished() {
        return furnished;
    }

    public void setFurnished(int furnished) {
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

    /**
     * method to fetch all housinig options form the datatbase house
     */
    public static List<HousingOption> fetchHousingOptionsFromDB() {
        List<HousingOption> housingOptions = new ArrayList<>();
        String query = "SELECT * FROM Houses";

        try (Connection houseConn = DBConnection.connect(DB_HOUSES_URL);
             PreparedStatement houseStmt = houseConn.prepareStatement(query);
             ResultSet rs = houseStmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("Id");
                    String location = rs.getString("LocationH");
                    String address = rs.getString("AddressH");
                    int cost = rs.getInt("CostH");
                    int floor = rs.getInt("FloorH");
                    int size = rs.getInt("SizeH");
                    double distanceFromUni = rs.getDouble("DistanceFromUni");
                    double distanceFromMeans = rs.getDouble("DistanceFromMeans");
                    int numberOfBed = rs.getInt("NumberOfBed");
                    int furnished = rs.getInt("Furnished");
                    double latitude = rs.getDouble("LatitudeH");
                    double longitude = rs.getDouble("LongitudeH");

                    HousingOption housingOption = new HousingOption(id, location, address,
                    cost, floor, size, distanceFromUni, distanceFromMeans, numberOfBed,
                    furnished, latitude, longitude);
                    housingOptions.add(housingOption);
                    System.out.println("Το σπίτι " + id + " εισήχθη με επιτυχία στην λίστα!");

                logger.info("Successful connection with the database and retrieval of data");
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την ανάκτηση δεδομένων από τη βάση: " + e.getMessage());
            logger.severe("Error during the retrieval of data from the database : " + e.getMessage());
        }
        return housingOptions;
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
