# Unihome

## Description
The Unihome software provides an efficient solution for allocating student housing based on user preferences. This document provides instructions on how to create an executable JAR for the Unihome project and test its functionality.

## Instructions

1. **Clone the Repository:**
   Clone this repository using the following URL:
   ```
   https://github.com/irinanikoli/Unihome.git
   ```

2. **Navigate to the Unihome Directory:**
   After cloning the repository, navigate to the directory containing the project files. For example:
   ```
   cd C:\UNIHOME\Unihome
   ```
   or follow the path where you stored the cloned files. Ensure you navigate into the second folder named `unihome` within the path.

3. **Build the Executable JAR:**
   Run the following command to clean the build directory and package the Unihome software as an executable JAR:
   ```
   mvn clean package
   ```

4. **Run the Executable JAR:**
   Execute the JAR file using the following command:
   ```
   java -jar "C:\UNIHOME\Unihome\target\unihome-1.0-SNAPSHOT.jar"
