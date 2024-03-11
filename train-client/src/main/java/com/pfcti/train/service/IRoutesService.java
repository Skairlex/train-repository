package com.pfcti.train.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.pfcti.train.entity.Location;
import com.pfcti.train.entity.Route;

public interface IRoutesService {

    void addLocation(String name);

    void addRoute(String originName, String destinationName, int distance);

    int calculateShortestRoute(String originName, String destinationName);

    int calculateRoute(String originName, String destinationName);

    int countTripsWithMaxStops(String startLocation, String endLocation, int maxStops);

    int countTripsWithExactStops(String startLocation, String endLocation, int exactStops);

    int countRoutesWithDistanceLessThan(String startLocation, String endLocation, int maxDistance);

    Map<String, Location> getLocations();

    Set<Route> getRoutes();

    Set<String> getLocationNames();

    List<String> getRouteInfo();
}
