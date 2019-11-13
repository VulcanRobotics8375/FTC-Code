/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Pathfinding;

import org.opencv.core.Point;

import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<Tile>> tiles;


    public Board(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return this.tiles;
    }

    public void setBoard() {

        ArrayList<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();

        map.add(new ArrayList<Tile>());
        map.get(0).add(new Tile(new Point(0, 0), Tile.TileType.WALL));
        Board board = new Board(map);

    }

}
