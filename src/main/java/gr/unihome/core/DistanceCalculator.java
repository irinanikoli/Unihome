/**
 * Copyright 2025 Unihome

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */


package gr.unihome.core;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DistanceCalculator {
    private static final Logger logger = AppLogger.getLogger();
    
    // Database connection URLs
    private static final String DB_HOUSES_URL = "jdbc:sqlite:data/houses.db";
    private static final String DB_UNI_URL = "jdbc:sqlite:data/universities.db";
    private static final String DB_MEANS_URL = "jdbc:sqlite:data/means.db";
    

    
    // Calculates the distance between two points based on their coordinates (latitude, longitude)
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
            final int R = 6371; // Earth's radius in kilometers
            
            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                     + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                     * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            
            return R * c; // Returns the distance in kilometers
        }
    
        /**
         * @param universityName The name of the university provided by the user
         * Connects to the databases, retrieves coordinates of the university and houses,
         * calculates distances for each house, and updates the `DistanceFromUni` column in the `Houses` table.
         */
        public static void calculateDistancesBetweenHousesAndUni(String universityName) {

            String houseQuery = "SELECT Id, LatitudeH, LongitudeH FROM Houses;";
            String uniQuery = "SELECT UniversityName, Latitude, Longitude FROM universities WHERE UniversityName = ?;";
    
            try (Connection houseConn = DriverManager.getConnection(DB_HOUSES_URL);
                 Connection uniConn = DriverManager.getConnection(DB_UNI_URL);
                 PreparedStatement houseStmt = houseConn.prepareStatement(houseQuery);
                 PreparedStatement uniStmt = uniConn.prepareStatement(uniQuery)) {
    
                 // Set parameter for the university
                 uniStmt.setString(1, universityName);
                 try (ResultSet uniRs = uniStmt.executeQuery()) {
                    double uniLat = uniRs.getDouble("Latitude");
                    double uniLon = uniRs.getDouble("Longitude");
    
                    try (ResultSet houseRs = houseStmt.executeQuery()) {
                        // Retrieve house details and calculate distances
                        while (houseRs.next()) {
                            int houseId = houseRs.getInt("Id");
                            double houseLat = houseRs.getDouble("LatitudeH");
                            double houseLon = houseRs.getDouble("LongitudeH");
                            
                            double distance = calculateDistance(houseLat, houseLon, uniLat, uniLon);
                        updateUniCoordinates(houseConn, houseId, distance);                        
                    }
                }       
             }
        } catch (SQLException e) {
            logger.severe("Error with the sql connection : " + e.getMessage());
            System.err.println("Error calculating distances: " + e.getMessage());
        }
    }

    /**
     * @param houseId The ID of the house being updated
     * @param distance The calculated distance from the university
     * Updates the `DistanceFromUni` column in the `Houses` table.
     */
    public static void updateUniCoordinates(Connection conn, int houseId, double distance) {
        String updateSQL = """
            UPDATE Houses
            SET DistanceFromUni = ?
            WHERE Id = ?;
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            
            pstmt.setDouble(1, distance);
            pstmt.setInt(2, houseId);

            pstmt.executeUpdate();            
        } catch (SQLException e) {
            System.err.println("Error executing SQL in houses: " + e.getMessage());
        }
    }

    /**
     * Connects to the databases, calculates distances between houses and means of transport,
     * determines the closest mean for each house, and updates the `DistanceFromMeans` column in the `Houses` table.
     */
    public static void calculateDistancesBetweenHousesAndMeans() {
        String selectHousesSQL = "SELECT Id, LatitudeH, LongitudeH FROM houses;";
        String selectMeansSQL = "SELECT CodeMeans, Latitude, Longitude FROM means;";
        
        try (Connection meansConn = DriverManager.getConnection(DB_MEANS_URL);
             Connection houseConn = DriverManager.getConnection(DB_HOUSES_URL);
             PreparedStatement meansStmt = meansConn.prepareStatement(selectMeansSQL)) {
             PreparedStatement houseStmt = houseConn.prepareStatement(selectHousesSQL);
            
            try (ResultSet houseResultSet = houseStmt.executeQuery()) {
                while (houseResultSet.next()) {
                    int houseId = houseResultSet.getInt("Id");
                    double houseLat = houseResultSet.getDouble("LatitudeH");
                    double houseLon = houseResultSet.getDouble("LongitudeH");
                    
                    double minDistance = Double.MAX_VALUE;
                    int closestMeansId = -1;

                    try (ResultSet meansResultSet = meansStmt.executeQuery()) {
                        while (meansResultSet.next()) {
                            int meansId = meansResultSet.getInt("CodeMeans");
                            double meansLat = meansResultSet.getDouble("Latitude");
                            double meansLon = meansResultSet.getDouble("Longitude");
                            
                            double distance = calculateDistance(meansLat, meansLon, houseLat, houseLon);                           

                            if (distance < minDistance || (distance == minDistance && meansId < closestMeansId)) {
                                minDistance = distance;
                                closestMeansId = meansId;
                            }
                        }
                    }
                    updateMeansCoordinates(houseConn, houseId, minDistance);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error with SQL connection : " + e.getMessage());
            System.err.println("Error calculating distances: " + e.getMessage());
        }   
    }

    /**
     * @param houseId The ID of the house being updated
     * @param distance The calculated distance from the means
     * Updates the `DistanceFromMeans` column in the `Houses` table.
     */
    public static void updateMeansCoordinates(Connection conn, int houseId, double distance) {
        String updateClosestMeansSQL = """
            UPDATE Houses
            SET DistanceFromMeans = ?
            WHERE Id = ?;
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(updateClosestMeansSQL)) {
            
            pstmt.setDouble(1, distance);
            pstmt.setInt(2, houseId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Σφάλμα κατά την εκτέλεση SQL στο houses: " + e.getMessage());
        }
    }
}