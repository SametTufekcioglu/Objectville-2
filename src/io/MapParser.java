package io;

import core.CityGrid;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class MapParser {
     int rows = 0;
     int cols = 0;
    CityGrid cityGrid = new CityGrid(rows, cols);

    public void loadData(){
        Scanner fileReader = null;

        try{
            fileReader = new Scanner(Paths.get("mymap.txt"));
            while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                if(rows == 0){
                    cols = line.length();
                }
                rows++;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
//hg



}
