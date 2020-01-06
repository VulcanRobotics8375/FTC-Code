/*
 * Copyright (c) 2020 Vulcan Robotics FTC Team 8375. Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
