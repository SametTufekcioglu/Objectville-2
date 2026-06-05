package models.services;

import models.Cell;
import models.zones.Housing;

public class Hospital extends ServiceProvider{
    public Hospital(int x, int y) {
        super(x, y,3);
    }

    @Override
    protected boolean applyService(Cell target) {
        if (target instanceof Housing) {
            Housing house = (Housing) target;
            house.setHasHealth(true);
            return true;
        }return false;
    }

    @Override
    public char getSymbol() {
        return 'D';
    }
}
