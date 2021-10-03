package main.algorithms;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.Cell;
import main.Grid;
import main.Main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {

    private int[][] arrayGrid;
    private int sourceX;
    private int sourceY;
    private int searchX = -1;
    private int searchY = -1;
    private Timeline timeline;
    private int dots = 0;

    public BFS() {
        arrayGrid = new int[Main.getCOLUMNS()][Main.getROWS()];
        makeArrayGrid();
        pathExists(arrayGrid);
    }

    private void refreshGrid() {
        if (searchX > 0 && searchY > 0 && searchX != sourceX && searchY != sourceY) {
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
                } else if (Grid.cells[row][column].whichCellType().equals(Cell.CellState.FINISH)) {
                    arrayGrid[column][row] = 3;
                } else {
                    arrayGrid[column][row] = 0;
                }
            }
        }
    }
    private void pathExists(int[][] matrix) {

        Node source = new Node(sourceX, sourceY, 0);
        Queue<Node> queue = new LinkedList<>();

        queue.add(source);

        timeline = new Timeline(new KeyFrame(Duration.millis(5), e -> queue(queue, matrix)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void queue(Queue<Node> queue, int[][] matrix) {
        if (!queue.isEmpty()) {
            Node poped = queue.poll();
            textFieldUpdate();
            if (matrix[poped.x][poped.y] != 3) {
                matrix[poped.x][poped.y] = 1;
                searchX = poped.x;
                searchY = poped.y;
                refreshGrid();
                List<Node> neighbourList = addNeighbours(poped, arrayGrid);
                queue.addAll(neighbourList);
            } else if (matrix[poped.x][poped.y] == 3) {
                Main.getStatusField().setText("Path found!");
                timeline.stop();
            }
        } else {
            Main.getStatusField().setText("No path!");
            timeline.stop();
        }
    }

    private List<Node> addNeighbours(Node poped, int[][] arrayGrid) {

        List<Node> list = new LinkedList<>();

        if ((poped.x-1 > 0 && poped.x-1 < arrayGrid.length) && arrayGrid[poped.x-1][poped.y] != 1) {
            list.add(new Node(poped.x-1, poped.y, poped.distanceFromSource+1));
        }
        if ((poped.x+1 > 0 && poped.x+1 < arrayGrid.length) && arrayGrid[poped.x+1][poped.y] != 1) {
            list.add(new Node(poped.x+1, poped.y, poped.distanceFromSource+1));
        }
        if ((poped.y-1 > 0 && poped.y-1 < arrayGrid.length) && arrayGrid[poped.x][poped.y-1] != 1) {
            list.add(new Node(poped.x, poped.y-1, poped.distanceFromSource+1));
        }
        if ((poped.y+1 > 0 && poped.y+1 < arrayGrid.length) && arrayGrid[poped.x][poped.y+1] != 1) {
            list.add(new Node(poped.x, poped.y+1, poped.distanceFromSource+1));
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
}
class Node {
    int x;
    int y;
    int distanceFromSource;

    Node(int x, int y, int dis) {
        this.x = x;
        this.y = y;
        this.distanceFromSource = dis;
    }
}
