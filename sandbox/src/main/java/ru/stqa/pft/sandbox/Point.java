package ru.stqa.pft.sandbox;

public class Point {
    public double x, y;
    public double distance;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance (Point B) {
    double x = this.x - B.x;
    double y = this.y - B.y;
    return distance = Math.sqrt(x * x + y * y);
}
}