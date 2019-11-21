/*
 * Copyright (c) 2019 Vulcan Robotics FTC Team 8375. All Rights Reserved.
 */

package org.firstinspires.ftc.team8375.Pathfinding;

import org.firstinspires.ftc.team8375.Autonomous.VulcanPipeline;
import org.opencv.core.Point;

public class opModePathfind extends VulcanPipeline {

    private Board board;

    @Override
    public void runOpMode() {

        board = new Board(new Tile(new Point(0, 0), Tile.TileType.FLOOR), new Tile(new Point(0, 6), Tile.TileType.FLOOR));

        while(opModeIsActive()) {

        }

    }
}
