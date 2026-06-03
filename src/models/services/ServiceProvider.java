package models.services;

import core.CityGrid;
import models.Cell;
import models.zones.Housing;
import models.zones.Industrial;
import models.zones.Commercial;

public abstract class ServiceProvider extends Cell {
    private int radius;

    public ServiceProvider(int x,int y,int radius){
        super(x,y);
        this.radius=radius;
    }

    public int getRadius(){
        return radius;
    }
    public void distributeService(CityGrid cityGrid){
        int rows = cityGrid.getRows();
        int cols = cityGrid.getCols();
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                Cell target = cityGrid.getCell(i,j);
                if(target==null){
                    continue;
                }

                int distance = Math.abs(this.getX()-i)+Math.abs(this.getY()-j);

                if(distance<=this.radius){
                    applyService(target);

                    // Örnek çıktıdaki isimlendirmelerle log basalım:
                    String targetTypeName = "";
                    if (target instanceof Housing) targetTypeName = "House";
                    else if (target instanceof Industrial) targetTypeName = "Industrial";
                    else if (target instanceof Commercial) targetTypeName = "Commercial";

                    String serviceName = "";
                    if (this instanceof PoliceStation) serviceName = "security";
                    else if (this instanceof Hospital) serviceName = "health";
                    else if (this instanceof School) serviceName = "education";

                    if (!targetTypeName.isEmpty()) {
                        System.out.println(targetTypeName + " at (" + i + "," + j + ") received " + serviceName + " service");
                    }
                }
            }
        }
    }
    protected abstract void applyService(Cell target);
}
