import io.jenetics.*;
import io.jenetics.engine.*;
import java.util.List;

public class MatchingEngine {
    private List<HousingOption> housingOptions; 
    public static final int topN = 10;
    // number of houses to return
    public MatchingEngine(List<HousingOption> housingOptions) {
        this.housingOptions = housingOptions;
    }
    public double evaluate(HousingOption ho, Student preferences) {


    }
    
    
}
