package models.services;

import models.Cell;

public class School extends ServiceProvider{
    public School(int x, int y) {
        super(x, y, 4);
    }

    @Override
    protected void applyService(Cell target) {
        target.setHasEducation(true);
    }

    @Override
    public char getSymbol() {
        return 'S';
    }
}
