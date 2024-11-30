package gr.unihome.core;

import java.util.logging.*;
import java.io.IOException;

public class AppLogger {
    /*
     * Uses Logger class from java.io.logging
     * A class used by all clases to record every error that occurs during the making
     * Singleton: Only 1 instance of this class will be used
     */
    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());

    static {
        //setting the "mistakes" file
        try {
            FileHandler filehandler = new FileHandler("logs/app.log", true);
            filehandler.setFormatter(new SimpleFormatter());
            logger.addHandler(filehandler);

        } catch(IOException e) {
            System.out.println("Error setting up filehandler" + e.getMessage());
            e.printStackTrace();
        }
    }
    private AppLogger() {

    }
    public static AppLogger getInstance() {
        return new AppLogger();
    }
    public void log(String message) {
        logger.info(message);
    }
    public void logError(String errorMessage) {
        logger.severe(errorMessage);
    }
}
