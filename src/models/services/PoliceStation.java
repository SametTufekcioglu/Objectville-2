package models.services;

import models.Cell;

public class PoliceStation extends ServiceProvider{
    public PoliceStation(int x,int y){
        super(x,y,5);
    }
    @Override
    protected void applyService(Cell target){
        target.setHasSecurity(true);
    }
    @Override
    public char getSymbol(){
        return'F';
    }
}
