import java.util.concurrent.ThreadLocalRandom;

public class PacmanGame {
    enum Move {TOP, BOTTOM, LEFT, RIGHT}
    enum Status {IN_GAME, LOSE, WIN}
    private Status currentStatus = Status.IN_GAME;
    Coordinate pacman;
    int[][] grid;
    int point = 0;
    int partialPoint = 0;

    public PacmanGame() {
        this.grid = new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2},
                {2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2},
                {2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 2},
                {2, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 2},
                {2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
                {2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2},
                {2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 0, 2, 0, 2, 2, 2, 2, 2, 0, 2, 0, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2},
                {2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2},
                {2, 2, 2, 0, 2, 0, 2, 0, 2, 2, 2, 2, 2, 0, 2, 0, 2, 0, 2, 2, 2},
                {2, 2, 2, 0, 2, 0, 2, 0, 2, 2, 2, 2, 2, 0, 2, 0, 2, 0, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
                {2, 0, 2, 2, 2, 2, 2, 2, 2, 0, 2, 0, 2, 2, 2, 2, 2, 2, 2, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
        };
        pacman= new Coordinate(1, 1);
        generateItemToEat();
    }

    public void generateItemToEat(){
        grid[20][1] = 4;
        grid[20][19] = 4;
        grid[10][1] = 4;
        grid[10][19] = 4;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 0)
                    grid[i][j] = 3;
            }
        }
    }

    public void generateCherry(){
        if(partialPoint == 150) {
            int x = ThreadLocalRandom.current().nextInt(grid.length);
            int y = ThreadLocalRandom.current().nextInt(grid[x].length);
            if(!this.pacman.equals(new Coordinate(x, y)) && grid[x][y] != 2) {
                grid[x][y] = 1;
                partialPoint = 0;
            }
            else
                generateCherry();
            }

    }

    public void eatItemsOrCherry(Coordinate coord){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 3 && coord.getX() == i && coord.getY() == j){
                    point += 10;
                    partialPoint += 10;
                    grid[i][j] = 0;
                }
                if(grid[i][j] == 4 && coord.getX() == i && coord.getY() == j) {
                    point += 100;
                    partialPoint += 100;
                    grid[i][j] = 0;
                }
                if(grid[i][j] == 1 && coord.getX() == i && coord.getY() == j) {
                    point += 1000;
                    partialPoint += 1000;
                    grid[i][j] = 0;
                }
            }
        }
        win();
    }

    public void win(){
        if(point > 1000)
            currentStatus = Status.WIN;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public int getPoint() {
        return point;
    }

    public int getPartialPoint() {
        return partialPoint;
    }

    public void move(Move m) {
        eatItemsOrCherry(this.pacman);
        generateCherry();
        if (m == Move.RIGHT && grid[pacman.getX()][pacman.getY() + 1] != 2) {
            this.pacman = new Coordinate(pacman.getX(), pacman.getY() + 1);
        } else if (m == Move.LEFT && grid[pacman.getX()][pacman.getY() - 1] != 2) {
            this.pacman = new Coordinate(pacman.getX(), pacman.getY() - 1);
        } else if (m == Move.TOP && grid[pacman.getX() - 1][pacman.getY() ] != 2) {
            this.pacman = new Coordinate(pacman.getX() - 1, pacman.getY());
        } else if (m == Move.BOTTOM && grid[pacman.getX() + 1][pacman.getY()] != 2) {
            this.pacman = new Coordinate(pacman.getX() + 1, pacman.getY());
        }
    }

    public String toString() {
        String result =  this.currentStatus + "\n";
        for (int x = 0; x < this.grid.length; x++) {
            result += "[";
            for (int y = 0; y <this.grid[x].length; y++) {
                if (this.pacman.equals(new Coordinate(x, y)))
                    result += "[\u001B[33mO\u001B[0m]";
                else if (grid[x][y] == 4)
                    result += "[\u001B[37mo\u001B[0m]";
                else if (grid[x][y] == 3)
                    result += "[\u001B[37m-\u001B[0m]";
                else if (grid[x][y] == 2)
                    result += "[\u001B[34mO\u001B[0m]";
                else if (grid[x][y] == 1)
                    result += "[\u001B[31mO\u001B[0m]";
                else
                    result += "[ ]";
            }
            result += "]\n";
        }
        return result;
    }

}