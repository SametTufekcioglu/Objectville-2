package models.services;

import core.CityGrid;
import models.Cell;

public abstract class ServiceProvider extends Cell {
    private int radius;
    public ServiceProvider(int x,int y,int radius){
        super(x,y);
        this.radius=radius;
    }
    public int getRadius(){
        return radius;
    }
    public void distributeService(CityGrid cityGrid){
        int rows = cityGrid.getRows();
        int cols = cityGrid.getCols();
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                Cell target = cityGrid.getCell(i,j);
                if(target==null){
                    continue;
                }

                int distance = Math.abs(this.getX()-i)+Math.abs(this.getY()-j);

                if(distance<=this.radius){
                    applyService(target);
                }
            }
        }
    }
    protected abstract void applyService(Cell target);
}
