package core;

import models.Cell;
import models.services.ServiceProvider;
import algorithms.BFSUtilityDistributor;
import models.utilities.UtilityProvider;
import models.zones.Zone;
import models.zones.Housing;
import models.zones.Industrial;
import models.zones.Commercial;

public class SimlulationEngine {
    private CityGrid cityGrid;
    private int totalTicks;
    private int poolPopulation = 0;
    private int poolGoods = 0;
    private int poolLifestyle = 0;

    public SimlulationEngine(CityGrid cityGrid,int totalTicks){
        this.cityGrid=cityGrid;
        this.totalTicks=totalTicks;
    }
public void startSimulation() {
    int rows = cityGrid.getRows();
    int cols = cityGrid.getCols();

    for (int tick = 1; tick <= totalTicks; tick++) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = cityGrid.getCell(i, j);
                if (cell != null) {
                    cell.resetServices();
                    if (cell instanceof Zone) {
                        ((Zone) cell).resetTurn();
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = cityGrid.getCell(i, j);
                if (cell instanceof ServiceProvider) {
                    ((ServiceProvider) cell).distributeService(cityGrid);
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = cityGrid.getCell(i, j);
                if (cell instanceof UtilityProvider) {
                    BFSUtilityDistributor.distribute(cityGrid, (UtilityProvider) cell);
                }
            }
        }

        if (tick > 1) {
            distributeResources(rows, cols);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = cityGrid.getCell(i, j);
                if (cell instanceof Zone) {
                    ((Zone) cell).updateTick();
                }
            }
        }

        poolPopulation = 0;
        poolGoods = 0;
        poolLifestyle = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = cityGrid.getCell(i, j);
                if (cell instanceof Housing) {
                    poolPopulation += ((Housing) cell).getOutput();
                } else if (cell instanceof Industrial) {
                    poolGoods += ((Industrial) cell).getOutput();
                } else if (cell instanceof Commercial) {
                    poolLifestyle += ((Commercial) cell).getOutput();
                }
            }
        }

        renderGrid(tick);
    }
}
    private void distributeResources(int rows, int cols) {
    int totalHousing = 0;
    int totalIndustrial = 0;
    int totalCommercial = 0;

    for(int i = 0; i < rows; i++){
        for(int j = 0; j < cols; j++){
            Cell cell = cityGrid.getCell(i, j);
            if(cell instanceof Housing) totalHousing++;
            else if(cell instanceof Industrial) totalIndustrial++;
            else if(cell instanceof Commercial) totalCommercial++;
        }
    }

    int totalWorkZones = totalIndustrial + totalCommercial;
    int popPerZone = totalWorkZones > 0 ? poolPopulation / totalWorkZones : 0;
    int goodsPerCommercial = totalCommercial > 0 ? poolGoods / totalCommercial : 0;
    int lifestylePerHousing = totalHousing > 0 ? poolLifestyle / totalHousing : 0;

    for(int i = 0; i < rows; i++){
        for(int j = 0; j < cols; j++){
            Cell cell = cityGrid.getCell(i, j);
            if(cell instanceof Industrial){
                ((Industrial)cell).setPopulation(popPerZone);
            } else if(cell instanceof Commercial){
                ((Commercial)cell).setPopulation(popPerZone);
                ((Commercial)cell).setGoods(goodsPerCommercial);
            } else if(cell instanceof Housing){
                ((Housing)cell).setLifestyle(lifestylePerHousing);
            }
        }
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
