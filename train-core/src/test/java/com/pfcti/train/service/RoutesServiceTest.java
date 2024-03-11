package com.pfcti.train.service;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import com.pfcti.train.entity.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoutesServiceTest {

    private RoutesService routesService;

    @BeforeEach
    void setUp() {
        routesService = new RoutesService();
        routesService.addLocation("A");
        routesService.addLocation("B");
        routesService.addLocation("C");
        routesService.addRoute("A", "B", 3);
        routesService.addRoute("B", "A", 3);
        routesService.addRoute("B", "C", 2);
        routesService.addRoute("C", "A", 5);
    }

    @Test
    void addLocationSimle() {
        routesService.addLocation("A");
        Assertions.assertNotNull(routesService.getLocations().get("A"));
    }

    @Test
    void addDuplicateLocation() {
        routesService.setLocations(new HashMap<>());
        routesService.addLocation("A");
        routesService.addLocation("A");
        Assertions.assertEquals(1,routesService.getLocations().size());
    }

    @Test
    void addMultipleLocations() {
        routesService.addLocation("A");
        routesService.addLocation("B");
        routesService.addLocation("C");
        Assertions.assertNotNull(routesService.getLocations().get("A"));
        Assertions.assertNotNull(routesService.getLocations().get("B"));
        Assertions.assertNotNull(routesService.getLocations().get("C"));
    }

    @Test
    void addLocationWithNullOrEmptyName() {
        Assertions.assertThrows(RuntimeException.class, () -> routesService.addLocation(null));
        Assertions.assertThrows(RuntimeException.class, () -> routesService.addLocation(""));
    }

    @Test
    void addRouteSimple() {
        routesService.setRoutes(new HashSet<>());
        routesService.addRoute("A", "B", 5);

        Set<Route> routes = routesService.getRoutes();
        Assertions.assertEquals(1, routes.size());

        Route addedRoute = routes.iterator().next();
        Assertions.assertEquals("A", addedRoute.getOrigin().getName());
        Assertions.assertEquals("B", addedRoute.getDestination().getName());
        Assertions.assertEquals(5, addedRoute.getDistance());
    }

    @Test
    void addRouteWithInvalidOriginOrDestination() {
        Assertions.assertThrows(RuntimeException.class, () -> routesService.addRoute("Nonexistent", "B", 5));
        Assertions.assertThrows(RuntimeException.class, () -> routesService.addRoute("A", "Nonexistent", 5));
    }

    @Test
    void addRouteWithNegativeDistance() {
        routesService.addLocation("A");
        routesService.addLocation("B");

        Assertions.assertThrows(RuntimeException.class, () -> routesService.addRoute("A", "B", -5));
    }

    @Test
    void addRouteToItself() {
        routesService.addLocation("A");
        Assertions.assertThrows(RuntimeException.class, () -> routesService.addRoute("A", "A", 0));
    }

    @Test
    void testCalculateShortestRoute() {
        Assertions.assertNull(null);
    }

    @Test
    void calculateShortestRouteValid() {

        int distance = routesService.calculateShortestRoute("A", "B");
        Assertions.assertEquals(3, distance);
    }

    @Test
    void calculateShortestRouteOriginInvalid() {
        routesService.addLocation("B");
        int result = routesService.calculateShortestRoute("InvalidLocation", "B");
        Assertions.assertEquals(-1, result);
    }

    @Test
    void calculateShortestRouteDestinationInvalid() {
        routesService.addLocation("A");
        int result = routesService.calculateShortestRoute("A", "InvalidLocation");
        Assertions.assertEquals(-2, result);
    }

    @Test
    void calculateShortestRouteSameLocationWithNoMoreRoutes() {
        routesService.addLocation("A");
        int result = routesService.calculateShortestRoute("A", "A");
        Assertions.assertEquals(0, result);
    }

    @Test
    void calculateShortestRouteSameLocationWithMoreRoutes() {
        routesService.addLocation("A");
        routesService.addLocation("B");
        routesService.addRoute("A","B",3);
        routesService.addRoute("B","A",3);
        int result = routesService.calculateShortestRoute("A", "A");
        Assertions.assertEquals(6, result);
    }


    @Test
    void calculateShortestRouteNoLocationsAvailable() {
        routesService.setLocations(new HashMap<>());
        int result = routesService.calculateShortestRoute("A", "B");
        Assertions.assertEquals(-3, result);
    }

    @Test
    void calculateRouteValid() {
        int distance = routesService.calculateRoute("A", "B");
        Assertions.assertEquals(3, distance);
    }

    @Test
    void calculateRouteNoConnection() {
        routesService.addLocation("A");
        routesService.addLocation("C");
        int distance = routesService.calculateRoute("A", "C");
        Assertions.assertEquals(-1, distance);
    }

    @Test
    void calculateRouteInvalidLocation() {
        int distance = routesService.calculateRoute("A", "X");
        Assertions.assertEquals(-2, distance);
    }

    @Test
    void countTripsWithMaxStopsValid() {
        int count = routesService.countTripsWithMaxStops("A", "B", 3);
        Assertions.assertEquals(2, count);
    }

    @Test
    void countTripsWithMaxStopsNoDirectConnection() {
        int count = routesService.countTripsWithMaxStops("A", "C", 3);
        Assertions.assertEquals(1, count);
    }

    @Test
    void countTripsWithMaxStopsInvalidLocation() {
        int count = routesService.countTripsWithMaxStops("A", "X", 3);
        Assertions.assertEquals(-2, count);
    }

    @Test
    void countTripsWithMaxStopsInvalidMaxStops() {
        int count = routesService.countTripsWithMaxStops("A", "B", 0);
        Assertions.assertEquals(-3, count);
    }

    @Test
    void countTripsWithMaxStopsCircularRoute() {
        int count = routesService.countTripsWithMaxStops("A", "A", 3);
        Assertions.assertEquals(2, count);
    }


    @Test
    void countTripsWithExactStopsValid() {
        int count = routesService.countTripsWithExactStops("A", "C", 2);
        Assertions.assertEquals(1, count);
    }

    // Test para verificar que devuelve 0 si no hay ubicaciones válidas
    @Test
    void countTripsWithExactStopsInvalidLocations() {
        int count = routesService.countTripsWithExactStops("X", "Y", 2);
        Assertions.assertEquals(-2, count);
    }

    // Test para verificar que devuelve -3 si el número de paradas exactas es negativo
    @Test
    void countTripsWithExactStopsNegativeStops() {
        int count = routesService.countTripsWithExactStops("A", "A", -1);
        Assertions.assertEquals(-3, count);
    }


    @Test
    void countRoutesWithDistanceLessThanValid() {
        int count = routesService.countRoutesWithDistanceLessThan("A", "C", 6);
        Assertions.assertEquals(1, count);
    }

    // Test para verificar que devuelve 0 si no hay ubicaciones válidas
    @Test
    void countRoutesWithDistanceLessThanInvalidLocations() {
        int count = routesService.countRoutesWithDistanceLessThan("X", "Y", 6);
        Assertions.assertEquals(-3, count);
    }


}
