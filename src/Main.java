import core.CityGrid;
import io.MapParser;
import models.Cell;
import core.SimlulationEngine;

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

        if(grid!=null){
            SimlulationEngine engine = new SimlulationEngine(grid,ticks);
            engine.startSimulation();
        }
        else{
            System.out.println("Error: CityGrid could not be loaded!");
        }
    }
}