package models;

public class Empty extends Cell{
    public Empty(int x, int y) {
        super(x, y);
    }
    public char getSymbol(){
        return 'E';
    }

}
