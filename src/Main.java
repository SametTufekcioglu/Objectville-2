import core.CityGrid;
import io.MapParser;
import models.Cell;

public class Main {
    public static void main(String[] args) {
        if(args.length<2){
            System.out.println("Error! Usage: java -jar ObjectVilleGame.jar <mapFile.txt> <countOfTicks>"); //error açıklamasını düzenlicem
            return;
        }
        int ticks = 0;
        try{
            ticks = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            System.out.println("Error: Tick count must be an integer! ");
            return;
        }
        String mapFilePath = args[0];


        System.out.println("Map is loading: " + mapFilePath);
        System.out.println("Tick value: " + ticks);
        System.out.println("-----------------------");

        MapParser parser = new MapParser();
        parser.loadData(mapFilePath);
        CityGrid grid = parser.getCityGrid();
        // TODO: 4. geliştiricinin Simulation sınıfı buraya eklenecek ve ticks değişkeni ona yollanacak

        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                Cell currentCell = grid.getCell(i, j);
                if(currentCell != null){
                    System.out.print(currentCell.getSymbol());
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}