import core.CityGrid;
import io.MapParser;
import models.Cell;

public class Main {
    public static void main(String[] args) {
        if(args.length<2){
            System.out.println("Error"); //error açıklamasını düzenlicem
            return;
        }

        String mapFilePath = args[0];
        int ticks = Integer.parseInt(args[1]);

        System.out.println("Map is loading: " + mapFilePath);
        System.out.println("Tick value: " + ticks);
        System.out.println("-----------------------");

        MapParser parser = new MapParser();
        parser.loadData(mapFilePath);
        CityGrid grid = parser.getCityGrid();

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