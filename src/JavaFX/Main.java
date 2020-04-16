package JavaFX;

import Gameplay.DiceRollingSection;
import javafx.application.Application;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Main extends Application {

    public void start(Stage primaryStage) {
        DiceRollingSection dice = new DiceRollingSection(primaryStage);
        primaryStage.show();
    }
}

