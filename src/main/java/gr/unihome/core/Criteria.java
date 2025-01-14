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



public class Criteria {
    String stName;
    String stUniversity;
    double maxBudget;
    double minSqMeters;
    double maxDistanceFromUni;
    double maxDistanceFromMeans;
    
    public Criteria(String stName, String stUniversity, double maxBudget, double minSqMeters, double maxDistanceFromUni, double maxDistanceFromMeans) {
        this.stName = stName;
        this.stUniversity = stUniversity;
        this.maxBudget = maxBudget;
        this.minSqMeters = minSqMeters;
        this.maxDistanceFromUni = maxDistanceFromUni;
        this.maxDistanceFromMeans = maxDistanceFromMeans;
    }

    public String getStName() {
        return this.stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStUniversity() {
        return this.stUniversity;
    }

    public void setStUniversity(String stUniversity) {
        this.stUniversity = stUniversity;
    }

    public double getBudget() {
        return maxBudget;
    }

    public void setBudget(double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public double getMinSqMeters() {
        return this.minSqMeters;
    }

    public void setMinSqMeters(double minSqMeters) {
        this.minSqMeters = minSqMeters;
    }

    public double getMaxDistanceFromUni() {
        return this.maxDistanceFromUni;
    }

    public void setMaxDistanceFromUni(double maxDistanceFromUni) {
        this.maxDistanceFromUni = maxDistanceFromUni;
    }

    public double getMaxDistanceFromMeans() {
        return this.maxDistanceFromMeans;
    }

    public void setMaxDistanceFromMeans(double maxDistanceFromMeans) {
        this.maxDistanceFromMeans = maxDistanceFromMeans;
    }
    @Override
    public String toString() {
            return "Όνομα:" + this.stName + ",Πανεπιστήμιο:" + this.stUniversity + ",Budget:" + this.maxBudget + ",Επιθυμητή επιφάνεια σπιτιού:" + this.minSqMeters;
    }

}