import java.util.*;

public class PackageCollector {
    public static int minRoadsToCollectPackages(int[] packages, int[][] roads) {
        int n = packages.length;
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Step 1: Build the adjacency list (Graph Representation)
        for (int[] road : roads) {
            graph.computeIfAbsent(road[0], k -> new ArrayList<>()).add(road[1]);
            graph.computeIfAbsent(road[1], k -> new ArrayList<>()).add(road[0]);
        }

        // Step 2: Identify all package locations
        Set<Integer> packageLocations = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (packages[i] == 1) {
                packageLocations.add(i);
            }
        }

        // Step 3: BFS to find minimum road traversal
        return bfsTraversal(graph, packageLocations, n);
    }

    private static int bfsTraversal(Map<Integer, List<Integer>> graph, Set<Integer> packageLocations, int n) {
        int minMoves = Integer.MAX_VALUE;

        // Try starting BFS from every node that exists in the graph
        for (int start : graph.keySet()) {
            Set<Integer> visited = new HashSet<>();
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{start, 0}); // {node, distance}
            int moves = 0;
            Set<Integer> collected = new HashSet<>();

            while (!queue.isEmpty()) {
                int[] current = queue.poll();
                int node = current[0], dist = current[1];

                // Collect packages if within distance 2
                if (dist <= 2 && packageLocations.contains(node)) {
                    collected.add(node);
                }

                // If all packages collected, record moves
                if (collected.equals(packageLocations)) {
                    minMoves = Math.min(minMoves, moves);
                    break;
                }

                // Traverse adjacent nodes
                for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(new int[]{neighbor, dist + 1});
                        moves++; // Count road traversals
                    }
                }
            }
        }
        return minMoves;
    }

    public static void main(String[] args) {
        int[] packages1 = {1, 0, 0, 0, 0, 1};
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println(minRoadsToCollectPackages(packages1, roads1)); // Output: 2

        int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1};
        int[][] roads2 = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}};
        System.out.println(minRoadsToCollectPackages(packages2, roads2)); // Output: 2
    }
}
