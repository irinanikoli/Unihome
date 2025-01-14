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
import java.sql.SQLException;

public class MeansDatabaseTest {
    
    @BeforeAll
    public static void setup() {
        // Initialize and clear the table before the tests
        MeansDatabase.initialize();
    }

    @Test
    public void testInsertMeans() {
        int uniqueCodeMeans = 0;

        MeansDatabase.insertMeans(uniqueCodeMeans, "Στάση Λεωφορείου", 37.9838, 23.7275);

        try (Connection conn = DBConnection.connect("jdbc:sqlite:data/means.db")) {
            assertNotNull(conn, "Connection to the database failed!");

            var statement = conn.createStatement();
            var result = statement.executeQuery("SELECT COUNT(*) FROM means WHERE CodeMeans = " + uniqueCodeMeans + ";");
            result.next();
            int count = result.getInt(1);
            assertEquals(1, count, "The database contains more or fewer than one entry for CodeMeans 1.");
            
            result = statement.executeQuery("SELECT * FROM means WHERE CodeMeans = " + uniqueCodeMeans + ";");
            assertTrue(result.next(), "The bus stop was not found in the database.");
            assertEquals(uniqueCodeMeans, result.getInt("CodeMeans"));
            assertEquals("Στάση Λεωφορείου", result.getString("MeansName"));
            assertEquals(37.9838, result.getDouble("Latitude"), 0.0001);
            assertEquals(23.7275, result.getDouble("Longitude"), 0.0001);
        } catch (SQLException e) {
            fail("Error during database connection or SQL execution: " + e.getMessage());
        }
    }

    @Test
    public void testInsertRandomMeans() {
        MeansDatabase.insertRandomMeans(5);

        try (Connection conn = DBConnection.connect("jdbc:sqlite:data/means.db")) {
            var statement = conn.createStatement();
            var result = statement.executeQuery("SELECT COUNT(*) FROM means;");
            result.next();
            int count = result.getInt(1);
            assertTrue(count >= 5, "Did not insert 5 bus stops.");
        } catch (SQLException e) {
            fail("Error during database connection or SQL execution: " + e.getMessage());
        }
    }

    @Test
    public void testTableExists() {
        try (Connection conn = DBConnection.connect("jdbc:sqlite:data/means.db")) {
            var statement = conn.createStatement();
            // Returns column info for the table
            var result = statement.executeQuery("PRAGMA table_info(means);");
            
            boolean foundCodeMeans = false;
            while (result.next()) {
                // Returns the column name
                String columnName = result.getString("name");
                if ("CodeMeans".equals(columnName)) {
                    foundCodeMeans = true;
                    break;
                }
            }
            
            assertTrue(foundCodeMeans, "The 'CodeMeans' column was not found in the table.");
        } catch (SQLException e) {
            fail("Error during database connection or SQL execution: " + e.getMessage());
        }
    }
    
}