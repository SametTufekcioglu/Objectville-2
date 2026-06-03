package models.zones;

import models.Cell;

public abstract class Zone extends Cell {
    protected int level = 0;
    protected int output = 0;

    protected int receivedElectricity = 0;
    protected int receivedWater = 0;
    protected int receivedInternet = 0;
    // DEV 3 WRITED
    protected int receivedPopulation = 0;
    protected int receivedGoods = 0;
    protected int receivedLifestyle = 0;

    public void addReceivedPopulation(int pop) { this.receivedPopulation += pop; }
    public void addReceivedGoods(int goods) { this.receivedGoods += goods; }
    public void addReceivedLifestyle(int lifestyle) { this.receivedLifestyle += lifestyle; }
    // DEV 3 WRITED

    protected boolean hasSecurity = false;
    protected boolean hasHealth = false;
    protected boolean hasEducation = false;

    // DEV 3 WRITED
    public boolean isSecurity() { return hasSecurity; }
    public boolean isHealth() { return hasHealth; }
    public boolean isEducation() { return hasEducation; }
    public int getReceivedElectricity() { return receivedElectricity; }
    public int getReceivedWater() { return receivedWater; }
    public int getReceivedInternet() { return receivedInternet; }
    public int getReceivedPopulation() { return receivedPopulation; }
    public int getReceivedGoods() { return receivedGoods; }
    public int getReceivedLifestyle() { return receivedLifestyle; }
    // DEV 3 WRITED

    public Zone(int x, int y) {
        super(x, y);
    }

    public abstract boolean needsElectricity();
    public abstract boolean needsWater();
    public abstract boolean needsInternet();
    protected abstract boolean canLevelUpTo(int targetLevel);
    protected abstract int calculateOutput(int m);

    public void resetTurn() {
        this.receivedElectricity = 0;
        this.receivedWater = 0;
        this.receivedInternet = 0;
        this.receivedPopulation = 0;    // DEV 3
        this.receivedGoods = 0;         // DEV 3
        this.receivedLifestyle = 0;     // DEV 3
        this.hasSecurity = false;
        this.hasHealth = false;
        this.hasEducation = false;
    }

    public void updateTick() {
        int m = Integer.MAX_VALUE;
        
        if (needsElectricity()) {
            m = Math.min(m, receivedElectricity);
        }
        if (needsWater()) {
            m = Math.min(m, receivedWater);
        }
        if (needsInternet()) {
            m = Math.min(m, receivedInternet);
        }

        if (m == Integer.MAX_VALUE) {
            m = 0;
        }

        if (m == 0) {
            level = 0; 
        } else {
            if (canLevelUpTo(level + 1) && level < 3) {
                level++;
            } else if (!canLevelUpTo(level) && level > 0) {
                level--;
            }
        }
        output = calculateOutput(m);
    }

    public int getLevel() { 
        return level; 
    }
    
    public int getOutput() { 
        return output; 
    }
    
    public void setElectricity(int e) { 
        this.receivedElectricity = e; 
    }
    
    public void setWater(int w) { 
        this.receivedWater = w; 
    }
    
    public void setInternet(int i) { 
        this.receivedInternet = i; 
    }
    
    public void setSecurity(boolean s) { 
        this.hasSecurity = s; 
    }
    
    public void setHealth(boolean h) { 
        this.hasHealth = h; 
    }
    
    public void setEducation(boolean e) { 
        this.hasEducation = e; 
    }
}