package JavaFX;

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

public class JavaFXYahtzee extends Application {

    public void start(Stage primaryStage) throws FileNotFoundException {
        GridPane diceGrid = createGrid();

        addDice(diceGrid);

        Scene scene = new Scene(diceGrid);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setMinSize(400, 200);
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(50);
        grid.setVgap(20);
        grid.setPadding(new Insets(10));
        return grid;
    }

    public static void addDice(GridPane diceGrid) throws FileNotFoundException {
        for (int i = 1; i <= 5; i++) {
            addFirstText(diceGrid, i);
            addSecondText(diceGrid, i);
            addDiceImage(diceGrid, i);
            addKeepCheckBox(diceGrid, i);
        }
    }

    public static void addFirstText(GridPane diceGrid, int i) {
        Text firstText = new Text("Dice " + i);
        firstText.setFont(new Font(12));

        diceGrid.add(firstText, 0, i);
    }

    public static void addSecondText(GridPane diceGrid, int i) {
        Text secondText = new Text(Integer.toString(i));
        secondText.setFont(new Font(12));

        diceGrid.add(secondText, 1, i);
    }

    public static void addDiceImage(GridPane diceGrid, int i) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("dice" + i + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);

        diceGrid.add(imageView, 2, i);
    }

    public static void addKeepCheckBox(GridPane diceGrid, int i) {
        CheckBox keepCheckBox = new CheckBox("Keep?");
        HBox hBox = new HBox(keepCheckBox);
        hBox.setAlignment(Pos.CENTER);

        diceGrid.add(hBox, 3, i);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

