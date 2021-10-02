module main.pathfindingalgorithms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens main to javafx.fxml;
    exports main;
}