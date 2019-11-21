/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Pathfinding;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collections;

import static org.firstinspires.ftc.team8375.Pathfinding.Tile.TileType.FLOOR;

public class pathFinder {

    private ArrayList<ArrayList<Tile>> map = new ArrayList<>();
    private ArrayList<Tile> path;
    private ArrayList<Tile> open = new ArrayList<>();
    private ArrayList<Tile> closed = new ArrayList<>();
    private ArrayList<Tile> neighbors = new ArrayList<>();

    float G = 0f;

    public Tile getBest() {

        Tile tmp = open.get(open.size() - 1);
        for (int i = open.size() - 1; i >= 1; i--) {

            if (open.get(i).getF() < tmp.getF())
                tmp = open.get(i);

        }

        open.remove(tmp);
        return tmp;

    }


    public ArrayList<Tile> getPath(Tile start, Tile dst) {
        open.clear();
        closed.clear();

        open.add(0, start);

        Tile currentNode;

        while (true) {

            currentNode = getBest();
            open.remove(currentNode);
            closed.add(currentNode);

            if(currentNode == dst)
                break;

            setTileNeighbors(currentNode);


            for (Tile neighbor : neighbors) {
                if(closed.contains(neighbor))
                    continue;

                G = neighbor.moveCost(currentNode);

                if (!open.contains(neighbor)) {

                    open.add(neighbor);
                    neighbor.setParent(currentNode);
                    neighbor.setG(this.G);
                    neighbor.calcCostH(dst);
                    neighbor.calcCostF();

                }

            }

        }

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


    public void setBoard() {

        //turns the 2d board int[][] array into an ArrayList
        for(int i = 0; i < 11; i++) {
            map.add(new ArrayList<Tile>());

            for(int k = 0; k < 11; k++) {
                if(BoardArray.getArray(i, k) == 1) {

                    map.get(i).add(new Tile(new Point(k, i), FLOOR));

                } else if(BoardArray.getArray(i, k) == 0) {
                    map.get(i).add(new Tile(new Point(k, i), Tile.TileType.WALL));
                }
            }
        }

    }

    private void setTileNeighbors(Tile parent) {

        Tile parentBoardPos;

        for (int i = -1; i < 1; i++) {
            for (int k = -1; k < 1; k++) {
                if(i != 0 && k != 0) {
                    parentBoardPos = map.get(((int) parent.getPos().y) + i).get(((int) parent.getPos().x) + k);

                    if (!parentBoardPos.isWall() && !neighbors.contains(parentBoardPos)) {
                        neighbors.add(parentBoardPos);
                    }
                }
            }
        }
    }

}
