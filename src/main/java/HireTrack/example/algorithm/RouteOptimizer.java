package HireTrack.example.algorithm;

import java.util.*;

public class RouteOptimizer {

    // Graph representation
    private final Map<String, List<Edge>> graph = new HashMap<>();

    // Edge class
    static class Edge {
        String destination;
        int distance;

        Edge(String destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }
    }

    // Add location connection
    public void addRoute(String from, String to, int distance) {
        graph.computeIfAbsent(from, k -> new ArrayList<>())
             .add(new Edge(to, distance));
        graph.computeIfAbsent(to, k -> new ArrayList<>())
             .add(new Edge(from, distance));
    }

    // Dijkstra's Algorithm
    public Map<String, Integer> shortestPath(String start) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance));

        pq.add(new Edge(start, 0));
        distances.put(start, 0);

        while (!pq.isEmpty()) {
            Edge current = pq.poll();

            for (Edge neighbor : graph.getOrDefault(current.destination, new ArrayList<>())) {
                int newDist = distances.get(current.destination) + neighbor.distance;

                if (newDist < distances.getOrDefault(neighbor.destination, Integer.MAX_VALUE)) {
                    distances.put(neighbor.destination, newDist);
                    pq.add(new Edge(neighbor.destination, newDist));
                }
            }
        }

        return distances;
    }
}