package models.zones;

public class Housing extends Zone {
    public Housing(int x, int y) {
        super(x, y);
    }

    @Override 
    public char getSymbol() { 
        return 'H'; 
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
        return true; 
    }

    @Override
    protected boolean canLevelUpTo(int targetLevel) {
        if (targetLevel <= 1) {
            return true;
        }
        if (targetLevel == 2) {
            return hasSecurity && hasHealth && hasEducation;
        }
        if (targetLevel == 3) {
            return hasSecurity && hasHealth && hasEducation && receivedLifestyle > 0;
        }
        return false;
    }

    @Override
    protected int calculateOutput(int m) {
        if (level == 0) return 0;
        if (level == 1) return m;
        if (level == 2) return 2 * m;
        return (2 * m) + receivedLifestyle;
    }

    public void setLifestyle(int l) { 
        this.receivedLifestyle = l; 
    }

}