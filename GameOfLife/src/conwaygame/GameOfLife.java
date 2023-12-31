package conwaygame;
import java.util.ArrayList;

/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 6;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
        grid[4][4] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(file);
        int r = StdIn.readInt();
        int c = StdIn.readInt();

        grid = new boolean[r][c];

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = StdIn.readBoolean();
            }
        }
       
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        return grid[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] == true){
                    return true;
                }
            }
        }
        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE
        int count = 0;
        int rowUp = row - 1;
        int rowDown = row + 1;
        int colLeft = col - 1;
        int colRight = col + 1;

        if(rowUp == -1){
            rowUp = grid.length - 1;
        }
        if(rowDown == grid.length){
            rowDown = 0;
        }
        if(colLeft == -1){
            colLeft = grid[0].length - 1;
        }
        if(colRight == grid[0].length){
            colRight = 0;
        }
        if(grid[rowUp][colLeft]){
            count++;
        }
        if(grid[row][colLeft]){
            count ++;
        }
        if(grid[rowDown][colLeft]){
            count++;
        }
        if(grid[rowUp][col]){
            count++;
        }
        if(grid[rowDown][col]){
            count++;
        }
        if(grid[rowUp][colRight]){
            count++;
        }
        if(grid[row][colRight]){
            count++;
        }
        if(grid[rowDown][colRight]){
            count++;
        }
        return count; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        // WRITE YOUR CODE HERE
        boolean [][] newGrid;
        newGrid = new boolean[grid.length][grid[0].length];

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(getCellState(i, j) == true){
                    if(numOfAliveNeighbors(i, j) < 2){
                        newGrid[i][j] = false;
                    }
                    else if(numOfAliveNeighbors(i, j) > 3){
                        newGrid[i][j] = false;
                    }
                    else{
                        newGrid[i][j] = grid[i][j];
                    }
                }
                else{
                    if(numOfAliveNeighbors(i, j) == 3){
                        newGrid[i][j] = true;
                    }
                    else{
                        newGrid[i][j] = grid[i][j];
                    }
                }
            }
        }
        return newGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        boolean[][] ngrid;
        ngrid = computeNewGrid();
        totalAliveCells = 0;

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j] = ngrid[i][j];
                if(grid[i][j] == true){
                    totalAliveCells++;
                }
            }
        }
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        boolean[][] newGrid;
        for(int a = 0; a < n; a++){
            newGrid = computeNewGrid();
            for(int i = 0; i < grid.length; i++){
                for(int j = 0; j < grid[0].length; j++){
                    grid[i][j] = newGrid[i][j];
                }
            }
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        WeightedQuickUnionUF w = new WeightedQuickUnionUF(grid.length, grid[0].length);
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == true){
                    int rowUp = i - 1;
                    int rowDown = i + 1;
                    int colLeft = j - 1;
                    int colRight = j + 1;

                    if(rowUp == -1){
                        rowUp = grid.length - 1;
                    }
                    if(rowDown == grid.length){
                        rowDown = 0;
                    }
                    if(colLeft == -1){
                        colLeft = grid[0].length -1;
                    }
                    if(colRight == grid[0].length){
                        colRight = 0;
                    }
                    if(grid[rowUp][colLeft]){
                        w.union(i, j, rowUp, colLeft);
                    }
                    if(grid[i][colLeft]){
                        w.union(i, j, i, colLeft);
                    }
                    if(grid[rowDown][colLeft]){
                        w.union(i, j, rowDown, colLeft);
                    }
                    if(grid[rowUp][j]){
                        w.union(i, j, rowUp, j);
                    }
                    if(grid[rowDown][j]){
                        w.union(i, j, rowDown, j);
                    }
                    if(grid[rowUp][colRight]){
                        w.union(i, j, rowUp, colRight);
                    }
                    if(grid[i][colRight]){
                        w.union(i, j, i, colRight);
                    }
                    if(grid[rowDown][colRight]){
                        w.union(i, j, rowDown, colRight);
                    }
                }
            }
        }
        int count = 0;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == true){
                    if(numOfAliveNeighbors(i, j) != 0){
                        if(w.find(i, j) == (i * grid.length + j)){
                            count++;
                        }
                    }
                    else{
                        count++;
                    }
                }
            }
        }
        return count; // update this line, provided so that code compiles
    }
}
