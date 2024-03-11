package com.pfcti.train.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import com.pfcti.train.entity.Location;
import com.pfcti.train.entity.Route;
import com.pfcti.train.entity.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@Slf4j
public class RoutesService implements IRoutesService{

    private Map<String, Location> locations = new HashMap<>();
    private Set<Route> routes = new HashSet<>();

    public void addLocation(String name) {
        locations.put(name, new Location(name));
    }

    public void addRoute(String originName, String destinationName, int distance) {
        Location origin = locations.get(originName);
        Location destination = locations.get(destinationName);

        if (origin != null && destination != null) {
            Route route = new Route(origin, destination, distance);
            routes.add(route);
            if(origin.getOutgoingRoutes()==null){
                origin.setOutgoingRoutes(new ArrayList<>());
            }
            origin.getOutgoingRoutes().add(route);
        } else {
            throw new RuntimeException("Invalid origin or destination");
        }
    }

    public int calculateShortestRoute(String originName, String destinationName) {
        Location origin = locations.get(originName);
        Location destination = locations.get(destinationName);
        if (origin == null || destination == null) {
            throw new RuntimeException("Invalid origin or destination");
        }
        Map<Location, Integer> distanceMap = new HashMap<>();
        Set<Location> visitedLocations = new HashSet<>();
        for (Location location : locations.values()) {
            distanceMap.put(location, Integer.MAX_VALUE);
        }
        distanceMap.put(origin, 0);
        while (!visitedLocations.contains(destination)) {
            Location currentLocation = getClosestLocation(distanceMap, visitedLocations);
            visitedLocations.add(currentLocation);

            for (Route route : currentLocation.getOutgoingRoutes()) {
                Location nextLocation = route.getDestination();
                int newDistance = distanceMap.get(currentLocation) + route.getDistance();

                if (newDistance < distanceMap.get(nextLocation)) {
                    distanceMap.put(nextLocation, newDistance);
                }
            }
        }

        return distanceMap.get(destination);
    }

    @Override
    public int calculateRoute(String origin, String destination) {
        // Verifica si existe una conexión directa entre origin y destination
        for (Route route : routes) {
            if (route.getOrigin().getName().equals(origin) && route.getDestination().getName().equals(destination)) {
                return route.getDistance();
            }
        }

        // No hay conexión directa, devuelve -1
        return -1;
    }

    @Override
    public int countTripsWithMaxStops(String startLocation, String endLocation, int maxStops) {
        Location start = locations.get(startLocation);
        Location end = locations.get(endLocation);

        if (start == null || end == null) {
            return -2;
        }

        if (maxStops<1) {
            return -3;
        }

        return countTripsWithMaxStops(start, end, maxStops);
    }

    @Override
    public int countTripsWithExactStops(String startLocation, String endLocation, int exactStops) {
        Location start = locations.get(startLocation);
        Location end = locations.get(endLocation);

        if (start == null || end == null) {
            return -2; // Ubicaciones no válidas
        }

        return countTripsWithExactStopsDFS(start, end, exactStops, 0);
    }

    private int countTripsWithExactStopsDFS(Location current, Location end, int exactStops, int stops) {
        if (stops == exactStops && current.equals(end)) {
            return 1; // Se encontró una ruta que cumple con los requisitos
        }

        if (stops > exactStops) {
            return 0; // Se excedió el número exacto de paradas
        }

        int tripsCount = 0;

        for (Route route : current.getOutgoingRoutes()) {
            tripsCount += countTripsWithExactStopsDFS(route.getDestination(), end, exactStops, stops + 1);
        }

        return tripsCount;
    }

    private int countTripsWithMaxStops(Location start, Location end, int maxStops) {
        int tripsCount = 0;

        LinkedList<Step> stack = new LinkedList<>();
        stack.push(new Step(start, 0)); // Agregamos el inicio con 0 paradas

        while (!stack.isEmpty()) {
            Step currentStep = stack.pop();
            Location currentLocation = currentStep.getLocation();
            int currentStops = currentStep.getStops();

            if (currentStops > maxStops) {
                continue; // Saltar esta ruta si excede el número máximo de paradas
            }

            if (currentLocation.equals(end) && currentStops > 0) {
                tripsCount++; // Encontramos una ruta que cumple con los requisitos
            }

            for (Route route : currentLocation.getOutgoingRoutes()) {
                stack.push(new Step(route.getDestination(), currentStops + 1));
            }
        }

        return tripsCount;
    }


    private Location getClosestLocation(Map<Location, Integer> distanceMap, Set<Location> visitedLocations) {

        // Encuentra la entrada con la menor distancia no visitada usando Comparator y Optional
        Optional<Entry<Location, Integer>> closestEntry = distanceMap.entrySet().stream()
            .filter(entry -> !visitedLocations.contains(entry.getKey()))
            .min(Comparator.comparingInt(Map.Entry::getValue));

        // Si se encuentra una entrada, devuelve la ubicación correspondiente
        if (closestEntry.isPresent()) {
            return closestEntry.get().getKey();
        } else {
            // Si no hay entradas válidas, lanza una excepción
            throw new RuntimeException("No reachable locations");
        }
    }



}
