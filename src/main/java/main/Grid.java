package main;

import javafx.scene.layout.Pane;

public class Grid extends Pane {

    private int y;
    private int x;

    private double width;
    private double height;

    public static Cell[][] cells = null;

    public Grid(int x, int y, double width, double height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        cells = new Cell[x][y];
    }

    /**
     * Add cell to array and to the UI.
     */
    public void add(Cell cell, int column, int row) {

        cells[column][row] = cell;

        double w = width / x;
        double h = height / y;
        double x = w * column;
        double y = h * row;

        cell.setLayoutX(x);
        cell.setLayoutY(y);
        cell.setPrefWidth(w);
        cell.setPrefHeight(h);

        getChildren().add(cell);
    }

    public Cell getCell(int column, int row) {
        return cells[row][column];
    }

    //Unhighlight All Cells
    public void unHighlightAll() {
        for (int row = 0; row < y; row++) {
            for (int column = 0; column < x; column++) {
                if (!(row == 0 || column == 0 || row == y - 1 || column == x - 1)) {
                    cells[row][column].unHighlight();
                }
            }
        }

        Main.getStatusField().setText("Cleared!");
        Main.setStartSet(false);
        Main.setFinishSet(false);
    }


}


