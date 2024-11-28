
import java.util.HashMap;
import java.util.Map;

public class Criteria {
    String stName;
    String stUniversity;
    double maxBudget;
    double minSqMeters;
    double maxDistanceFromUni;
    double maxDistanceFromMeans;
    @SuppressWarnings({ "rawtypes", "unchecked" })
    Map <String,Integer> preferences = new HashMap();
    public Criteria(String aname, String auniveristy, double abudget, double asqMeters, double amaxDistanceFromUni, double amaxDistanceFromMeans) {
        stName = aname;
        stUniversity = auniveristy;
        maxBudget = abudget;
        minSqMeters = asqMeters;
        maxDistanceFromUni = amaxDistanceFromUni;
        maxDistanceFromMeans = amaxDistanceFromMeans;
    }

    public String getStUniversity() {
        return stUniversity;
    }

    public void setStUniversity(String newStUniversity) {
        stUniversity = newStUniversity;
    }

    public double getBudget() {
        return maxBudget;
    }

    public void setBudget(double newBudget) {
        maxBudget = newBudget;
    }

    public double getMinSqMeters() {
        return minSqMeters;
    }

    public void setMinSqMeters(double newMinSqMeters) {
        minSqMeters = newMinSqMeters;
    }

    public double getMaxDistanceFromUni() {
        return maxDistanceFromUni;
    }

    public void setMaxDistanceFromUni(double newMaxDistanceFromUni) {
        maxDistanceFromUni = newMaxDistanceFromUni;
    }

    public double getMaxDistanceFromMeans() {
        return maxDistanceFromMeans;
    }

    public void setMaxDistanceFromMeans(double newMaxDistanceFromMeans) {
        maxDistanceFromMeans = newMaxDistanceFromMeans;
    }

    public void setPreferences(String criteria, int prOrder) {
        preferences.put(criteria, prOrder);
    }

    @SuppressWarnings("rawtypes")
    public Map getPreferences() {
        return preferences;
    }

    @Override
    public String toString() {
            return "Όνομα:" + stName + ",Πανεπιστήμιο:" + stUniversity + ",Budget:" + maxBudget + ",Επιθυμητή επιφάνεια σπιτιού:" + minSqMeters;
    }

}
