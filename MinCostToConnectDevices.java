import java.util.*;

public class MinCostToConnectDevices {
    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        // Create a list to store all edges
        List<int[]> edges = new ArrayList<>();
        for (int[] connection : connections) {
            int device1 = connection[0];
            int device2 = connection[1];
            int cost = connection[2];
            edges.add(new int[]{device1, device2, cost});
        }
        for (int i = 0; i < n; i++) {
            edges.add(new int[]{i + 1, n + 1, modules[i]});
        }

        // Sort edges by cost
        edges.sort((a, b) -> Integer.compare(a[2], b[2]));
        int[] parent = new int[n + 2]; // Devices are numbered from 1 to n, virtual node is n+1
        for (int i = 1; i <= n + 1; i++) {
            parent[i] = i;
        }

        // Kruskal's Algorithm to find MST
        int totalCost = 0;
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];

            int rootU = find(parent, u);
            int rootV = find(parent, v);

            if (rootU != rootV) {
                totalCost += cost;
                parent[rootU] = rootV;
            }
        }

        return totalCost;
    }

    // Union-Find: Find operation with path compression
    private static int find(int[] parent, int node) {
        if (parent[node] != node) {
            parent[node] = find(parent, parent[node]);
        }
        return parent[node];
    }

    // Main function to test the solution
    public static void main(String[] args) {
        int n = 3;
        int[] modules = {1, 2, 2};
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};
        System.out.println(minCostToConnectDevices(n, modules, connections)); // Output: 3
    }
}