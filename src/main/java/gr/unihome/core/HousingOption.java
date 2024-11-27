public class HousingOption {

        private String id; // Μοναδικό αναγνωριστικό για κάθε σπίτι
        private String location; // Περιγραφή τοποθεσίας
        private String address;
        private double cost; // Ενοίκιο
        private int floor;
        private double size; // Τετραγωνικά μέτρα
        private double distanceFromUni; // Απόσταση από το πανεπιστήμιο
        private double distanceFromMeans; // Απόσταση από μέσα μαζικής μεταφοράς
        private int numberofbed;
        private boolean furnished;
    

    public HousingOption(String id, String location, String address, double cost, int floor, double size, double distanceFromUni, double distanceFromMeans, int numberofbed, boolean furnished) {
        this.id = id;
        this.location = location;
        this.address = address;
        this.cost = cost;
        this.floor = floor;
        this.size = size;
        this.distanceFromUni = distanceFromUni;
        this.distanceFromUni = distanceFromUni;
        this.numberofbed = numberofbed;
        this.furnished = furnished;
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
                '}';
    }
}
