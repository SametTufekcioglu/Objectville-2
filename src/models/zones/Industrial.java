package models.zones;

public class Industrial extends Zone {
    public Industrial(int x, int y) {
        super(x, y);
    }

    @Override 
    public char getSymbol() { 
        return 'I'; 
    }
    
    @Override 
    public boolean needsElectricity() { 
        return true; 
    }
    
    @Override 
    public boolean needsWater() { 
        return true; 
    }
    
    @Override 
    public boolean needsInternet() { 
        return false; 
    } 

    @Override
    protected boolean canLevelUpTo(int targetLevel) {
        if (targetLevel <= 1) {
            return true;
        }
        if (targetLevel == 2) {
            return hasSecurity;
        }
        if (targetLevel == 3) {
            return hasSecurity && receivedPopulation > 0;
        }
        return false;
    }

    @Override
    protected int calculateOutput(int m) {
        if (level == 0) return 0;
        if (level == 1) return m;
        if (level == 2) return 2 * m;
        return (2 * m) + receivedPopulation;
    }

    public void setPopulation(int p) { 
        this.receivedPopulation = p; 
    }
    
    /*@Override
    public void resetTurn() {
        super.resetTurn();
        this.receivedPopulation = 0;
    }*/
}