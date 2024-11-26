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
        private double latitude; // syntetagmenes x
        private double longitude; // syntetagmenes y

        private DistanceCalculator distanceCalculator;
    

    public HousingOption(String id, String location, String address, double cost, int floor, double size, double distanceFromUni, double distanceFromMeans, int numberofbed, boolean furnished, double longitude, double latitude) {
        this.id = id;
        this.location = location;
        this.address = address;
        this.cost = cost;
        this.floor = floor;
        this.size = size;
        this.distanceFromUni = distanceFromUni;
        this.distanceFromMeans = distanceFromMeans;
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

    public void setNumberofbed(int numberofbed) {
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
                ", numberofbed=" + numberofbed +
                ", furnished=" + furnished +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}