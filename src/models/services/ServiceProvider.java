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

               // int distance = Math.abs(this.getX()-i)+Math.abs(this.getY()-j);
              //  int distance = Math.max(Math.abs(this.getX() - i), Math.abs(this.getY() - j));

                double distance = Math.sqrt(Math.pow(this.getX() - i, 2) + Math.pow(this.getY() - j, 2));

                if(distance<=this.radius){
                    boolean isServiceApplied = applyService(target);
                    //applyService(target);
                    if(isServiceApplied){ String targetTypeName = "";
                        if (target instanceof Housing) targetTypeName = "House";
                        else if (target instanceof Industrial) targetTypeName = "Industrial";
                        else if (target instanceof Commercial) targetTypeName = "Commercial";

                        String serviceName = "";
                        if (this instanceof PoliceStation) serviceName = "security";
                        else if (this instanceof Hospital) serviceName = "health";
                            //sıkıntı
                        else if (this instanceof School) serviceName = "education";

                        if (!targetTypeName.isEmpty()) {
                            System.out.println(targetTypeName + " at (" + i + "," + j + ") received " + serviceName + " service");
                        }}

                    // Örnek çıktıdaki isimlendirmelerle log basalım:

                }
            }
        }
    }

    //void -> boolean oldu o yüzden applyService hepsinde update edilmeli
    protected abstract boolean applyService(Cell target);
}
