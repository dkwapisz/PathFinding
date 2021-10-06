package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.algorithms.BFS;
import main.algorithms.Dijkstra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {

    private final static int COLUMNS = 10;
    private final static int ROWS = 10;
    private final double WIDTH = 1200;
    private final double HEIGHT = 900;

    private final StackPane root = new StackPane();
    private final Grid grid = new Grid(COLUMNS, ROWS, WIDTH - 200, HEIGHT);
    private final MouseAction mouseAction = new MouseAction();
    private final Button saveButton = new Button("Save");
    private final Button loadButton = new Button("Load");
    private final Button startButton = new Button("START");
    private final Button clearButton = new Button("Clear all");
    private static final RadioButton wallOption = new RadioButton("Wall ");
    private static final RadioButton startOption = new RadioButton("Start");
    private static final RadioButton finishOption = new RadioButton("Finish");
    private static final TextField statusField = new TextField("Ready!");
    private static final ComboBox algorithmType = new ComboBox();
    private static boolean startSet;
    private static boolean finishSet;
    private final ToggleGroup cellOption = new ToggleGroup();

    @Override
    public void start(Stage stage) {
        setControls();

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {

                Cell cell = new Cell(column, row);

                if (row == 0 || column == 0 || row == ROWS - 1 || column == COLUMNS - 1) {
                    cell.highlight("wall");
                }

                mouseAction.makePaintable(cell);
                grid.add(cell, column, row);

            }
        }

        root.getChildren().add(grid);
        root.getChildren().add(saveButton);
        root.getChildren().add(loadButton);
        root.getChildren().add(startButton);
        root.getChildren().add(clearButton);
        root.getChildren().add(wallOption);
        root.getChildren().add(startOption);
        root.getChildren().add(finishOption);
        root.getChildren().add(statusField);
        root.getChildren().add(algorithmType);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("mainStyle.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
    }

    private void setControls() {
        wallOption.setToggleGroup(cellOption);
        wallOption.setSelected(true);
        startOption.setToggleGroup(cellOption);
        finishOption.setToggleGroup(cellOption);
        statusField.setEditable(false);
        algorithmType.getItems().addAll("BFS", "Dijkstra");

        saveButton.setOnAction(event -> {
            try {
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loadButton.setOnAction(event -> {
            try {
                loadFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        startButton.setOnAction(event -> {
            if (algorithmType.getValue() != null) {
                if (algorithmType.getValue().equals("BFS")) {
                    new BFS();
                } else if (algorithmType.getValue().equals("Dijkstra")) {
                    new Dijkstra();
                }
            } else {
                statusField.setText("Choose type!");
            }

        });

        clearButton.setOnAction(event -> {
            BFS.stopTimelines();
            grid.unHighlightAll();
        });

        saveButton.setId("saveButton");
        loadButton.setId("loadButton");
        wallOption.setId("wallOption");
        startOption.setId("startOption");
        finishOption.setId("finishOption");
        statusField.setId("statusField");
        algorithmType.setId("algorithmType");
        startButton.setId("startButton");
        clearButton.setId("clearButton");

    }

    private void saveFile() throws IOException {
        int size = Grid.cells.length*Grid.cells[0].length;
        int[] tab = new int[size];
        int i=0;

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (Grid.cells[row][column].whichCellType().equals(Cell.CellState.WALL)) {
                    tab[i] = 1;
                } else if (Grid.cells[row][column].whichCellType().equals(Cell.CellState.START)) {
                    tab[i] = 2;
                } else if (Grid.cells[row][column].whichCellType().equals(Cell.CellState.FINISH)) {
                    tab[i] = 3;
                } else {
                    tab[i] = 0;
                }
                i++;
            }
        }

        FileWriter writer = new FileWriter("src/main/resources/main/savedBoard" + ROWS + ".txt");
        for (int j = 0; j < size; j++) {
            writer.write(tab[j] + System.lineSeparator());
        }
        writer.close();
    }

    private void loadFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/main/savedBoard" + ROWS + ".txt"));
        int actualValue;

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {

                if (!scanner.hasNextLine()) {
                    scanner.close();
                    return;
                }

                actualValue = Integer.parseInt(scanner.nextLine());

                if (actualValue == 1) {
                    Grid.cells[row][column].highlight("wall");
                } else if (actualValue == 2) {
                    Grid.cells[row][column].highlight("start");
                } else if (actualValue == 3) {
                    Grid.cells[row][column].highlight("finish");
                } else {
                    Grid.cells[row][column].unHighlight();
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
    public static TextField getStatusField() {
        return statusField;
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

    public static int getCOLUMNS() {
        return COLUMNS;
    }
    public static int getROWS() {
        return ROWS;
    }
}
