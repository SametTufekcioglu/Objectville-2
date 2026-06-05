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

            int workZoneCount = industrialCount + commercialCount;

            int popPerZone = 0;
            int goodsPerCommercial = 0;
            int lifestylePerHouse = 0;

            if (currentPopulationPool > 0 && workZoneCount > 0) {
                popPerZone = currentPopulationPool / workZoneCount;
            }

            if (currentGoodsPool > 0 && commercialCount > 0) {
                goodsPerCommercial = currentGoodsPool / commercialCount;
            }

            if (currentLifestylePool > 0 && housingCount > 0) {
                lifestylePerHouse = currentLifestylePool / housingCount;
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell cell = cityGrid.getCell(i, j);

                    if (cell instanceof Commercial) {
                        if (popPerZone > 0) {
                            ((Zone) cell).addReceivedPopulation(popPerZone);
                            System.out.println("Commercial at (" + i + "," + j + ") received " + popPerZone + " population");
                        }

                        if (goodsPerCommercial > 0) {
                            ((Zone) cell).addReceivedGoods(goodsPerCommercial);
                            System.out.println("Commercial at (" + i + "," + j + ") received " + goodsPerCommercial + " goods");
                        }
                    }

                    else if (cell instanceof Industrial) {
                        if (popPerZone > 0) {
                            ((Zone) cell).addReceivedPopulation(popPerZone);
                            System.out.println("Industrial at (" + i + "," + j + ") received " + popPerZone + " population");
                        }
                    }

                    else if (cell instanceof Housing) {
                        if (lifestylePerHouse > 0) {
                            ((Zone) cell).addReceivedLifestyle(lifestylePerHouse);
                            System.out.println("House at (" + i + "," + j + ") received " + lifestylePerHouse + " lifestyle");
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