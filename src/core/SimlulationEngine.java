package core;

import models.Cell;
import models.services.ServiceProvider;
import algorithms.BFSUtilityDistributor;
import models.utilities.UtilityProvider;
import models.zones.Zone;
import models.zones.Housing;
import models.zones.Industrial;
import models.zones.Commercial;

public class SimlulationEngine {
    private CityGrid cityGrid;
    private int totalTicks;


    private int currentPopulationPool = 0;
    private int currentGoodsPool = 0;
    private int currentLifestylePool = 0;

    public SimlulationEngine(CityGrid cityGrid, int totalTicks) {
        this.cityGrid = cityGrid;
        this.totalTicks = totalTicks;
    }

    public void startSimulation() {
        int rows = cityGrid.getRows();
        int cols = cityGrid.getCols();

        for (int tick = 1; tick <= totalTicks; tick++) {
            System.out.println("Tick " + tick);


            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell cell = cityGrid.getCell(i, j);
                    if (cell != null) {
                        cell.resetServices();
                        if (cell instanceof Zone) {
                            ((Zone) cell).resetTurn();
                        }
                    }
                }
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell cell = cityGrid.getCell(i, j);
                    if (cell instanceof ServiceProvider) {
                        ((ServiceProvider) cell).distributeService(cityGrid);
                    }
                }
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell cell = cityGrid.getCell(i, j);
                    if (cell instanceof UtilityProvider) {
                        BFSUtilityDistributor.distribute(cityGrid, (UtilityProvider) cell);
                    }
                }
            }

            int industrialCount = 0, commercialCount = 0, housingCount = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell cell = cityGrid.getCell(i, j);
                    if (cell instanceof Housing) housingCount++;
                    else if (cell instanceof Industrial) industrialCount++;
                    else if (cell instanceof Commercial) commercialCount++;
                }
            }


            int totalZones = industrialCount + commercialCount;
            if (currentPopulationPool > 0 && totalZones > 0) {
                int popPerZone = currentPopulationPool / totalZones;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        Cell cell = cityGrid.getCell(i, j);
                        if (cell instanceof Industrial || cell instanceof Commercial) {
                            ((Zone) cell).addReceivedPopulation(popPerZone);
                            System.out.println(cell.getClass().getSimpleName() + " at (" + i + "," + j + ") received " + popPerZone + " population");
                        }
                    }
                }
            }


            if (currentGoodsPool > 0 && commercialCount > 0) {
                int goodsPerZone = currentGoodsPool / commercialCount;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        Cell cell = cityGrid.getCell(i, j);
                        if (cell instanceof Commercial) {
                            ((Zone) cell).addReceivedGoods(goodsPerZone);
                            System.out.println("Commercial at (" + i + "," + j + ") received " + goodsPerZone + " goods");
                        }
                    }
                }
            }


            if (currentLifestylePool > 0 && housingCount > 0) {
                int lifestylePerZone = currentLifestylePool / housingCount;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        Cell cell = cityGrid.getCell(i, j);
                        if (cell instanceof Housing) {
                            ((Zone) cell).addReceivedLifestyle(lifestylePerZone);
                            System.out.println("House at (" + i + "," + j + ") received " + lifestylePerZone + " lifestyle");
                        }
                    }
                }
            }


            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell cell = cityGrid.getCell(i, j);
                    if (cell instanceof Zone) {
                        ((Zone) cell).updateTick();
                    }
                }
            }


            currentPopulationPool = 0;
            currentGoodsPool = 0;
            currentLifestylePool = 0;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell cell = cityGrid.getCell(i, j);
                    if (cell instanceof Zone) {
                        Zone zone = (Zone) cell;
                        if (zone instanceof Housing) {
                            currentPopulationPool += zone.getOutput();
                        } else if (zone instanceof Industrial) {
                            currentGoodsPool += zone.getOutput();
                        } else if (zone instanceof Commercial) {
                            currentLifestylePool += zone.getOutput();
                        }
                    }
                }
            }
        }
    }
}