package algorithms;

import core.CityGrid;
import models.Cell;
import models.Road;
import models.utilities.UtilityProvider;
import models.zones.Zone;
import models.zones.Housing;
import models.zones.Industrial;
import models.zones.Commercial;

import java.util.LinkedList;
import java.util.Queue;

public class BFSUtilityDistributor {

    public static void distribute(CityGrid grid, UtilityProvider provider) {
        int startX = provider.getX();
        int startY = provider.getY();
        int remaining = provider.getCapacity();

        boolean[][] visited = new boolean[grid.getRows()][grid.getCols()];
        Queue<int[]> queue = new LinkedList<>();

        visited[startX][startY] = true;
        queue.add(new int[]{startX, startY});

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!queue.isEmpty() && remaining > 0) {
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (nx < 0 || ny < 0 || nx >= grid.getRows() || ny >= grid.getCols()) continue;
                if (visited[nx][ny]) continue;

                Cell neighbor = grid.getCell(nx, ny);
                if (neighbor instanceof Road || neighbor instanceof Zone) {
                    visited[nx][ny] = true;

                    if (neighbor instanceof Zone) {
                        Zone zone = (Zone) neighbor;

                        int maxDemand = Math.max(zone.getOutput(), 1);

                        int alreadyReceived = 0;
                        if (provider.getSymbol() == 'P') alreadyReceived = zone.getReceivedElectricity();
                        else if (provider.getSymbol() == 'W') alreadyReceived = zone.getReceivedWater();
                        else if (provider.getSymbol() == 'T') alreadyReceived = zone.getReceivedInternet();


                        int netDemand = Math.max(0, maxDemand - alreadyReceived);

                        int absorbed = Math.min(netDemand, remaining);
                        remaining -= absorbed;

                        if (absorbed > 0) {
                            String typeName = zone instanceof Housing ? "House" : (zone instanceof Industrial ? "Industrial" : "Commercial");
                            if (provider.getSymbol() == 'P') {
                                zone.setElectricity(alreadyReceived + absorbed);
                                System.out.println(typeName + " at (" + nx + "," + ny + ") received " + absorbed + " electricity");
                            } else if (provider.getSymbol() == 'W') {
                                zone.setWater(alreadyReceived + absorbed);
                                System.out.println(typeName + " at (" + nx + "," + ny + ") received " + absorbed + " water");
                            } else if (provider.getSymbol() == 'T') {
                                zone.setInternet(alreadyReceived + absorbed);
                                System.out.println(typeName + " at (" + nx + "," + ny + ") received " + absorbed + " internet");
                            }
                        }
                    }
                    queue.add(new int[]{nx, ny});
                }
                if (remaining <= 0) break;
            }
        }
    }
}