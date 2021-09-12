package PW14;

public class MyPoint {
    private int i, x, y;
    private double weight;
    private double dist;

    public MyPoint(int i, int x, int y, double weight, double dist) {
        this.i = i;
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.dist = dist;
    }


    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[" + i + "] ";
    }
}
