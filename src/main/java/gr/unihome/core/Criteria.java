package gr.unihome.core;

// it is written by Aimilia and commited by Panos due to technical problems

public class Criteria {
    String stName;
    String stUniversity;
    double maxBudget;
    double minSqMeters;
    double maxDistanceFromUni;
    double maxDistanceFromMeans;
    
    public Criteria(String stName, String stUniveristy, double maxBudget, double minSqMeters, double maxDistanceFromUni, double maxDistanceFromMeans) {
        this.stName = stName;
        this.stUniversity = stUniversity;
        this.maxBudget = maxBudget;
        this.minSqMeters = minSqMeters;
        this.maxDistanceFromUni = maxDistanceFromUni;
        this.maxDistanceFromMeans = maxDistanceFromMeans;
    }

    public String getStUniversity() {
        return this.stUniversity;
    }

    public void setStUniversity(String newStUniversity) {
        this.stUniversity = newStUniversity;
    }

    public double getBudget() {
        return maxBudget;
    }

    public void setBudget(double newBudget) {
        this.maxBudget = newBudget;
    }

    public double getMinSqMeters() {
        return this.minSqMeters;
    }

    public void setMinSqMeters(double newMinSqMeters) {
        this.minSqMeters = newMinSqMeters;
    }

    public double getMaxDistanceFromUni() {
        return this.maxDistanceFromUni;
    }

    public void setMaxDistanceFromUni(double newMaxDistanceFromUni) {
        this.maxDistanceFromUni = newMaxDistanceFromUni;
    }

    public double getMaxDistanceFromMeans() {
        return this.maxDistanceFromMeans;
    }

    public void setMaxDistanceFromMeans(double newMaxDistanceFromMeans) {
        this.maxDistanceFromMeans = newMaxDistanceFromMeans;
    }
    @Override
    public String toString() {
            return "Όνομα:" + this.stName + ",Πανεπιστήμιο:" + this.stUniversity + ",Budget:" + this.maxBudget + ",Επιθυμητή επιφάνεια σπιτιού:" + this.minSqMeters;
    }

}