package com.pfcti.train.service;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolicitedTest {

    private RoutesService routesService;

    @BeforeEach
    void setUp() {
        routesService = new RoutesService();
        routesService.addLocation("A");
        routesService.addLocation("B");
        routesService.addLocation("C");
        routesService.addLocation("D");
        routesService.addLocation("E");
        routesService.addRoute("A", "B", 5);
        routesService.addRoute("B", "C", 4);
        routesService.addRoute("C", "D", 8);
        routesService.addRoute("D", "C", 8);
        routesService.addRoute("D", "E", 6);
        routesService.addRoute("A", "D", 5);
        routesService.addRoute("C", "E", 2);
        routesService.addRoute("E", "B", 3);
        routesService.addRoute("A", "E", 7);
    }

    @Test
    void calculateDistanceRouteABC() {
        List<String> routeList = Arrays.asList("A", "B", "C");
        int distance = (int)routesService.calculateRoutesOfList(routeList);
        Assertions.assertEquals(9, distance);
    }

    @Test
    void calculateDistanceRouteAD() {
        List<String> routeList = Arrays.asList("A", "D");
        int distance =(int) routesService.calculateRoutesOfList(routeList);
        Assertions.assertEquals(5, distance);
    }

    @Test
    void calculateDistanceRouteADC() {
        List<String> routeList = Arrays.asList("A", "D","C");
        int distance =(int) routesService.calculateRoutesOfList(routeList);
        Assertions.assertEquals(13, distance);
    }

    @Test
    void calculateDistanceRouteAEBCD() {
        List<String> routeList = Arrays.asList("A", "E", "B", "C", "D");
        int distance =(int) routesService.calculateRoutesOfList(routeList);
        Assertions.assertEquals(22, distance);
    }

    @Test
    void calculateDistanceRouteAED() {
        List<String> routeList = Arrays.asList("A", "E", "D");
        String response =(String) routesService.calculateRoutesOfList(routeList);
        Assertions.assertEquals("NO SUCH ROUTE", response);
    }

    @Test
    void countTripsStartingAndEndingAtCWithMax3Stops() {
        int count = routesService.countTripsWithMaxStops("C", "C", 3);
        Assertions.assertEquals(2, count);
    }

    @Test
    void countTripsStartingAtAAndEndingAtCWithExactly4Stops() {
        int count = routesService.countTripsWithExactStops("A", "C", 4);
        Assertions.assertEquals(3, count);
    }

    @Test
    void shortestRouteLengthFromAToC() {
        int distance = routesService.calculateShortestRoute("A", "C");
        Assertions.assertEquals(9, distance);
    }

    @Test
    void shortestRouteLengthFromBToB() {
        int distance = routesService.calculateShortestRoute("B", "B");
        Assertions.assertEquals(9, distance);
    }

    @Test
    void countDifferentRoutesFromCToCWithDistanceLessThan30() {
        int count = routesService.countRoutesWithDistanceLessThan("C", "C", 30);
        Assertions.assertEquals(7, count);
    }

}
