package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {

    private int columns = 50;
    private int rows = 50;
    private double width = 1200;
    private double height = 800;

    private StackPane root = new StackPane();
    private Grid grid = new Grid(columns, rows, width - 200, height);
    private MouseAction mouseAction = new MouseAction();
    private Button saveButton = new Button("Save");
    private Button loadButton = new Button("Load");
    private Button startButton = new Button("START");
    private static RadioButton wallOption = new RadioButton("Wall ");
    private static RadioButton startOption = new RadioButton("Start");
    private static RadioButton finishOption = new RadioButton("Finish");
    private static boolean startSet;
    private static boolean finishSet;
    private ToggleGroup cellOption = new ToggleGroup();

    @Override
    public void start(Stage stage) {

        setButtons();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                Cell cell = new Cell(column, row);
                mouseAction.makePaintable(cell);
                grid.add(cell, column, row);

            }
        }

        root.getChildren().add(grid);
        root.getChildren().add(saveButton);
        root.getChildren().add(loadButton);
        root.getChildren().add(startButton);
        root.getChildren().add(wallOption);
        root.getChildren().add(startOption);
        root.getChildren().add(finishOption);

        // create scene and stage
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("mainStyle.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    private void setButtons() {
        wallOption.setToggleGroup(cellOption);
        wallOption.setSelected(true);
        startOption.setToggleGroup(cellOption);
        finishOption.setToggleGroup(cellOption);

        saveButton.setOnAction(event -> {
            try {
                saveFile(grid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loadButton.setOnAction(event -> {
            try {
                loadFile(grid);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        startButton.setOnAction(event -> {

        });

        //Button location
        saveButton.translateXProperty().setValue(450);
        saveButton.translateYProperty().setValue(-380);
        loadButton.translateXProperty().setValue(450);
        loadButton.translateYProperty().setValue(-340);
        wallOption.translateXProperty().setValue(450);
        wallOption.translateYProperty().setValue(-300);
        startOption.translateXProperty().setValue(450);
        startOption.translateYProperty().setValue(-260);
        finishOption.translateXProperty().setValue(450);
        finishOption.translateYProperty().setValue(-220);
        startButton.translateXProperty().setValue(450);
        startButton.translateYProperty().setValue(-180);

    }

    private void saveFile(Grid grid) throws IOException {
        int size = grid.cells.length*grid.cells[0].length;
        int[] tab = new int[size];
        int i=0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (grid.cells[row][column].whichCellType().equals(Cell.CellState.WALL)) {
                    tab[i] = 1;
                } else if (grid.cells[row][column].whichCellType().equals(Cell.CellState.START)) {
                    tab[i] = 2;
                } else if (grid.cells[row][column].whichCellType().equals(Cell.CellState.FINISH)) {
                    tab[i] = 3;
                } else {
                    tab[i] = 0;
                }
                i++;
            }
        }

        FileWriter writer = new FileWriter("src/main/resources/main/savedBoard.txt");
        for (int j = 0; j < size; j++) {
            writer.write(tab[j] + System.lineSeparator());
        }
        writer.close();
    }

    private void loadFile(Grid grid) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/main/savedBoard.txt"));
        int actualValue = 0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                if (!scanner.hasNextLine()) {
                    scanner.close();
                    return;
                }

                actualValue = Integer.parseInt(scanner.nextLine());

                if (actualValue == 1) {
                    grid.cells[row][column].highlight("wall");
                } else if (actualValue == 2) {
                    grid.cells[row][column].highlight("start");
                } else if (actualValue == 3) {
                    grid.cells[row][column].highlight("finish");
                } else {
                    grid.cells[row][column].unHighlight();
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static RadioButton getWallOption() {
        return wallOption;
    }
    public static RadioButton getStartOption() {
        return startOption;
    }
    public static RadioButton getFinishOption() {
        return finishOption;
    }

    public static boolean isStartSet() {
        return startSet;
    }
    public static boolean isFinishSet() {
        return finishSet;
    }

    public static void setStartSet(boolean startSet) {
        Main.startSet = startSet;
    }
    public static void setFinishSet(boolean finishSet) {
        Main.finishSet = finishSet;
    }
}
