package io;

import core.CityGrid;
import models.Cell;
import models.Empty;
import models.Road;

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

            fileReader = new Scanner(Paths.get("mymap.txt"));
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

                //siz buradaki diğer yerleri doldurunca (H, I falan) bu else kısmını da silersiniz
                    }else{
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
