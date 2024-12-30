package gr.unihome.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    static int counter = 1;
    private static String budget = "";
    private static String mintm = "";
    private static String maxtm = "";
    private static String uniDistance = "";
    private static String metroDistance = "";
    private static String budget_preference = "";
    private static String tm_preference = ""; 
    private static String unidistance_preference = "";
    private static String mmmdistance_preference = "";
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
        if (counter == 1) {
            HousesDatabase.initialize();
            UniversitiesDatabase.initialize();
            MeansDatabase.initialize();

            HousesDatabase.insertRandomHouses(100);
            UniversitiesDatabase.insertRandomUniversities(8);
            MeansDatabase.insertRandomMeans(50);
        }
        counter++;


    }

}
