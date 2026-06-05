package models.zones;

import models.Cell;

public abstract class Zone extends Cell {
    protected int level = 0;
    protected int output = 0;

    protected int receivedElectricity = 0;
    protected int receivedWater = 0;
    protected int receivedInternet = 0;
    protected int receivedPopulation = 0;
    protected int receivedGoods = 0;
    protected int receivedLifestyle = 0;

    public void addReceivedPopulation(int pop) { this.receivedPopulation += pop; }
    public void addReceivedGoods(int goods) { this.receivedGoods += goods; }
    public void addReceivedLifestyle(int lifestyle) { this.receivedLifestyle += lifestyle; }


    protected boolean hasSecurity = false;
    protected boolean hasHealth = false;
    protected boolean hasEducation = false;

    public int getReceivedElectricity() { return receivedElectricity; }
    public int getReceivedWater() { return receivedWater; }
    public int getReceivedInternet() { return receivedInternet; }

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
        // Cell üst sınıfından o tick gelen güncel servis durumlarını local boolean'lara senkronize et
        this.hasSecurity = this.isHasSecurity();
        this.hasHealth = this.isHasHealth();
        this.hasEducation = this.isHasEducation();

        int m = Integer.MAX_VALUE;
        if (needsElectricity()) m = Math.min(m, receivedElectricity);
        if (needsWater()) m = Math.min(m, receivedWater);
        if (needsInternet()) m = Math.min(m, receivedInternet);

        if (m == Integer.MAX_VALUE) m = 0;

        // Kural: Eğer bölge hiçbir altyapıyı alamadıysa (veya alması gerekenler 0 ise) anında 0'a düşer
        boolean totallyCut = (needsElectricity() && receivedElectricity == 0) || (needsWater() && receivedWater == 0) || (needsInternet() && receivedInternet == 0);

        int oldLevel = level;

        if (totallyCut) {
            level = 0;
        } else {
            if (m > 0 && canLevelUpTo(level + 1) && level < 3) {
                level++;
            } else if (!canLevelUpTo(level) && level > 0) {
                level--;
            }
        }

        String typeName = this instanceof Housing ? "House" : (this instanceof Industrial ? "Industrial" : "Commercial");



        output = calculateOutput(m);

        if (output > 0) {
            String resourceName = this instanceof Housing ? "population" : (this instanceof Industrial ? "goods" : "lifestyle");
            System.out.println(typeName + " at (" + x + "," + y + ") generated " + output + " " + resourceName);
        }

        if (level > oldLevel) {
            System.out.println(typeName + " at (" + x + "," + y + ") levels up from " + oldLevel + " to " + level);
        } else if (level < oldLevel) {
            System.out.println(typeName + " at (" + x + "," + y + ") levels down from " + oldLevel + " to " + level);
        }
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
}