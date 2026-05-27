package models.utilities;

public class InternetHub extends UtilityProvider {

    public InternetHub(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'T';
    }
}