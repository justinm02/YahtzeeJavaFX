package JavaFX;

import Gameplay.YahtzeeGame;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private String playerName;

    public void start(Stage primaryStage) {
        GridPane mainGrid = new GridPane();
        mainGrid.setHgap(40);
        mainGrid.setVgap(20);
        mainGrid.setPadding(new Insets(10, 10, 10, 10));

        YahtzeeGame yahtzeeGame = new YahtzeeGame();

        DiceRollingSection diceRollingSection = new DiceRollingSection(primaryStage, mainGrid, yahtzeeGame);
        ScoringSection scoringSection = new ScoringSection(primaryStage, mainGrid, yahtzeeGame, diceRollingSection);

        Scene scene = new Scene(mainGrid, 1200, 1200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch();
    }
}

