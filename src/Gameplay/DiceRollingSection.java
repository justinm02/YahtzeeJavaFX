package Gameplay;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DiceRollingSection {

    private Stage primaryStage;
    private GridPane diceGrid;
    private YahtzeeGame yahtzeeGame;
    private Dice[] dice;

    private Text rollsRemaining;

    public DiceRollingSection(Stage primaryStage) {
        this.primaryStage = primaryStage;
        diceGrid = createGrid();
        yahtzeeGame = new YahtzeeGame();
        dice = new Dice[5];
        rollsRemaining = new Text();

        try {
            initDiceUI();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(diceGrid);

        primaryStage.setScene(scene);
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setMinSize(400, 200);
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(40);
        grid.setVgap(20);
        grid.setPadding(new Insets(10));
        return grid;
    }

    private void initDiceUI() throws FileNotFoundException {
        yahtzeeGame.useRoll();
        for (int row = 1; row <= 5; row++) {
            addDiceText(row);

            int diceRoll = yahtzeeGame.rollDice(row-1);

            Dice rolledDice = new Dice();
            dice[row-1] = rolledDice;

            addDiceDescription(rolledDice, diceRoll, row);
            addDiceImage(rolledDice, diceRoll, row);
            addRerollCheckBox(rolledDice, row);
        }
        addRollButton();
    }

    private void addDiceText(int row) {
        Text firstText = new Text("Dice " + row);
        firstText.setFont(new Font(12));

        diceGrid.add(firstText, 0, row);
    }

    private void addDiceDescription(Dice dice, int diceRoll, int row) {
        Text diceDescription = new Text(Integer.toString(diceRoll));
        diceDescription.setFont(new Font(12));

        dice.setDiceDescription(diceDescription);
        diceGrid.add(diceDescription, 1, row);
    }

    private void addDiceImage(Dice dice, int diceRoll, int row) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("dice" + diceRoll + ".png");
        Image diceImage = new Image(inputStream);
        ImageView diceImageView = new ImageView(diceImage);
        diceImageView.setFitHeight(50);
        diceImageView.setFitWidth(50);
        diceImageView.setPreserveRatio(true);

        dice.setDiceImageView(diceImageView);
        diceGrid.add(diceImageView, 2, row);
    }

    private void addRerollCheckBox(Dice dice, int row) {
        CheckBox rerollCheckBox = new CheckBox("Reroll?");
        HBox hBox = new HBox(rerollCheckBox);
        hBox.setAlignment(Pos.CENTER);

        dice.setRerollCheckBox(rerollCheckBox);
        diceGrid.add(hBox, 3, row);
    }

    private void addRollButton() {
        Button rollButton = new Button("Roll");
        rollsRemaining.setText("Rolls Remaining: " + yahtzeeGame.getRollsRemaining());
        rollsRemaining.setFont(new Font(12));

        rollButton.setOnAction(
                new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        roll();
                        primaryStage.show();
                        if (yahtzeeGame.getRollsRemaining() == 0) {
                            rollButton.setDisable(true);
                        }
                    }
                }
        );

        diceGrid.add(rollButton, 1, 6);
        diceGrid.add(rollsRemaining, 2, 6);
    }

    private void roll() {
        boolean useUpRoll = false;
        for (int index = 0; index < dice.length; index++) {
            Dice rolledDice = dice[index];
            if (rolledDice.getRerollState()) {
                useUpRoll = true;
                int diceRoll = yahtzeeGame.rollDice(index);

                Text diceDescription = rolledDice.getDiceDescription();
                diceDescription.setText((Integer.toString(diceRoll)));

                try {
                    Image diceImage = new Image(new FileInputStream("dice" + diceRoll + ".png"));
                    ImageView diceImageView = rolledDice.getDiceImageView();
                    diceImageView.setImage(diceImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (useUpRoll) {
            yahtzeeGame.useRoll();
        }
        rollsRemaining.setText("Rolls Remaining: " + yahtzeeGame.getRollsRemaining());
    }
}
