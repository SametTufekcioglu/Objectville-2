package models;

public abstract class Cell {
    protected int x;
    protected int y;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract char getSymbol();
}
