package com.pfcti.train.service;

public interface IRoutesService {

    void addLocation(String name);

    void addRoute(String originName, String destinationName, int distance);

    int calculateShortestRoute(String originName, String destinationName);

    int calculateRoute(String originName, String destinationName);

    int countTripsWithMaxStops(String startLocation, String endLocation, int maxStops);

    int countTripsWithExactStops(String startLocation, String endLocation, int exactStops);
}
