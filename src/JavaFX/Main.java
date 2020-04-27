package JavaFX;

import Gameplay.YahtzeeGame;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {
        GridPane mainGrid = new GridPane();
        mainGrid.setHgap(40);
        mainGrid.setVgap(20);
        mainGrid.setPadding(new Insets(10, 10, 10, 10));
        mainGrid.setMinSize(1000, 1000);

        YahtzeeGame yahtzeeGame = new YahtzeeGame();

        DiceRollingSection diceRollingSection = new DiceRollingSection(primaryStage, mainGrid, yahtzeeGame);
        ScoringSection scoringSection = new ScoringSection(primaryStage, mainGrid, yahtzeeGame, diceRollingSection);

        Scene scene = new Scene(mainGrid, 1000, 1000);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

