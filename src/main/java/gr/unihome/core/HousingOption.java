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

        private static final String DB_HOUSES_URL = "jdbc:sqlite:data/houses.db";
        private int id; // Unique identifier for each house
        private String location; // Description of the location
        private String address; // Address of the house
        private int cost; // Rent cost
        private int floor; // Floor number
        private int size; // Area in square meters
        private double distanceFromUni; // Distance from university
        private double distanceFromMeans; // Distance from public transportation
        private int numberOfBed; // Number of bedrooms
        private int furnished; // Indicates if the house is furnished
        private double latitude; // Geographical latitude
        private double longitude; // Geographical longitude

    // Constructor
    public HousingOption(int id, String location, String address, int cost, int floor,
            int size, double distanceFromUni, double distanceFromMeans, int numberOfBed,
            int furnished, double latitude, double longitude) {
        this.id = id;
        this.location = location;
        this.address = address;
        this.cost = cost;
        this.floor = floor;
        this.size = size;
        this.numberOfBed = numberOfBed;
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

    public int getNumberOfBed() {
        return numberOfBed;
    }

    public void setNumberOfBed(int numberOfBed) {
        this.numberOfBed = numberOfBed;
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
     * Method to fetch all housinig options form the datatbase house
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
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving data from the database: " + e.getMessage());
            logger.severe("Error during the retrieval of data from the database : " + e.getMessage());
        }
        return housingOptions;
    }


    @Override
    public String toString() {
        return String.format(
            """
            ------------------------------------
            ID: %d
            Τοποθεσία: %s
            Διεύθυνση: %s
            Κόστος: €%d
            Όροφος: %d
            Μέγεθος: %d τ.μ.
            Απόσταση από Πανεπιστήμιο: %.2f km
            Απόσταση από Μέσα Μεταφοράς: %.2f km
            Αριθμός Κλινών: %d
            Επίπλωση: %s
            Συντεταγμένες: (%.6f, %.6f)
            ------------------------------------
            """,
            id,
            location,
            address,
            cost,
            floor,
            size,
            distanceFromUni,
            distanceFromMeans,
            numberOfBed,
            (furnished == 1 ? "Ναι" : "Όχι"),
            latitude,
            longitude
        );
    }
}
