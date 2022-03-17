package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceCalcTests {

    @Test
    public void firstTest() {
        Point A = new Point(2, 4);
        Point B = new Point(2, 4);
        Assert.assertEquals(A.distance(B), 0);
    }

    @Test
    public void secondTest () {
        Point A = new Point(1, 4);
        Point B = new Point(2, 5);
        Assert.assertEquals(A.distance(B), 12);
    }
}
