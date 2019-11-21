/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Pathfinding;


import org.opencv.core.Point;

import java.util.ArrayList;

/*
* This class is adapted from https://github.com/helfsoft/astar/blob/master/src/com/helfsoft/astar/Tile.java
*/

public class Tile {

    public enum TileType {
        FLOOR, WALL
    }

    private Point pos;
    private Point realPos;
    private Point center;

    private TileType type;

    public Tile parent;

    private ArrayList<Tile> neighbors;
    private ArrayList<Tile> allNeighbors;

    public float G;
    private float F;
    private float H;


    public Tile(Point pos, TileType type) {
        this.pos = pos;

        this.realPos = new Point((int) (pos.x),
                (int) (pos.y));

        this.center = new Point((int) (realPos.x / 2f),
                (int) (realPos.y / 2f));

        this.neighbors = new ArrayList<Tile>();
        this.allNeighbors = new ArrayList<Tile>();
        this.type = type;

        F = 0;
        G = 0f;
        H = 0f;
    }

    public void calcCostH(Tile dst) {

        float dx = (float) Math.abs(pos.x - dst.pos.x);
        float dy = (float) Math.abs(pos.y - dst.pos.y);
        H = Math.max(dx, dy);

        //Heuristic for diagonal movement
//        H = 25 * (dx + dy) + (5 - 2 * 25) * Math.min(dx, dy);
    }

    public void calcCostF() {

        F = G + H;

    }
    public float moveCost(Tile from) {

        float cost = 10f;

        if (this.isWall())
            cost *= 10f;

        return cost + from.G;

    }

    public static TileType typeFromId(int id) {

        switch (id) {
            case 0:
                return TileType.FLOOR;
            case 2:
                return TileType.WALL;
            default:
                return TileType.WALL;
        }

    }

    @Override
    public boolean equals(Object obj) {
        Tile other = null;
        if (obj instanceof Tile)
            other = (Tile) obj;
        else
            return false;

        if (other.pos.equals(this.pos))
            return true;
        return false;

    }

    public Tile getParent() {
        return parent;
    }

    public void setParent(Tile parent) {
        this.parent = parent;
    }

    public ArrayList<Tile> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Tile> neighbors) {
        this.neighbors = neighbors;
    }

    public ArrayList<Tile> getAllNeighbors() {
        return allNeighbors;
    }

    public void setAllNeighbors(ArrayList<Tile> allNeighbors) {
        this.allNeighbors = allNeighbors;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public Point getRealPos() {
        return realPos;
    }

    public void setRealPos(Point realPos) {
        this.realPos = realPos;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public float getG() {
        return G;
    }

    public void setG(float g) {
        G = g;
    }

    public float getF() {
        return F;
    }

    public void setF(float f) {
        F = f;
    }

    public float getH() {
        return H;
    }

    public void setH(float h) {
        H = h;
    }

    public boolean isLeftNeighbor(Tile from) {

        return (this.pos.y == from.pos.y) && (this.pos.x + 1 == from.pos.x);

    }

    public boolean isRightNeighbor(Tile from) {

        return (this.pos.y == from.pos.y) && (this.pos.x - 1 == from.pos.x);

    }

    public boolean isUpNeighbor(Tile from) {

        return (this.pos.y - 1 == from.pos.y) && (this.pos.x == from.pos.x);

    }

    public boolean isDownNeighbor(Tile from) {

        return (this.pos.y + 1 == from.pos.y) && (this.pos.x == from.pos.x);

    }



    public boolean isWall() {

        return this.type == TileType.WALL;

    }


}
