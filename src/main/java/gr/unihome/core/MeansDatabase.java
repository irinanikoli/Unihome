/*
 * Copyright 2025 Unihome
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

import java.util.List;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class handles the creation and management of a database for means of transportation.
 * The database includes information about transportation stops in the region of Attica, Greece.
 */
public class MeansDatabase {
    private static final String DB_MEANS_URL = "jdbc:sqlite:data/means.db";
    private static Random random1 = new Random();

        // List of transportation types to be inserted into the database
    private static List<String> nameMeans = List.of(
        "Στάση Λεωφορείου", "Στάση Μετρό γραμμή 1", "Στάση Μετρό γραμμή 2", "Στάση Μετρό γραμμή 3", "Στάση Προαστιακού", "Στάση Τραμ"
    );

    /**
     * Initializes the database by creating the table "means".
     * If the table already exists, it is dropped and recreated.
     */
    public static void initialize() {
        String dropTableSQL = "DROP TABLE IF EXISTS means;";
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS means (
               CodeMeans INT PRIMARY KEY,
               MeansName VARCHAR(100),
               Latitude REAL,
               Longitude REAL
             );
             """;

        try (Connection conn = DBConnection.connect(DB_MEANS_URL);
            PreparedStatement dropStmt = conn.prepareStatement(dropTableSQL);
            PreparedStatement createStmt = conn.prepareStatement(createTableSQL)) {
                dropStmt.executeUpdate();
                createStmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error while executing SQL for 'means': " + e.getMessage());
            }
    }

    /**
     * Inserts a specified number of random transportation stops into the database.
     * @param numberOfMeans the number of transportation stops to insert.
     */
    public static void insertRandomMeans(int numberOfMeans) {
        for (int i = 0; i < numberOfMeans; i++) {
            // Generate random data for each transportation stop
            int codeMeans = i + 1;
            String name = nameMeans.get(random1.nextInt(nameMeans.size()));
            double latitude = 37.8 + (random1.nextDouble() * (38.2 - 37.8));  // Latitude range: 37.8 to 38.2
            double longitude = 23.5 + (random1.nextDouble() * (24.0 - 23.5)); // Longitude range: 23.5 to 24.0

            // Insert the generated data into the database
            insertMeans(codeMeans, name, latitude, longitude);
        }
    }

    /**
     * Inserts a single transportation stop into the database.
     * @param codeMeans the unique code of the transportation stop.
     * @param name the name of the transportation stop.
     * @param latitude the latitude of the stop's location.
     * @param longitude the longitude of the stop's location.
     */
    public static void insertMeans(int codeMeans, String name, double latitude, double longitude) {
        String insertSQL = """
            INSERT INTO means (CodeMeans, MeansName, Latitude, Longitude)
            VALUES (?, ?, ?, ?);
        """; 

        try (Connection conn = DBConnection.connect(DB_MEANS_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            // Set the parameters for the SQL query
            pstmt.setInt(1, codeMeans);
            pstmt.setString(2, name);
            pstmt.setDouble(3, latitude);
            pstmt.setDouble(4, longitude);
            
            // Execute the insertion
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while executing SQL for 'means': " + e.getMessage());
        }
    }
}
