package core;

import models.Cell;
import models.services.ServiceProvider;
import algorithms.BFSUtilityDistributor;
import models.utilities.UtilityProvider;
import models.zones.Zone;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SimlulationEngine {
    private CityGrid cityGrid;
    private int totalTicks;

    public SimlulationEngine(CityGrid cityGrid,int totalTicks){
        this.cityGrid=cityGrid;
        this.totalTicks=totalTicks;
    }
    public void startSimulation() throws IOException {
        int rows= cityGrid.getRows();
        int cols= cityGrid.getCols();
        for(int tick = 1;tick<=totalTicks;tick++){
            for(int i = 0;i < rows;i++){
                for(int j = 0;j<cols;j++){
                    Cell cell = cityGrid.getCell(i,j);
                    if(cell!=null){
                        cell.resetServices();
                        // Dev 3 added: reset zone utilities each tick
                        if(cell instanceof Zone){
                            ((Zone)cell).resetTurn();
                        }//.
                    }
                }
            }
            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    Cell cell= cityGrid.getCell(i,j);
                    if(cell instanceof ServiceProvider){
                        ((ServiceProvider)cell).distributeService(cityGrid);
                    }
                }
            }

            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    Cell cell = cityGrid.getCell(i,j);
                    if(cell instanceof UtilityProvider){
                        BFSUtilityDistributor.distribute(cityGrid, (UtilityProvider) cell);
                    }
                }
            }

            // DEV 3 WRITED
            // STEP 2B: Log services received
            FileWriter fileWriter = new FileWriter("output.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    Cell cell = cityGrid.getCell(i, j);
                    if(cell instanceof Zone){
                        Zone zone = (Zone) cell;
                        String zoneType = zone.getClass().getSimpleName();

                        if(zone.isSecurity()){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received security service");
                        }
                        if(zone.isHealth()){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received health service");
                        }
                        if(zone.isEducation()){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received education service");
                        }
                    }
                }
            }

            // DEV 3 WRITED
            if(tick>1){
                //TODO Developer 2 distribute pool resources
                //DEV 3 WRITED
                int totalPopulation = 0;
                int totalGoods = 0;
                int totalLifestyle = 0;

                // Önce tüm çıktıları topla
                for(int i = 0; i < rows; i++){
                    for(int j = 0; j < cols; j++){
                        Cell cell = cityGrid.getCell(i, j);
                        if(cell instanceof Zone){
                            Zone zone = (Zone) cell;
                            String zoneType = zone.getClass().getSimpleName();

                            if(zoneType.equals("House")){
                                totalPopulation += zone.getOutput();
                            }
                            else if(zoneType.equals("Industrial")){
                                totalGoods += zone.getOutput();
                            }
                            else if(zoneType.equals("Commercial")){
                                totalLifestyle += zone.getOutput();
                            }
                        }
                    }
                }

                // Nüfus dağıtımı: Industrial ve Commercial'e eşit şekilde
                if(totalPopulation > 0){
                    int industrialZoneCount = 0;
                    int commercialZoneCount = 0;

                    // Önce say
                    for(int i = 0; i < rows; i++){
                        for(int j = 0; j < cols; j++){
                            Cell cell = cityGrid.getCell(i, j);
                            if(cell instanceof Zone){
                                String zoneType = cell.getClass().getSimpleName();
                                if(zoneType.equals("Industrial")) industrialZoneCount++;
                                else if(zoneType.equals("Commercial")) commercialZoneCount++;
                            }
                        }
                    }

                    // Sonra dağıt
                    if(industrialZoneCount > 0){
                        int popPerZone = totalPopulation / industrialZoneCount;
                        int remainder = totalPopulation % industrialZoneCount;
                        int count = 0;
                        for(int i = 0; i < rows; i++){
                            for(int j = 0; j < cols; j++){
                                Cell cell = cityGrid.getCell(i, j);
                                if(cell instanceof Zone){
                                    String zoneType = cell.getClass().getSimpleName();
                                    if(zoneType.equals("Industrial")){
                                        Zone zone = (Zone) cell;
                                        int pop = popPerZone + (count < remainder ? 1 : 0);
                                        zone.addReceivedPopulation(pop);
                                        count++;
                                    }
                                }
                            }
                        }
                    }

                    if(commercialZoneCount > 0){
                        int popPerZone = totalPopulation / commercialZoneCount;
                        int remainder = totalPopulation % commercialZoneCount;
                        int count = 0;
                        for(int i = 0; i < rows; i++){
                            for(int j = 0; j < cols; j++){
                                Cell cell = cityGrid.getCell(i, j);
                                if(cell instanceof Zone){
                                    String zoneType = cell.getClass().getSimpleName();
                                    if(zoneType.equals("Commercial")){
                                        Zone zone = (Zone) cell;
                                        int pop = popPerZone + (count < remainder ? 1 : 0);
                                        zone.addReceivedPopulation(pop);
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }

                // Mallar dağıtımı: Houses'e eşit şekilde
                if(totalGoods > 0){
                    int houseCount = 0;
                    for(int i = 0; i < rows; i++){
                        for(int j = 0; j < cols; j++){
                            Cell cell = cityGrid.getCell(i, j);
                            if(cell instanceof Zone){
                                String zoneType = cell.getClass().getSimpleName();
                                if(zoneType.equals("House")) houseCount++;
                            }
                        }
                    }

                    if(houseCount > 0){
                        int goodsPerZone = totalGoods / houseCount;
                        int remainder = totalGoods % houseCount;
                        int count = 0;
                        for(int i = 0; i < rows; i++){
                            for(int j = 0; j < cols; j++){
                                Cell cell = cityGrid.getCell(i, j);
                                if(cell instanceof Zone){
                                    String zoneType = cell.getClass().getSimpleName();
                                    if(zoneType.equals("House")){
                                        Zone zone = (Zone) cell;
                                        int goods = goodsPerZone + (count < remainder ? 1 : 0);
                                        zone.addReceivedGoods(goods);
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }

                // Yaşam tarzı dağıtımı: Houses'e eşit şekilde
                if(totalLifestyle > 0){
                    int houseCount = 0;
                    for(int i = 0; i < rows; i++){
                        for(int j = 0; j < cols; j++){
                            Cell cell = cityGrid.getCell(i, j);
                            if(cell instanceof Zone){
                                String zoneType = cell.getClass().getSimpleName();
                                if(zoneType.equals("House")) houseCount++;
                            }
                        }
                    }

                    if(houseCount > 0){
                        int lifestylePerZone = totalLifestyle / houseCount;
                        int remainder = totalLifestyle % houseCount;
                        int count = 0;
                        for(int i = 0; i < rows; i++){
                            for(int j = 0; j < cols; j++){
                                Cell cell = cityGrid.getCell(i, j);
                                if(cell instanceof Zone){
                                    String zoneType = cell.getClass().getSimpleName();
                                    if(zoneType.equals("House")){
                                        Zone zone = (Zone) cell;
                                        int lifestyle = lifestylePerZone + (count < remainder ? 1 : 0);
                                        zone.addReceivedLifestyle(lifestyle);
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // STEP 4B: Zone output'u logla ÖNCE updateTick() çağrılmadan
            //FileWriter fileWriter = new FileWriter("output.txt", true); // append mode
            //PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("Tick " + tick);

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    Cell cell = cityGrid.getCell(i, j);
                    if(cell instanceof Zone){
                        Zone zone = (Zone) cell;
                        String zoneType = zone.getClass().getSimpleName();

                        // Services log
                        if(zone.isSecurity()){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received security service");
                        }
                        if(zone.isHealth()){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received health service");
                        }
                        if(zone.isEducation()){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received education service");
                        }

// Utilities log
                        if(zone.getReceivedElectricity() > 0){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received " + zone.getReceivedElectricity() + " electricity");
                        }
                        if(zone.getReceivedWater() > 0){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received " + zone.getReceivedWater() + " water");
                        }
                        if(zone.getReceivedInternet() > 0){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received " + zone.getReceivedInternet() + " internet");
                        }

// Received resources log
                        if(zone.getReceivedPopulation() > 0){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received " + zone.getReceivedPopulation() + " population");
                        }
                        if(zone.getReceivedGoods() > 0){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received " + zone.getReceivedGoods() + " goods");
                        }
                        if(zone.getReceivedLifestyle() > 0){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") received " + zone.getReceivedLifestyle() + " lifestyle");
                        }
                    }
                }
            }

            //TODO Developer 2 update zone levels
            // Update zone levels
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    Cell cell = cityGrid.getCell(i, j);
                    if(cell instanceof Zone){
                        Zone zone = (Zone) cell;
                        int oldLevel = zone.getLevel();
                        zone.updateTick();
                        int newLevel = zone.getLevel();

                        if(oldLevel != newLevel){
                            printWriter.println(zone.getClass().getSimpleName() + " at (" + i + "," + j + ") levels " +
                                    (newLevel > oldLevel ? "up" : "down") + " from " + oldLevel + " to " + newLevel);
                        }
                    }
                }
            }

// STEP 5: Yeni üretim toplanır ve loglanır
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    Cell cell = cityGrid.getCell(i, j);
                    if(cell instanceof Zone){
                        Zone zone = (Zone) cell;
                        String zoneType = zone.getClass().getSimpleName();
                        int output = zone.getOutput();

                        String resourceType = "";
                        if(zoneType.equals("House")) resourceType = "population";
                        else if(zoneType.equals("Housing")) resourceType = "population";  // İKİ OPTION DA EKLE
                        else if(zoneType.equals("Industrial")) resourceType = "goods";
                        else if(zoneType.equals("Commercial")) resourceType = "lifestyle";

                        if(output > 0){
                            printWriter.println(zoneType + " at (" + i + "," + j + ") generated " + output + " " + resourceType);
                        }
                    }
                }
            }

            printWriter.println();
            printWriter.close();
            // DEV 3 WRITED

            //TODO Developer 2 collect new productions
            //renderGrid(tick);
        }
    }

    private void renderGrid(int currentTick){
        System.out.println("---TICK "+currentTick+" ---");
        int rows = cityGrid.getRows();
        int cols = cityGrid.getCols();

        for(int i=0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                Cell cell = cityGrid.getCell(i,j);
                if(cell!=null){
                    System.out.print(cell.getSymbol());
                    //System.out.println(cell.getSymbol()+" ");
                }
                else{
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
