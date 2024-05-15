package seng201.team35.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Track {
    private List<Point> waypoints;

    public Track() {
        waypoints = new ArrayList<>();
        initializeWaypoints();
    }

    private void initializeWaypoints() {
        waypoints.add(new Point(80, 50));
        waypoints.add(new Point(80, 40));
        waypoints.add(new Point(80, 30));
        waypoints.add(new Point(80, 20));
        waypoints.add(new Point(80, 10));
        waypoints.add(new Point(70, 10));
        waypoints.add(new Point(60, 10));
        waypoints.add(new Point(60, 20));
        waypoints.add(new Point(60, 30));
        waypoints.add(new Point(60, 40));
        waypoints.add(new Point(50, 40));
        waypoints.add(new Point(40, 40));
        waypoints.add(new Point(30, 40));
    }

    public List<Point> getWaypoints() {
        return waypoints;
    }
}
