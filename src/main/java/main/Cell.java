package main;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {

    private int y;
    private int x;
    private CellState cellState;

    public Cell(int x, int y) {

        this.y = y;
        this.x = x;

        cellState = CellState.BLANK;
        getStyleClass().add("cell");
        setOpacity(0.9);
    }

    public void highlight() {

        if (!(y == 0 || x == 0 || y == Main.getROWS() - 1 || x == Main.getCOLUMNS() - 1)) {
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

        if (!(y == 0 || x == 0 || y == Main.getROWS() - 1 || x == Main.getCOLUMNS() - 1)) {
            getStyleClass().remove("cell-wall");
            getStyleClass().remove("cell-start");
            getStyleClass().remove("cell-finish");
            getStyleClass().remove("cell-visited");
            getStyleClass().remove("cell-path");

            if (cellState == CellState.START) {
                Main.setStartSet(false);
            } else if (cellState == CellState.FINISH) {
                Main.setFinishSet(false);
            }

            cellState = CellState.BLANK;
        }

    }

    public void highlightSearch() {
        getStyleClass().remove("cell-visited");
        getStyleClass().add("cell-visited");
    }

    public void highlightPath() {
        getStyleClass().remove("cell-path");
        getStyleClass().add("cell-path");
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
        return this.x + "/" + this.y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
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


