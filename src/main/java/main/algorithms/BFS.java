package main.algorithms;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.Cell;
import main.Grid;
import main.Main;

import java.util.*;

public class BFS {

    private int[][] arrayGrid;
    private int sourceX;
    private int sourceY;
    private int destX;
    private int destY;
    private int searchX = -1;
    private int searchY = -1;
    private static Timeline timelineFinding;
    private static Timeline timelinePath;
    private int pathIterator = 2;
    private int dots = 0;
    private List<Node> nodeList = new ArrayList<>();

    public BFS() {
        arrayGrid = new int[Main.getCOLUMNS()][Main.getROWS()];
        makeArrayGrid();
        pathExists(arrayGrid);
    }

    private void refreshGrid() {
        if (searchX > 0 && searchY > 0 && (searchX != sourceX || searchY != sourceY)) {
            Grid.cells[searchY][searchX].highlightSearch();
        }
    }

    private void makeArrayGrid() {
        for (int row = 0; row < Main.getROWS(); row++) {
            for (int column = 0; column < Main.getCOLUMNS(); column++) {
                if (Grid.cells[row][column].whichCellType().equals(Cell.CellState.WALL)) {
                    arrayGrid[column][row] = 1;
                } else if (Grid.cells[row][column].whichCellType().equals(Cell.CellState.START)) {
                    sourceX = column;
                    sourceY = row;
                    arrayGrid[column][row] = 2;
                } else if (Grid.cells[row][column].whichCellType().equals(Cell.CellState.FINISH)) {
                    destX = column;
                    destY = row;
                    arrayGrid[column][row] = 3;
                } else {
                    arrayGrid[column][row] = 0;
                }
            }
        }
    }
    private void pathExists(int[][] arrayGrid) {

        Node source = new Node(sourceX, sourceY, 0, sourceX, sourceY);
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
        int lastX = destX;
        int lastY = destY;

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
        }

    }

    public static Timeline getTimelineFinding() {
        return timelineFinding;
    }
}


class Node {
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
