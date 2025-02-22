import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.List;

public class NetworkOptimizationApp extends JFrame {
    private NetworkGraph graph;
    private JPanel graphPanel;
    private JTextArea outputArea;

    public NetworkOptimizationApp() {
        setTitle("Network Optimization App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize graph
        graph = new NetworkGraph(0);

        // Create GUI components
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };
        graphPanel.setBackground(Color.WHITE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JButton addNodeButton = new JButton("Add Node");
        JButton addEdgeButton = new JButton("Add Edge");
        JButton optimizeButton = new JButton("Optimize Network");
        JButton shortestPathButton = new JButton("Find Shortest Path");

        // Add action listeners
        addNodeButton.addActionListener(e -> addNode());
        addEdgeButton.addActionListener(e -> addEdge());
        optimizeButton.addActionListener(e -> optimizeNetwork());
        shortestPathButton.addActionListener(e -> findShortestPath());

        // Layout
        JPanel controlPanel = new JPanel();
        controlPanel.add(addNodeButton);
        controlPanel.add(addEdgeButton);
        controlPanel.add(optimizeButton);
        controlPanel.add(shortestPathButton);

        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(graphPanel), BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
    }

    private void addNode() {
        graph = new NetworkGraph(graph.getNodes() + 1);
        graphPanel.repaint();
    }

    private void addEdge() {
        String input = JOptionPane.showInputDialog(this, "Enter edge (source, destination, cost, bandwidth):");
        if (input != null) {
            String[] parts = input.split(",");
            int source = Integer.parseInt(parts[0].trim());
            int destination = Integer.parseInt(parts[1].trim());
            int cost = Integer.parseInt(parts[2].trim());
            int bandwidth = Integer.parseInt(parts[3].trim());
            graph.addEdge(source, destination, cost, bandwidth);
            graphPanel.repaint();
        }
    }

    private void optimizeNetwork() {
        List<NetworkGraph.Edge> mst = NetworkOptimizer.findMST(graph);
        outputArea.setText("Optimal Network Topology (MST):\n");
        for (NetworkGraph.Edge edge : mst) {
            outputArea.append(edge.source + " -> " + edge.destination + " (Cost: " + edge.cost + ")\n");
        }
        graphPanel.repaint();
    }

    private void findShortestPath() {
        String input = JOptionPane.showInputDialog(this, "Enter start and end nodes:");
        if (input != null) {
            String[] parts = input.split(",");
            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());
            List<Integer> path = NetworkOptimizer.findShortestPath(graph, start, end);
            outputArea.setText("Shortest Path:\n");
            for (int node : path) {
                outputArea.append(node + " ");
            }
            outputArea.append("\n");
            graphPanel.repaint();
        }
    }

    private void drawGraph(Graphics g) {
        int radius = 20;
        for (int i = 0; i < graph.getNodes(); i++) {
            int x = 50 + i * 100;
            int y = 50;
            g.setColor(Color.BLUE);
            g.fillOval(x, y, radius, radius);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i), x + radius / 2, y + radius / 2);
        }

        for (NetworkGraph.Edge edge : graph.getEdges()) {
            int x1 = 50 + edge.source * 100 + radius / 2;
            int y1 = 50 + radius / 2;
            int x2 = 50 + edge.destination * 100 + radius / 2;
            int y2 = 50 + radius / 2;
            g.setColor(Color.RED);
            g.drawLine(x1, y1, x2, y2);
            g.drawString("C:" + edge.cost + " B:" + edge.bandwidth, (x1 + x2) / 2, (y1 + y2) / 2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NetworkOptimizationApp app = new NetworkOptimizationApp();
            app.setVisible(true);
        });
    }
}