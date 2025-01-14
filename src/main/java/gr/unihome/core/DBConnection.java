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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DBConnection class provides utility methods for connecting to a database
 * and executing SQL update statements.
 */
public class DBConnection {
     /**
     * Establishes a connection to the specified database.
     * 
     * @param dbUrl The URL of the database to connect to.
     * @return A Connection object if the connection is successful, or null if the connection fails.
     */
    public static Connection connect(String dbUrl) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
        }
        return conn;
    }

     /**
     * Executes a SQL update statement (e.g., INSERT, UPDATE, DELETE) on the specified database.
     * 
     * @param dbUrl The URL of the database where the SQL statement will be executed.
     * @param sql The SQL update statement to execute.
     */
    public static void executeUpdate(String dbUrl, String sql) {
        try (Connection conn = connect(dbUrl)) {
                // Check if the connection is null before proceeding
                if (conn == null) {
                    System.out.println("Connection failed. Cannot execute the SQL statement.");
                    return; // Exit the method if the connection is not established
                }
                // Create a Statement object to execute the SQL statement
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(sql);
                }
        } catch (SQLException e) {
            System.out.println("Error while executing SQL: " + e.getMessage());
        }
    }
}
