package gr.unihome.core;

public class Criteria {
    String stName;
    String stUniversity;
    double maxBudget;
    double minSqMeters;
    double maxDistanceFromUni;
    double maxDistanceFromMeans;
    
    public Criteria(String aname, String auniveristy, double abudget, double asqMeters, double amaxDistanceFromUni, double amaxDistanceFromMeans) {
        this.stName = aname;
        this.stUniversity = auniveristy;
        this.maxBudget = abudget;
        this.minSqMeters = asqMeters;
        this.maxDistanceFromUni = amaxDistanceFromUni;
        this.maxDistanceFromMeans = amaxDistanceFromMeans;
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