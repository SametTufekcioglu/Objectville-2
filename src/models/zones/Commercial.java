package models.zones;

public class Commercial extends Zone {
    protected int receivedPopulation = 0;
    protected int receivedGoods = 0;

    public Commercial(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'C';
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
    protected boolean canReachLevel1() {
        return receivedPopulation > 0 && receivedGoods > 0;
    }

    @Override
    protected boolean canLevelUpTo(int targetLevel) {
        if (targetLevel == 1) {
            return canReachLevel1();
        }
        if (targetLevel == 2) {
            return isHasSecurity();
        }
        if (targetLevel == 3) {
            return isHasSecurity() && receivedPopulation > 0 && receivedGoods > 0;
        }
        return false;
    }

    @Override
    protected int calculateOutput(int m) {
        if (level == 0) {
            return 0;
        }
        if (level == 1) {
            return m;
        }
        if (level == 2) {
            return 2 * m;
        }
        return (2 * m) + Math.min(receivedPopulation, receivedGoods);
    }

    public void setPopulation(int p) {
        receivedPopulation = p;
    }

    public void setGoods(int g) {
        receivedGoods = g;
    }

    @Override
    public void resetTurn() {
        super.resetTurn();
        receivedPopulation = 0;
        receivedGoods = 0;
    }
}