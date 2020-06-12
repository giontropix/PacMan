import java.util.ArrayList;

public class Coordinate {
    private int x;
    private int y;
    private ArrayList<Coordinate> bestPath;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        bestPath = new ArrayList<>();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Coordinate> getBestPath() {
        return bestPath;
    }

    public void setBestPath(ArrayList<Coordinate> bestPath) {
        this.bestPath = bestPath;
    }

    public boolean equals(Object o) {
        if (o instanceof Coordinate) {
            Coordinate c = (Coordinate)o;
            return c.x == this.x && c.y == this.y;
        }
        return false;
    }
}
