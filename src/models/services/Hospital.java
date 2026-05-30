package models.services;

import models.Cell;

public class Hospital extends ServiceProvider{
    public Hospital(int x, int y) {
        super(x, y,3);
    }

    @Override
    protected void applyService(Cell target) {
        target.setHasHealth(true);
    }

    @Override
    public char getSymbol() {
        return 'D';
    }
}
