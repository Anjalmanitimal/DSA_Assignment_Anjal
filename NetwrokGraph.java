import java.util.*;

class NetworkGraph {
    static class Edge {
        int source, destination, cost, bandwidth;

        Edge(int source, int destination, int cost, int bandwidth) {
            this.source = source;
            this.destination = destination;
            this.cost = cost;
            this.bandwidth = bandwidth;
        }
    }

    private int nodes;
    private List<Edge> edges;

    public NetworkGraph(int nodes) {
        this.nodes = nodes;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int source, int destination, int cost, int bandwidth) {
        edges.add(new Edge(source, destination, cost, bandwidth));
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getNodes() {
        return nodes;
    }
}