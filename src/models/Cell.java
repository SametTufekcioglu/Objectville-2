package models;

public abstract class Cell {
    protected int x;
    protected int y;

    private boolean hasSecurity = false;
    private boolean hasHealth = false;
    private boolean hasEducation = false;
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

    public boolean isHasSecurity() {
        return hasSecurity;
    }

    public void setHasSecurity(boolean hasSecurity) {
        this.hasSecurity = hasSecurity;
    }

    public boolean isHasHealth() {
        return hasHealth;
    }

    public void setHasHealth(boolean hasHealth) {
        this.hasHealth = hasHealth;
    }

    public boolean isHasEducation() {
        return hasEducation;
    }

    public void setHasEducation(boolean hasEducation) {
        this.hasEducation = hasEducation;
    }

    public void resetServices(){
        this.hasSecurity=false;
        this.hasHealth=false;
        this.hasEducation=false;
    }
    public abstract char getSymbol();
}
