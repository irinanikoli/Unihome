
import java.util.HashMap;
import java.util.Map;

public class Criteria {
    String stName;
    String stUniversity;
    double budget;
    double sqMeters;
    Map <String,Integer> preferences = new HashMap();
    public Criteria(String aname, String auniveristy, double abudget, double asqMeters) {
        stName = aname;
        stUniversity = auniveristy;
        budget = abudget;
        sqMeters = asqMeters;
    }
    public void setBudget(double newBudget) {
        budget = newBudget;
    }
    public double getBudget() {
        return budget;
    }
    public void setPreferences(String criteria, int prOrder) {
        preferences.put(criteria, prOrder);
    }
    public Map getPreferences() {
        return preferences;
    }
    @Override
    public String toString() {
            return "Όνομα:" + stName + ",Πανεπιστήμιο:" + stUniversity + ",Budget:" + budget + ",Επιθυμητή επιφάνεια σπιτιού:" + sqMeters;
    }
}
