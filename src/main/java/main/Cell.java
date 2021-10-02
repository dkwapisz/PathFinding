package main;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {

    private int column;
    private int row;
    private CellState cellState;

    public Cell(int column, int row) {

        this.column = column;
        this.row = row;

        cellState = CellState.BLANK;
        getStyleClass().add("cell");
        setOpacity(0.9);
    }

    public void highlight() {

        if (Main.getWallOption().isSelected() && cellState != CellState.START && cellState != CellState.FINISH) {
            getStyleClass().remove("cell-wall");
            getStyleClass().add("cell-wall");
            cellState = CellState.WALL;
        }
        if (Main.getStartOption().isSelected() && !Main.isStartSet()) {
            getStyleClass().remove("cell-start");
            getStyleClass().add("cell-start");
            cellState = CellState.START;
            Main.setStartSet(true);
        }
        if (Main.getFinishOption().isSelected() && !Main.isFinishSet()) {
            getStyleClass().remove("cell-finish");
            getStyleClass().add("cell-finish");
            cellState = CellState.FINISH;
            Main.setFinishSet(true);
        }

    }


    public void highlight(String cellType) {

        switch (cellType) {
            case "wall" -> {
                getStyleClass().remove("cell-wall");
                getStyleClass().add("cell-wall");
                cellState = CellState.WALL;
            }
            case "start" -> {
                getStyleClass().remove("cell-start");
                getStyleClass().add("cell-start");
                cellState = CellState.START;
                Main.setStartSet(true);
            }
            case "finish" -> {
                getStyleClass().remove("cell-finish");
                getStyleClass().add("cell-finish");
                cellState = CellState.FINISH;
                Main.setFinishSet(true);
            }
        }
    }

    public void unHighlight() {

        getStyleClass().remove("cell-wall");
        getStyleClass().remove("cell-start");
        getStyleClass().remove("cell-finish");
        cellState = CellState.BLANK;

    }

    public void hoverHighlight() {

        if (Main.getWallOption().isSelected()) {
            getStyleClass().remove("cell-hover-wall");
            getStyleClass().add("cell-hover-wall");
        } else if (Main.getStartOption().isSelected()) {
            getStyleClass().remove("cell-hover-start");
            getStyleClass().add("cell-hover-start");
        } else {
            getStyleClass().remove("cell-hover-finish");
            getStyleClass().add("cell-hover-finish");
        }

    }

    public void hoverUnhighlight() {

        if (Main.getWallOption().isSelected()) {
            getStyleClass().remove("cell-hover-wall");
        } else if (Main.getStartOption().isSelected()) {
            getStyleClass().remove("cell-hover-start");
        } else {
            getStyleClass().remove("cell-hover-finish");
        }

    }

    public String toString() {
        return this.column + "/" + this.row;
    }

    public int getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    public CellState whichCellType() {return cellState;}

    public enum CellState {
        BLANK,
        WALL,
        START,
        FINISH,
        VISITED,
        PATH
    }
}


