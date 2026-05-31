package core;

import models.Cell;
import models.services.ServiceProvider;

public class SimlulationEngine {
    private CityGrid cityGrid;
    private int totalTicks;

    public SimlulationEngine(CityGrid cityGrid,int totalTicks){
        this.cityGrid=cityGrid;
        this.totalTicks=totalTicks;
    }
    public void startSimulation(){
        int rows= cityGrid.getRows();
        int cols= cityGrid.getCols();
        for(int tick = 1;tick<=totalTicks;tick++){
            for(int i = 0;i < rows;i++){
                for(int j = 0;j<cols;j++){
                    Cell cell = cityGrid.getCell(i,j);
                    if(cell==null){
                        cell.resetServices();
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

            //TODO:Develepor 3 BFS

            if(tick>1){
                //TODO Developer 2 distribute pool resources
            }

            //TODO Developer 2 update zone levels

            //TODO Developer 2 collect new productions
            renderGrid(tick);
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
                    System.out.println(cell.getSymbol()+" ");
                }
                else{
                    System.out.println(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
