package com.pfcti.train.entity;

public class RouteSearchState {
    private final Location location;
    private final int distance;

    public RouteSearchState(Location location, int distance) {
        this.location = location;
        this.distance = distance;
    }

    public Location getLocation() {
        return location;
    }

    public int getDistance() {
        return distance;
    }
}
