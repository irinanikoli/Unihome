/*
 * Copyright 2025 [Your Name or Your Organization]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



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
            filehandler.setLevel(Level.ALL);
            logger.addHandler(filehandler);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            logger.addHandler(consoleHandler);
            //setting level of logger
            logger.setLevel(Level.ALL);
            //turnoff of parent handler to avoid double records
            logger.setUseParentHandlers(false);

        } catch(IOException e) {
            System.out.println("Error setting up filehandler" + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Logger getLogger() {
        return logger;
    }
}
