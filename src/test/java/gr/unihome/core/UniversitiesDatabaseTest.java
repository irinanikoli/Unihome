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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversitiesDatabaseTest {
    // Setup method to initialize an in-memory database
    @BeforeAll
    public static void setUp() {
        // Initialize the database by dropping existing tables and creating a new one
        UniversitiesDatabase.initialize();
    }

    // Test to verify if the database table is created correctly
    @Test
    public void testInitialize() {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='universities';";
        
        // Connect to the database and check if the table was created
        try (Connection conn = DBConnection.connect("jdbc:sqlite:data/universities.db"); 
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Assert that the universities table exists in the in-memory database
            assertTrue(rs.next(), "The universities table should exist after initialization.");

        } catch (SQLException e) {
            assertTrue(false, "An error occurred while checking the table existence.");
        }
    }

    // Test to verify if a university is inserted correctly
    @Test
    public void testInsertUniversity() {
        // Data for a new university to be inserted
        String name = "Test University";
        double latitude = 37.983425;
        double longitude = 23.703813;

        // Insert the university into the database
        UniversitiesDatabase.insertUniversity(name, latitude, longitude);

        // Verify the insertion by checking if the university appears in the database
        String query = "SELECT * FROM universities WHERE UniversityName = ?;";
        
        try (Connection conn = DBConnection.connect("jdbc:sqlite:data/universities.db"); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Assert that the result set has the university we inserted
                assertTrue(rs.next(), "The university should be present in the database after insertion.");
                assertTrue(rs.getString("UniversityName").equals(name), "The university name should match the inserted name.");
                assertTrue(rs.getDouble("Latitude") == latitude, "The latitude should match the inserted latitude.");
                assertTrue(rs.getDouble("Longitude") == longitude, "The longitude should match the inserted longitude.");
            }

        } catch (SQLException e) {
            assertTrue(false, "An error occurred while inserting the university.");
        }
    }

    // Test to verify if multiple universities can be inserted correctly
    @Test
    public void testInsertMultipleUniversities() {
        // Insert multiple universities
        UniversitiesDatabase.insertRandomUniversities(3);

        // Check if the universities are inserted correctly
        String query = "SELECT COUNT(*) FROM universities;";
        
        try (Connection conn = DBConnection.connect("jdbc:sqlite:data/universities.db"); 
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            rs.next();
            int count = rs.getInt(1);
            assertTrue(count >= 3, "There should be at least 3 universities inserted.");
        } catch (SQLException e) {
            assertTrue(false, "An error occurred while inserting multiple universities.");
        }
    }
}
