package core;

import models.Cell;

public class CityGrid {
    protected Cell[][] grid;

    public CityGrid(int rows, int cols){
        grid = new Cell[rows][cols];
    }

    public void addCell(int x, int y, Cell cell){
        grid[x][y] = cell;

    }
    public Cell getCell(int x, int y){
        if(x<0 || y<0 || x>= getRows() || y>= getCols()){
            return null;
        }return grid[x][y];
    }

    public int getRows(){
        return grid.length;
    }

    public int getCols(){
        return grid[0].length;
    }


}
