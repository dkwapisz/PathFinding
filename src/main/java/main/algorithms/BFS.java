package main.algorithms;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.Grid;
import main.Main;

import java.util.*;

public class BFS extends Algorithm{


    private int searchX = -1;
    private int searchY = -1;
    private static Timeline timelineFinding;
    private static Timeline timelinePath;
    private int pathIterator = 2;
    private int dots = 0;
    private final List<Node> nodeList = new ArrayList<>();

    public BFS() {
        pathExists(getArrayGrid());
    }

    private void refreshGrid() {
        if (searchX > 0 && searchY > 0 && (searchX != getSourceX() || searchY != getSourceY())) {
            Grid.cells[searchY][searchX].highlightSearch();
        }
    }

    private void pathExists(int[][] arrayGrid) {

        Node source = new Node(getSourceX(), getSourceY(), 0, getSourceX(), getSourceY());
        Queue<Node> queue = new LinkedList<>();

        queue.add(source);

        timelineFinding = new Timeline(new KeyFrame(Duration.millis(15), e -> queue(queue, arrayGrid)));
        timelineFinding.setCycleCount(Timeline.INDEFINITE);
        timelineFinding.play();


    }

    private void queue(Queue<Node> queue, int[][] arrayGrid) {

        if (!queue.isEmpty()) {
            Node poped = queue.poll();

            textFieldUpdate();
            if (arrayGrid[poped.x][poped.y] != 3) {
                arrayGrid[poped.x][poped.y] = 1;
                searchX = poped.x;
                searchY = poped.y;
                refreshGrid();
                List<Node> neighbourList = addNeighbours(poped, arrayGrid);
                queue.addAll(neighbourList);
            } else if (arrayGrid[poped.x][poped.y] == 3) {
                Main.getStatusField().setText("Path found!");
                List<Integer> path = findPath(poped.distanceFromSource);
                timelineFinding.stop();

                timelinePath = new Timeline(new KeyFrame(Duration.millis(15), e -> printPath(path, poped.distanceFromSource)));
                timelinePath.setCycleCount(Timeline.INDEFINITE);
                timelinePath.play();
            }
        } else {
            Main.getStatusField().setText("No path!");
            timelineFinding.stop();
        }
    }

    private List<Node> addNeighbours(Node poped, int[][] arrayGrid) {

        List<Node> list = new LinkedList<>();
        if ((poped.x-1 > 0 && poped.x-1 < arrayGrid.length) && arrayGrid[poped.x-1][poped.y] != 1) {
            Node nextNode = new Node(poped.x-1, poped.y, poped.distanceFromSource+1, poped.x, poped.y);

            list.add(nextNode);
            nodeList.add(nextNode);
        }
        if ((poped.x+1 > 0 && poped.x+1 < arrayGrid.length) && arrayGrid[poped.x+1][poped.y] != 1) {
            Node nextNode = new Node(poped.x+1, poped.y, poped.distanceFromSource+1, poped.x, poped.y);

            list.add(nextNode);
            nodeList.add(nextNode);
        }
        if ((poped.y-1 > 0 && poped.y-1 < arrayGrid.length) && arrayGrid[poped.x][poped.y-1] != 1) {
            Node nextNode = new Node(poped.x, poped.y-1, poped.distanceFromSource+1, poped.x, poped.y);

            list.add(nextNode);
            nodeList.add(nextNode);
        }
        if ((poped.y+1 > 0 && poped.y+1 < arrayGrid.length) && arrayGrid[poped.x][poped.y+1] != 1) {
            Node nextNode = new Node(poped.x, poped.y+1, poped.distanceFromSource+1, poped.x, poped.y);

            list.add(nextNode);
            nodeList.add(nextNode);
        }

        return list;
    }

    private void textFieldUpdate() {
        if (dots == 0) {
            Main.getStatusField().setText("Seaching.");
            dots = 1;
        } else if (dots == 1) {
            Main.getStatusField().setText("Seaching..");
            dots = 2;
        } else if (dots == 2) {
            Main.getStatusField().setText("Seaching...");
            dots = 0;
        }
    }

    private List<Integer> findPath(int distance) {

        List<Integer> path = new ArrayList<>();
        int lastX = getDestX();
        int lastY = getDestY();

        for (int i = 0; i < distance; i++) {
            for (Node node : nodeList) {
                if (node.x == lastX && node.y == lastY) {
                    path.add(node.path.get(0));
                    path.add(node.path.get(1));
                    lastX = node.path.get(0);
                    lastY = node.path.get(1);
                    break;
                }
            }
        }

        Collections.reverse(path);

        return path;
    }

    private void printPath(List<Integer> path, int distance) {
        if (pathIterator < distance*2) {
            Grid.cells[path.get(pathIterator)][path.get(pathIterator+1)].highlightPath();
            pathIterator += 2;
        } else {
            timelinePath.stop();
        }
    }

    public static void stopTimelines() {
        if (timelinePath != null) {
            timelinePath.stop();
        }
        if (timelineFinding != null) {
            timelineFinding.stop();
        }
    }
}
