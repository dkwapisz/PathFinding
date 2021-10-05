package main.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int x;
    int y;
    int distanceFromSource;
    List<Integer> path = new ArrayList<>();

    Node(int x, int y, int dist, int lastX, int lastY) {
        this.x = x;
        this.y = y;
        path.add(lastX);
        path.add(lastY);
        this.distanceFromSource = dist;
    }
}
