package io;

import core.CityGrid;
import models.Cell;
import models.Empty;
import models.Road;
import models.utilities.PowerPlant;
import models.utilities.WaterPumpingStation;
import models.utilities.InternetHub;

import models.zones.Housing;
import models.zones.Industrial;
import models.zones.Commercial;
import models.services.PoliceStation;
import models.services.Hospital;
import models.services.School;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class MapParser {
     private int rows = 0;
     private int cols = 0;
     private CityGrid cityGrid;


    public void loadData(String filePath){
        Scanner fileReader = null;

        try{
            fileReader = new Scanner(Paths.get(filePath));
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                if(rows == 0){
                    cols = line.length();
                }
                rows++;
            }
            fileReader.close();

            cityGrid = new CityGrid(rows, cols);

            fileReader = new Scanner(Paths.get(filePath));
            int currentRow = 0;

            while(fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                for(int currentCol=0; currentCol<line.length(); currentCol++){
                    char symbol = line.charAt(currentCol);
                    Cell newCell = null;

                    if(symbol == 'R'){
                        newCell = new Road(currentRow, currentCol);
                    } else if (symbol == 'E') {
                        newCell = new Empty(currentRow, currentCol);
                    } else if (symbol == 'P') {
                        newCell = new PowerPlant(currentRow, currentCol);
                    } else if (symbol == 'W') {
                        newCell = new WaterPumpingStation(currentRow, currentCol);
                    } else if (symbol == 'T') {
                        newCell = new InternetHub(currentRow, currentCol);
                        // Dev 3 changed (added) below
                    } else if (symbol == 'H') {
                        newCell = new Housing(currentRow, currentCol);
                    } else if (symbol == 'I') {
                        newCell = new Industrial(currentRow, currentCol);
                    } else if (symbol == 'C') {
                        newCell = new Commercial(currentRow, currentCol);
                    } else if (symbol == 'F') {
                        newCell = new PoliceStation(currentRow, currentCol);
                    } else if (symbol == 'D') {
                        newCell = new Hospital(currentRow, currentCol);
                    } else if (symbol == 'S') {
                        newCell = new School(currentRow, currentCol);
                        // Dev 3 added above
                    } else {
                        newCell = new Empty(currentRow, currentCol);
                    }

                    cityGrid.addCell(currentRow,currentCol,newCell);
                }
                currentRow++;
            }
            fileReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CityGrid getCityGrid(){
        return cityGrid;
    }

}
