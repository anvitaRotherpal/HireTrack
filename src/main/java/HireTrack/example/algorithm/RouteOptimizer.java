package HireTrack.example.algorithm;

import HireTrack.example.model.Store;
import java.util.*;

// RouteOptimizer calculates shortest paths between stores
public class RouteOptimizer {

    // Graph: key = store name, value = list of edges to other stores
    private final Map<String, List<Edge>> graph = new HashMap<>();

    // Inner class to represent an edge to another store
    static class Edge {
        Store destination;
        int distance;

        Edge(Store destination) {
            this.destination = destination;
            this.distance = destination.getDistance();
        }
    }

    // Add a route between two stores
    public void addRoute(Store from, Store to) {
        graph.computeIfAbsent(from.getName(), k -> new ArrayList<>())
             .add(new Edge(to));

        graph.computeIfAbsent(to.getName(), k -> new ArrayList<>())
             .add(new Edge(from));
    }

    // Dijkstra algorithm to calculate shortest distances from a starting store
    public Map<String, Integer> shortestPath(String start) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());

        for (String store : graph.keySet()) {
            distances.put(store, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        pq.offer(new AbstractMap.SimpleEntry<>(start, 0));

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> entry = pq.poll();
            String current = entry.getKey();
            int currentDist = entry.getValue();

            for (Edge edge : graph.getOrDefault(current, new ArrayList<>())) {
                String neighbor = edge.destination.getName();
                int newDist = currentDist + edge.distance;
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.offer(new AbstractMap.SimpleEntry<>(neighbor, newDist));
                }
            }
        }

        return distances;
    }

    // Temporary main method to test the algorithm
    public static void main(String[] args) {
        RouteOptimizer optimizer = new RouteOptimizer();

        Store home = new Store("Home", 0);
        Store storeA = new Store("StoreA", 4);
        Store storeB = new Store("StoreB", 2);
        Store storeC = new Store("StoreC", 5);

        optimizer.addRoute(home, storeA);
        optimizer.addRoute(home, storeB);
        optimizer.addRoute(storeB, storeA);
        optimizer.addRoute(storeA, storeC);
        optimizer.addRoute(storeB, storeC);

        System.out.println(optimizer.shortestPath("Home"));
    }
}