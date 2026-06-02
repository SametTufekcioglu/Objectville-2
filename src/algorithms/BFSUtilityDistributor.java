package algorithms;

import core.CityGrid;
import models.Cell;
import models.Road;
import models.utilities.UtilityProvider;
import models.zones.Zone;

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

            Cell currentCell = grid.getCell(cx, cy);

            if (currentCell instanceof Zone) {
                Zone zone = (Zone) currentCell;
                int demand = Math.max(zone.getOutput(), 1);
                int absorbed = Math.min(demand, remaining);
                remaining -= absorbed;

                if (provider.getSymbol() == 'P') zone.setElectricity(absorbed);          //P = Electricity
                else if (provider.getSymbol() == 'W') zone.setWater(absorbed);           //W = Water
                else if (provider.getSymbol() == 'T') zone.setInternet(absorbed);        //T = Internet
            }

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (nx < 0 || ny < 0 || nx >= grid.getRows() || ny >= grid.getCols()) continue;
                if (visited[nx][ny]) continue;

                Cell neighbor = grid.getCell(nx, ny);
                if (neighbor instanceof Road || neighbor instanceof Zone) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }
    }
}