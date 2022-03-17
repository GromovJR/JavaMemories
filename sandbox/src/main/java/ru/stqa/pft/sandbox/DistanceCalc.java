package ru.stqa.pft.sandbox;

public class DistanceCalc {
    public static void main(String[] args) {

        Point A = new Point(5, 5);
        Point B = new Point(0, 0);
        System.out.println("Расстояние между точками А и В =  " + A.distance(B));
    }
}
