import java.util.Arrays;
public class GameBoard {
    private int[][] board; // 2D array representing the game board
    private int width, height;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[height][width]; // Initialize empty board
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getBoard() {
        return board;
    }

    // Check if a block can be placed at a specific position
    public boolean canPlaceBlock(Block block, int x, int y) {
        boolean[][] shape = block.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    int boardX = x + j;
                    int boardY = y + i;
                    if (boardX < 0 || boardX >= width || boardY >= height || (boardY >= 0 && board[boardY][boardX] != 0)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Place a block on the board
    public void placeBlock(Block block) {
        boolean[][] shape = block.getShape();
        int x = block.getX();
        int y = block.getY();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    board[y + i][x + j] = block.getColor();
                }
            }
        }
    }

    // Check for completed rows and clear them
    public int clearRows() {
        int rowsCleared = 0;
        for (int i = height - 1; i >= 0; i--) {
            boolean isRowFull = true;
            for (int j = 0; j < width; j++) {
                if (board[i][j] == 0) {
                    isRowFull = false;
                    break;
                }
            }
            if (isRowFull) {
                // Shift all rows above down by one
                for (int k = i; k > 0; k--) {
                    System.arraycopy(board[k - 1], 0, board[k], 0, width);
                }
                // Clear the top row
                Arrays.fill(board[0], 0);
                rowsCleared++;
                i++; // Recheck the same row after shifting
            }
        }
        return rowsCleared;
    }
}