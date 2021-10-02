package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private int columns = 20;
    private int rows = 20;
    private double width = 1200;
    private double height = 800;

    @Override
    public void start(Stage stage) {

        StackPane root = new StackPane();
        Grid grid = new Grid(columns, rows, width - 200, height);
        MouseAction mouseAction = new MouseAction();
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");

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

        //Button location
        saveButton.translateXProperty().setValue(450);
        saveButton.translateYProperty().setValue(-380);
        loadButton.translateXProperty().setValue(450);
        loadButton.translateYProperty().setValue(-340);

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

        // create scene and stage
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("mainStyle.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    private void saveFile(Grid grid) throws IOException {
        int size = grid.cells.length*grid.cells[0].length;
        int[] tab = new int[size];
        int i=0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (grid.cells[row][column].isWall().equals(Cell.CellState.WALL)) {
                    tab[i] = 1;
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
        writer.write(Arrays.toString(tab));
        writer.close();
    }

    private void loadFile(Grid grid) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/main/savedBoard.txt"));

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                if (!scanner.hasNextLine()) {
                    scanner.close();
                    return;
                }

                if (Integer.parseInt(scanner.nextLine()) == 1) {
                    grid.cells[row][column].highlight();
                } else {
                    grid.cells[row][column].unhighlight();
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
