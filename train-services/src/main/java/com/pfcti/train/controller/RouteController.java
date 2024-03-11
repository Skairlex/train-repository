package com.pfcti.train.controller;

import java.util.List;
import java.util.Set;
import com.pfcti.train.service.IRoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private IRoutesService routesService;

    @GetMapping("/locations")
    public ResponseEntity<Set<String>> getLocations() {
        Set<String> locationNames = routesService.getLocationNames();
        return new ResponseEntity<>(locationNames, HttpStatus.OK);
    }

    @GetMapping("/routes")
    public ResponseEntity<List<String>> getRoutes() {
        List<String> routeInfo = routesService.getRouteInfo();
        return new ResponseEntity<>(routeInfo, HttpStatus.OK);
    }

    @PostMapping("/addLocation/{name}")
    public void addLocation(@PathVariable String name) {
        routesService.addLocation(name);
    }

    @PostMapping("/addRoute/{origin}/{destination}/{distance}")
    public ResponseEntity<String> addRoute(@PathVariable String origin, @PathVariable String destination, @PathVariable int distance) {
        try {
            routesService.addRoute(origin, destination, distance);
            return ResponseEntity.ok("Route added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/shortestRoute/{origin}/{destination}")
    public ResponseEntity<Object> calculateShortestRoute(@PathVariable String origin, @PathVariable String destination) {
        int shortestRouteDistance = routesService.calculateShortestRoute(origin, destination);

        if (shortestRouteDistance == -1) {
            String errorMessage = "Invalid origin";
            return ResponseEntity.badRequest().body(errorMessage);
        } else if (shortestRouteDistance == -2) {
            String errorMessage = "Invalid destination";
            return ResponseEntity.badRequest().body(errorMessage);
        } else if (shortestRouteDistance == -3) {
            String errorMessage = "No locations available";
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return ResponseEntity.ok(shortestRouteDistance);
    }

    @GetMapping("/countRoutesWithDistanceLessThan/{startLocation}/{endLocation}/{maxDistance}")
    public ResponseEntity<Object> countRoutesWithDistanceLessThan(
        @PathVariable String startLocation,
        @PathVariable String endLocation,
        @PathVariable int maxDistance) {

        try {
            int count = routesService.countRoutesWithDistanceLessThan(startLocation, endLocation, maxDistance);
            if (count == -3) {
                String errorMessage = "No locations available";
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/calculateIndirectDistance")
    public Object calculateIndirectDistance(@RequestBody List<String> locationNames) {
        int totalDistance = 0;

        for (int i = 0; i < locationNames.size() - 1; i++) {
            String origin = locationNames.get(i);
            String destination = locationNames.get(i + 1);
            // Cambiamos a calculateRoute para manejar el caso de "NO SUCH ROUTE"
            int distance = routesService.calculateShortestRoute(origin, destination);
            totalDistance += distance;
        }

        return totalDistance;
    }

    @PostMapping("/calculateDirectDistance")
    public Object calculateDirectDistance(@RequestBody List<String> locationNames) {
        int totalDistance = 0;

        for (int i = 0; i < locationNames.size() - 1; i++) {
            String origin = locationNames.get(i);
            String destination = locationNames.get(i + 1);

            // Cambiamos a calculateRoute para manejar el caso de "NO SUCH ROUTE"
            int distance = routesService.calculateRoute(origin, destination);

            if (distance == -1) {
                return "NO SUCH ROUTE"; // Maneja el caso en que no hay conexión directa
            } else if (distance == -2) {
                return "LOCATION NOT FOUND"; // Maneja el caso en que una ubicación no existe
            }

            totalDistance += distance;
        }

        return totalDistance;
    }
    @GetMapping("/countTripsWithMaxStops/{startLocation}/{endLocation}/{maxStops}")
    public ResponseEntity<Object> countTripsWithMaxStops(
        @PathVariable String startLocation,
        @PathVariable String endLocation,
        @PathVariable int maxStops) {

        int result = routesService.countTripsWithMaxStops(startLocation, endLocation, maxStops);

        if (result == -2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("NO EXIST UBICATIONS");
        }
        if (result == -3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("NO VALID NUMBER STOPS");
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/countTripsWithExactStops/{startLocation}/{endLocation}/{exactStops}")
    public ResponseEntity<Object> countTripsWithExactStops(
        @PathVariable String startLocation,
        @PathVariable String endLocation,
        @PathVariable int exactStops) {

        int result = routesService.countTripsWithExactStops(startLocation, endLocation, exactStops);

        if (result == -2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Ubicaciones no válidas");
        }
        if (result == -3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("DON PERMIT NEGATIVE NUMBER IN STOPS");
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/initializeGraph")
    public void initializeGraph() {
        // Agregar ubicaciones
        routesService.addLocation("A");
        routesService.addLocation("B");
        routesService.addLocation("C");
        routesService.addLocation("D");
        routesService.addLocation("E");

        // Agregar rutas
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
}
