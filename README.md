# Unihome

## Description
Unihome is a software that finds the optimal houses to university students based on their own needs and choices. The software utilizes genetic algorithms to find the first and at the same time best house and brute force algorithm to find the recommended list of houses. In order for houses to be chosen, a score is calculated. The mechanism of score involves normalized weights for the 4 criteria of the user (budget, size, distanceFromUni, distanceFromMeans) that are calculated based on the number of priority that the student chooses (1-4). The best house returned from the first algortithm is excluded from the list to avoid duplication. The project is right now on a more abstract case, as we tend to focus more on the retrieval of imaginary data that are randomly generated each and every time the program is run and of course, the efficient and correct calculation of the scores. We also have a very nice and modern graphical user interface, but unfortunately right now it isn't connected to the core part of the project. It is in our future plans though, as we are very optimistic to finalize the connection and also work with real-world data that can make the software great for real usage.


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

   
## UML Diagram

Below is the UML diagram of the project:

![UML Diagram](Στιγμιότυπο%20οθόνης%20(45).png)


## License

This project is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

You may obtain a copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

See the [LICENSE]("C:\UNIHOME\Unihome\Copyright 2025 Unihome.txt") file for more details.

