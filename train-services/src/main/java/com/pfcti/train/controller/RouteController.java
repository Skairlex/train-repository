package com.pfcti.train.controller;

import java.util.List;
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

    @PostMapping("/addLocation/{name}")
    public void addLocation(@PathVariable String name) {
        routesService.addLocation(name);
    }

    @PostMapping("/addRoute/{origin}/{destination}/{distance}")
    public void addRoute(@PathVariable String origin, @PathVariable String destination, @PathVariable int distance) {
        routesService.addRoute(origin, destination, distance);
    }

    @GetMapping("/shortestRoute/{origin}/{destination}")
    public int calculateShortestRoute(@PathVariable String origin, @PathVariable String destination) {
        return routesService.calculateShortestRoute(origin, destination);
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
                return "NO SUCH ROUTE";
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