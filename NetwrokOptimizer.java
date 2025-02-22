import java.util.*;
class NetworkOptimizer {
    // Kruskal's algorithm to find MST (minimizes cost)
    public static List<NetworkGraph.Edge> findMST(NetworkGraph graph) {
        List<NetworkGraph.Edge> mst = new ArrayList<>();
        List<NetworkGraph.Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingInt(e -> e.cost)); // Sort edges by cost

        int[] parent = new int[graph.getNodes()];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (NetworkGraph.Edge edge : edges) {
            int root1 = find(parent, edge.source);
            int root2 = find(parent, edge.destination);
            if (root1 != root2) {
                mst.add(edge);
                parent[root1] = root2;
            }
        }

        return mst;
    }

    // Dijkstra's algorithm to find the shortest path (minimizes latency)
    public static List<Integer> findShortestPath(NetworkGraph graph, int start, int end) {
        int nodes = graph.getNodes();
        int[] dist = new int[nodes];
        int[] prev = new int[nodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(i -> dist[i]));
        pq.add(start);

        while (!pq.isEmpty()) {
            int u = pq.poll();
            for (NetworkGraph.Edge edge : graph.getEdges()) {
                if (edge.source == u) {
                    int v = edge.destination;
                    int alt = dist[u] + edge.bandwidth; // Use bandwidth as weight for latency
                    if (alt < dist[v]) {
                        dist[v] = alt;
                        prev[v] = u;
                        pq.add(v);
                    }
                }
            }
        }

        // Reconstruct the path
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    // Helper function for Kruskal's algorithm
    private static int find(int[] parent, int node) {
        if (parent[node] != node) {
            parent[node] = find(parent, parent[node]);
        }
        return parent[node];
    }
}