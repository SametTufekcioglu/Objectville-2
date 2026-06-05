package models.utilities;

import models.Cell;

public abstract class UtilityProvider extends Cell {

    protected int capacity;

    public UtilityProvider(int x, int y) {
        super(x, y);
        this.capacity = 100;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}