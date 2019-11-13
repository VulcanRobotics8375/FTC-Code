/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Pathfinding;

import java.util.ArrayList;
import java.util.Collections;

public class pathFinder {

    public ArrayList<Tile> allTiles;
    private ArrayList<Tile> closed;
    private ArrayList<Tile> open;
    private Board board;

    public pathFinder(ArrayList<Tile> all) {
        board = new Board(allTiles);

        this.allTiles = all;
        this.closed = new ArrayList<Tile>();
        this.open = new ArrayList<Tile>();

    }

    public Tile getBest() {

        Tile tmp = open.get(open.size() - 1);
        for (int i = open.size() - 1; i >= 1; i--) {

            if (open.get(i).getF() < tmp.getF())
                tmp = open.get(i);

        }

        open.remove(tmp);
        return tmp;

    }

    public ArrayList<Tile> getPath(Tile dst, Tile start) {
        closed.clear();
        open.clear();

        ArrayList<Tile> path = new ArrayList<Tile>();

        Tile currentStep = start;
        open.add(0, currentStep);

        float G = 0f;

        int depth = 0;
        int depthMax = 1000;

        while (true) {

            /*
             * Limit the amount of loops for better performance
             */
            if (depth >= depthMax) {
                return null;
            }

            /*
             * If the tile which is currently checked (currentStep) is the
             * destination tile search can be stopped (break). The same goes for
             * an empty list of potential tiles suited for path (openlist).
             */
            if (currentStep.equals(dst)) {
                break;
            } else if (open.isEmpty()) {
                break;
            } else {

                /*
                 * Get tile with lowest F cost from openlist.
                 */
                currentStep = getBest();

                /*
                 * Add to closed list (tile already part of path).
                 */
                closed.add(currentStep);

                /*
                 * Check all neighbors of the currentstep.
                 */
                for (int i = 0; i < currentStep.getNeighbors().size(); i++) {

                    Tile neighbor = currentStep.getNeighbors().get(i);

                    if (neighbor.equals(dst)) {
                        neighbor.setParent(currentStep);
                        currentStep = neighbor;
                        break;
                    }

                    if (closed.contains(neighbor))
                        continue;

                    /*
                     * Get the moving costs from the currentstep to the
                     * neighbor.
                     */
                    G = neighbor.moveCost(currentStep);

                    if (!open.contains(neighbor)) {
                        open.add(neighbor);
                    } else if (G >= neighbor.G) {
                        continue;
                    }

                    neighbor.parent = currentStep;
                    neighbor.G = G;
                    neighbor.calcCostH(dst);
                    neighbor.calcCostF();
                }

            }
            depth += 1;
        }

        /*
         * Build the path reversly iterating over the tiles by accessing their
         * parent tile.
         */
        Tile startTmp = dst;

        while (!start.equals(startTmp)) {

            if (startTmp.getParent() == null)
                break;

            startTmp = startTmp.getParent();
            if (path.contains(startTmp)) {
                return null;
            }
            path.add(startTmp);
        }

        /*
         * Reverse to get the path from start to dst.
         */
        Collections.reverse(path);

        /*
         * If no path is found return null.
         */
        if (path.isEmpty())
            return null;

        return path;
    }


}
