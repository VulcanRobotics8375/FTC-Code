/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Pathfinding;

import org.opencv.core.Point;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class Board {

    private ArrayList<Tile> open;
    private ArrayList<Tile> closed;

    private Tile start;
    private Tile dst;

    private int startX;
    private int startY;
    private int dstX;
    private int dstY;

    public Board(Tile start, Tile dst) {
        this.start = tiles.get((int) start.getPos().y).get((int) start.getPos().x);
        this.dst = tiles.get((int) dst.getPos().y).get((int) dst.getPos().x);

        this.startX = (int) this.start.getPos().x;
        this.startY = (int) this.start.getPos().y;
        this.dstX = (int) this.dst.getPos().x;
        this.dstY = (int) this.dst.getPos().y;


    }

    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();

    public void setBoard() {

        ArrayList<ArrayList<Tile>> map = new ArrayList<>();

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

        tiles.addAll(map);

    }
}
