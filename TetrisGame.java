import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TetrisGame extends JPanel implements ActionListener, KeyListener {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private static final int TILE_SIZE = 30;
    private static final int PREVIEW_WIDTH = 4;
    private static final int PREVIEW_HEIGHT = 4;

    private GameBoard board;
    private Queue<Block> blockQueue;
    private Block currentBlock;
    private int score;
    private boolean gameOver;
    private Timer timer;

    public TetrisGame() {
        setPreferredSize(new Dimension(TILE_SIZE * (BOARD_WIDTH + PREVIEW_WIDTH + 2), TILE_SIZE * BOARD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        board = new GameBoard(BOARD_WIDTH, BOARD_HEIGHT);
        blockQueue = new LinkedList<>();
        score = 0;
        gameOver = false;

        // Generate two blocks initially
        generateBlock();
        generateBlock();
        currentBlock = blockQueue.poll();

        timer = new Timer(500, this); // Block falls every 500ms
        timer.start();
    }

    // Generate a random block and add it to the queue
    private void generateBlock() {
        Random random = new Random();
        boolean[][][] shapes = {
            {{true, true, true, true}}, // I-block
            {{true, true}, {true, true}}, // O-block
            {{false, true, false}, {true, true, true}}, // T-block
            {{true, true, false}, {false, true, true}}, // Z-block
            {{false, true, true}, {true, true, false}} // S-block
        };
        boolean[][] shape = shapes[random.nextInt(shapes.length)];
        int color = random.nextInt(5) + 1; // Random color (1-5)
        Block block = new Block(shape, color);
        blockQueue.add(block);
    }

    // Handle game logic
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            // Move the block down
            if (board.canPlaceBlock(currentBlock, currentBlock.getX(), currentBlock.getY() + 1)) {
                currentBlock.setY(currentBlock.getY() + 1);
            } else {
                board.placeBlock(currentBlock);
                // Check for completed rows
                int rowsCleared = board.clearRows();
                score += rowsCleared * 100; // Update score
                // Generate a new block
                generateBlock();
                currentBlock = blockQueue.poll();
                // Check for game over
                if (!board.canPlaceBlock(currentBlock, currentBlock.getX(), currentBlock.getY())) {
                    gameOver = true;
                    timer.stop();
                }
            }
            repaint(); // Redraw the game board
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(30, 30, 30)); // Dark gray
        g.fillRect(0, 0, BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);
        g.setColor(Color.BLACK);
        g.fillRect(BOARD_WIDTH * TILE_SIZE, 0, (PREVIEW_WIDTH + 2) * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);
        int[][] boardState = board.getBoard();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (boardState[i][j] != 0) {
                    g.setColor(getColor(boardState[i][j]));
                    g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        boolean[][] shape = currentBlock.getShape();
        int x = currentBlock.getX();
        int y = currentBlock.getY();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    g.setColor(getColor(currentBlock.getColor()));
                    g.fillRect((x + j) * TILE_SIZE, (y + i) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Draw the preview area
        Block nextBlock = blockQueue.peek();
        if (nextBlock != null) {
            boolean[][] nextShape = nextBlock.getShape();
            for (int i = 0; i < nextShape.length; i++) {
                for (int j = 0; j < nextShape[0].length; j++) {
                    if (nextShape[i][j]) {
                        g.setColor(getColor(nextBlock.getColor()));
                        g.fillRect((BOARD_WIDTH + 1 + j) * TILE_SIZE, (1 + i) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }

        // Draw the score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, (BOARD_WIDTH + 1) * TILE_SIZE, (PREVIEW_HEIGHT + 2) * TILE_SIZE);

        // Draw game over message
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over!", BOARD_WIDTH * TILE_SIZE / 2 - 30, BOARD_HEIGHT * TILE_SIZE / 2);
        }
    }

    // Get color based on block type
    private Color getColor(int color) {
        switch (color) {
            case 1: return Color.CYAN;
            case 2: return Color.YELLOW;
            case 3: return Color.MAGENTA;
            case 4: return Color.GREEN;
            case 5: return Color.RED;
            default: return Color.WHITE;
        }
    }

    // Handle key presses
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    moveRight();
                    break;
                case KeyEvent.VK_UP:
                    rotate();
                    break;
                case KeyEvent.VK_DOWN:
                    moveDown();
                    break;
            }
            repaint();
        }
    }

    private void moveLeft() {
        if (board.canPlaceBlock(currentBlock, currentBlock.getX() - 1, currentBlock.getY())) {
            currentBlock.setX(currentBlock.getX() - 1);
        }
    }

    private void moveRight() {
        if (board.canPlaceBlock(currentBlock, currentBlock.getX() + 1, currentBlock.getY())) {
            currentBlock.setX(currentBlock.getX() + 1);
        }
    }

    private void rotate() {
        Block rotatedBlock = new Block(currentBlock.getShape(), currentBlock.getColor());
        rotatedBlock.rotate();
        if (board.canPlaceBlock(rotatedBlock, currentBlock.getX(), currentBlock.getY())) {
            currentBlock.rotate();
        }
    }

    private void moveDown() {
        if (board.canPlaceBlock(currentBlock, currentBlock.getX(), currentBlock.getY() + 1)) {
            currentBlock.setY(currentBlock.getY() + 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Game");
        TetrisGame game = new TetrisGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}