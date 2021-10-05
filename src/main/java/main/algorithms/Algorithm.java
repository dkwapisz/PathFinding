package main.algorithms;

import main.Cell;
import main.Grid;
import main.Main;

public class Algorithm {

    private final int[][] arrayGrid;
    private int sourceX;
    private int sourceY;
    private int destX;
    private int destY;

    public Algorithm() {
        this.arrayGrid = new int[Main.getCOLUMNS()][Main.getROWS()];
        makeArrayGrid();
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

    public int[][] getArrayGrid() {
        return arrayGrid;
    }

    public int getSourceX() {
        return sourceX;
    }
    public int getSourceY() {
        return sourceY;
    }

    public int getDestX() {
        return destX;
    }
    public int getDestY() {
        return destY;
    }
}
