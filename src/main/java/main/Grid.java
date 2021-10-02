package main;

import javafx.scene.layout.Pane;

public class Grid extends Pane {

    private int rows;
    private int columns;

    private double width;
    private double height;

    Cell[][] cells;

    public Grid(int columns, int rows, double width, double height) {

        this.columns = columns;
        this.rows = rows;
        this.width = width;
        this.height = height;

        cells = new Cell[rows][columns];
    }

    /**
     * Add cell to array and to the UI.
     */
    public void add(Cell cell, int column, int row) {

        cells[row][column] = cell;

        double w = width / columns;
        double h = height / rows;
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
        for (int row=0; row < rows; row++) {
            for (int col=0; col < columns; col++) {
                cells[row][col].unHighlight();
            }
        }
        Main.setStartSet(false);
        Main.setFinishSet(false);
    }
}


