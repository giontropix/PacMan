import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PacmanGame {
    enum Move {TOP, BOTTOM, LEFT, RIGHT}
    enum Status {IN_GAME, LOSE, WIN}
    private Status currentStatus = Status.IN_GAME;
    Coordinate pacman;
    ArrayList<Coordinate> phantom = new ArrayList<>();
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
                {2, 2, 2, 2, 2, 0, 2, 0, 2, 9, 9, 9, 2, 0, 2, 0, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 0, 2, 0, 2, 0, 0, 0, 2, 0, 2, 0, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 2, 2, 2, 2},
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
        pacman = new Coordinate(1, 1);
        phantom.add(new Coordinate(13, 9));
        phantom.add(new Coordinate(13, 10));
        phantom.add(new Coordinate(13, 11));
        generateItemToEat();
    }

    public void generateItemToEat(){
        grid[20][1] = 4;//big items to eat
        grid[20][19] = 4;
        grid[10][1] = 4;
        grid[10][19] = 4;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 0 && !this.pacman.equals(new Coordinate(i, j)))
                    grid[i][j] = 3;//little items to eat
            }
        }
    }

    public void generateCherry(){
        if(partialPoint == 150) {
            int x = ThreadLocalRandom.current().nextInt(grid.length);
            int y = ThreadLocalRandom.current().nextInt(grid[x].length);
            if(!this.pacman.equals(new Coordinate(x, y)) && grid[x][y] != 2) {
                grid[x][y] = 1;//cherry
                partialPoint = 0;
            }
            else
                generateCherry();
            }
    }

    public void eatItemsOrCherry(Coordinate coord){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (coord.getX() == i && coord.getY() == j) {
                    int valueOfGrid = grid[i][j];
                    switch (valueOfGrid){
                        case 1: //fruit
                            point += 1000;
                            partialPoint += 1000;
                            grid[i][j] = 0;
                        break;
                        case 3: //little item to eat
                            point += 10;
                            partialPoint += 10;
                            grid[i][j] = 0;
                        break;
                        case 4: //big item to eat
                            point += 100;
                            partialPoint += 100;
                            grid[i][j] = 0;
                        break;
                    }
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
        generateCherry();
        if (m == Move.RIGHT && grid[pacman.getX()][pacman.getY() + 1] != 2 && grid[pacman.getX()][pacman.getY() + 1] != 9) {
            this.pacman.setY(pacman.getY() + 1);
        } else if (m == Move.LEFT && grid[pacman.getX()][pacman.getY() - 1] != 2 && grid[pacman.getX()][pacman.getY() - 1] != 9) {
            this.pacman.setY(pacman.getY() - 1);
        } else if (m == Move.TOP && grid[pacman.getX() - 1][pacman.getY() ] != 2 && grid[pacman.getX() - 1][pacman.getY() ] != 9) {
            this.pacman.setX(pacman.getX() - 1);
        } else if (m == Move.BOTTOM && grid[pacman.getX() + 1][pacman.getY()] != 2 && grid[pacman.getX() + 1][pacman.getY()] != 9) {
            this.pacman.setX(pacman.getX() + 1);
        }
        eatItemsOrCherry(this.pacman);
    }

    public String toString() {
        StringBuilder result = new StringBuilder(this.currentStatus + "\n");
        for (int x = 0; x < this.grid.length; x++) {
            result.append("[");
            for (int y = 0; y <this.grid[x].length; y++) {
                if (this.pacman.equals(new Coordinate(x, y)))
                    result.append("[\u001B[33mO\u001B[0m]");
                else if (this.phantom.contains(new Coordinate(x, y)))
                    result.append("[\u001B[35mO\u001B[0m]");
                else if (grid[x][y] == 9)
                    result.append("[\u001B[36mO\u001B[0m]");
                else if (grid[x][y] == 4)
                    result.append("[\u001B[37mo\u001B[0m]");
                else if (grid[x][y] == 3)
                    result.append("[\u001B[37m-\u001B[0m]");
                else if (grid[x][y] == 2)
                    result.append("[\u001B[34mO\u001B[0m]");
                else if (grid[x][y] == 1)
                    result.append("[\u001B[31mO\u001B[0m]");
                else
                    result.append("[ ]");
            }
            result.append("]\n");
        }
        return result.toString();
    }
}