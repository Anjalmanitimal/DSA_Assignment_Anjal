public class Block {
    private boolean[][] shape; // Shape of the block
    private int color; // Color of the block
    private int x, y; // Position of the block on the game board

    public Block(boolean[][] shape, int color) {
        this.shape = shape;
        this.color = color;
        this.x = 0; // Initial x position
        this.y = 0; // Initial y position
    }

    public boolean[][] getShape() {
        return shape;
    }

    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Rotate the block 90 degrees clockwise
    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        boolean[][] rotatedShape = new boolean[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedShape[j][rows - 1 - i] = shape[i][j];
            }
        }
        shape = rotatedShape;
    }
}

