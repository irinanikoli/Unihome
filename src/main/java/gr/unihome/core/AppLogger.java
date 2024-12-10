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
            FileHandler filehandler = new FileHandler("application.log", true);
            filehandler.setFormatter(new SimpleFormatter());
            logger.addHandler(filehandler);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);

        } catch(IOException e) {
            System.out.println("Error setting up filehandler" + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Logger getLogger() {
        return logger;
    }
}
