public class HousingOption {

    public class HousingOption {
        private String id; // Μοναδικό αναγνωριστικό για κάθε σπίτι
        private String location; // Περιγραφή τοποθεσίας
        private double cost; // Ενοίκιο
        private double size; // Τετραγωνικά μέτρα
        private double distanceFromUni; // Απόσταση από το πανεπιστήμιο
        private double distanceFromMeans; // Απόσταση από μέσα μαζικής μεταφοράς
    }

    public HousingOption(String id, String location, double cost, double size, double distanceFromUni, double distanceFromMeans) {
        this.id = id;
        this.location = location;
        this.cost = cost;
        this.size = size;
        this.distanceFromUni = distanceFromUni;
        this.distanceFromMeans = distanceFromMeans;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    @Override
    public String toString() {
        return "HousingOption{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", cost=" + cost +
                ", size=" + size +
                ", distanceFromUni=" + distanceFromUni +
                ", distanceFromMeans=" + distanceFromMeans +
                '}';
    }
}
