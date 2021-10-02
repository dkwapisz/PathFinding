package main;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {

    private int column;
    private int row;
    private boolean highlight;

    public Cell(int column, int row) {

        this.column = column;
        this.row = row;

        getStyleClass().add("cell");
        setOpacity(0.9);
    }

    public void highlight() {
        // ensure the style is only once in the style list
        getStyleClass().remove("cell-highlight");
        // add style
        getStyleClass().add("cell-highlight");
        highlight = true;
    }

    public void unhighlight() {
        getStyleClass().remove("cell-highlight");
        highlight = false;
    }

    public void hoverHighlight() {
        // ensure the style is only once in the style list
        getStyleClass().remove("cell-hover-highlight");

        // add style
        getStyleClass().add("cell-hover-highlight");
    }

    public void hoverUnhighlight() {
        getStyleClass().remove("cell-hover-highlight");
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
    public boolean isHighlight() {
        return highlight;
    }
}


