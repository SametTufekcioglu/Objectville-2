package models.zones;

import models.Cell;

public abstract class Zone extends Cell {
    protected int level = 0;
    protected int output = 0;

    protected int receivedElectricity = 0;
    protected int receivedWater = 0;
    protected int receivedInternet = 0;

    public Zone(int x, int y) {
        super(x, y);
    }

    public abstract boolean needsElectricity();
    public abstract boolean needsWater();
    public abstract boolean needsInternet();

    protected abstract boolean canReachLevel1();
    protected abstract boolean canLevelUpTo(int targetLevel);
    protected abstract int calculateOutput(int m);

    public void resetTurn() {
        receivedElectricity = 0;
        receivedWater = 0;
        receivedInternet = 0;
        resetServices();
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
            output = 0;
            return;
        }

        if (level == 0) {
            if (canReachLevel1()) {
                level = 1;
            }
        } else if (level == 1) {
            if (canLevelUpTo(2)) {
                level = 2;
            } else if (!canLevelUpTo(1)) {
                level = 0;
            }
        } else if (level == 2) {
            if (canLevelUpTo(3)) {
                level = 3;
            } else if (!canLevelUpTo(2)) {
                level = 1;
            }
        } else if (level == 3) {
            if (!canLevelUpTo(3)) {
                level = 2;
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
        receivedElectricity = e;
    }

    public void setWater(int w) {
        receivedWater = w;
    }

    public void setInternet(int i) {
        receivedInternet = i;
    }
}