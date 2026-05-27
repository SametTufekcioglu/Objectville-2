package models.utilities;

public class PowerPlant extends UtilityProvider {

    public PowerPlant(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'P';
    }
}