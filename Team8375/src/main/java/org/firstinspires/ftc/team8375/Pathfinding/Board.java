/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Pathfinding;

import org.opencv.core.Point;

import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<Tile>> tiles;
    private ArrayList<Tile> linearTiles;

    private pathFinder finder;

    public Board() {
        linearTiles = new ArrayList<Tile>();
        setBoard();

        finder = new pathFinder(linearTiles);
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return this.tiles;
    }

    public void setBoard() {

        ArrayList<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();

        //turns the 2d board int[][] array into an ArrayList
        for(int i = 0; i < 11; i++) {
            map.add(new ArrayList<Tile>());

            for(int k = 0; k < 11; k++) {
                if(BoardArray.getArray(i, k) == 1) {

                    map.get(i).add(new Tile(new Point(k, i), Tile.TileType.FLOOR));

                } else if(BoardArray.getArray(i, k) == 0) {
                    map.get(i).add(new Tile(new Point(k, i), Tile.TileType.WALL));
                }
            }
        }

        tiles = map;

        for (ArrayList<Tile> list : map) {
            linearTiles.addAll(list);
        }

        for(Tile tile : linearTiles) {
            ArrayList<Tile> neighbors = new ArrayList<Tile>();

            if (tile.getPos().x > 0)
                neighbors.add(map.get((int) tile.getPos().y).get(
                        (int) tile.getPos().x - 1));
            if (tile.getPos().x < 12 - 2)
                neighbors.add(map.get((int)tile.getPos().y).get(
                        (int) tile.getPos().x + 1));

            if (tile.getPos().y > 0)
                neighbors.add(map.get((int) tile.getPos().y - 1).get(
                        (int) tile.getPos().x));
            if (tile.getPos().y < 12 - 2)
                neighbors.add(map.get((int) tile.getPos().y + 1).get(
                        (int) tile.getPos().x));

            tile.setNeighbors(neighbors);


        }

    }
    public void run() {
        new Board();
    }

}
