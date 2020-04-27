package JavaFX;

import Gameplay.YahtzeeGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DiceRollingSection {

    private Stage primaryStage;
    private YahtzeeGame yahtzeeGame;
    private Dice[] dice;

    private Text rollsRemaining;
    private Button rollButton = new Button();

    public DiceRollingSection(Stage primaryStage, GridPane mainGrid, YahtzeeGame yahtzeeGame) {
        this.primaryStage = primaryStage;
        this.yahtzeeGame = yahtzeeGame;
        dice = new Dice[5];

        rollButton = new Button();
        rollsRemaining = new Text();

        try {
            initDiceUI(mainGrid);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initDiceUI(GridPane mainGrid) throws FileNotFoundException {
        yahtzeeGame.useRoll();

        //addWelcomeText(mainGrid);
        for (int row = 2; row <= 6; row++) {
            addDiceText(row, mainGrid);

            int diceRoll = yahtzeeGame.rollDice(row-2);

            Dice rolledDice = new Dice();
            dice[row-2] = rolledDice;

            addDiceDescription(rolledDice, diceRoll, row, mainGrid);
            addDiceImage(rolledDice, diceRoll, row, mainGrid);
            addRerollCheckBox(rolledDice, row, mainGrid);
        }
        addRollButton(mainGrid);
    }

    private void addWelcomeText(GridPane mainGrid) {
        Text welcomeText = new Text("Welcome to Yahtzee!");
        welcomeText.setFont(new Font(16));

        mainGrid.add(welcomeText, 2, 1);
    }

    private void addDiceText(int row, GridPane mainGrid) {
        Text diceText = new Text("Dice " + (row-1));
        diceText.setFont(new Font(16));

        mainGrid.add(diceText, 0, row);
    }

    private void addDiceDescription(Dice dice, int diceRoll, int row, GridPane mainGrid) {
        Text diceDescription = new Text(Integer.toString(diceRoll));
        diceDescription.setFont(new Font(16));

        dice.setDiceDescription(diceDescription);
        mainGrid.add(diceDescription, 1, row);
    }

    private void addDiceImage(Dice dice, int diceRoll, int row, GridPane mainGrid) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("dice" + diceRoll + ".png");
        Image diceImage = new Image(inputStream);
        ImageView diceImageView = new ImageView(diceImage);
        diceImageView.setFitHeight(50);
        diceImageView.setFitWidth(50);
        diceImageView.setPreserveRatio(true);

        dice.setDiceImageView(diceImageView);
        mainGrid.add(diceImageView, 2, row);
    }

    private void addRerollCheckBox(Dice dice, int row, GridPane mainGrid) {
        CheckBox rerollCheckBox = new CheckBox("Reroll?");
        rerollCheckBox.setFont(new Font(16));
        HBox hBox = new HBox(rerollCheckBox);
        hBox.setAlignment(Pos.CENTER);

        dice.setRerollCheckBox(rerollCheckBox);
        mainGrid.add(hBox, 3, row);
    }

    private void addRollButton(GridPane mainGrid) {
        rollButton.setText("Roll");
        rollsRemaining.setText("Rolls Remaining: " + yahtzeeGame.getRollsRemaining());
        rollsRemaining.setFont(new Font(16));

        rollButton.setOnAction(
                new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        roll(false);
                        primaryStage.show();
                        if (yahtzeeGame.getRollsRemaining() == 0) {
                            rollButton.setDisable(true);
                        }
                    }
                }
        );

        mainGrid.add(rollButton, 1, 7);
        mainGrid.add(rollsRemaining, 2, 7);
    }

    private void roll(boolean newTurn) {
        boolean useUpRoll = false;
        for (int index = 0; index < dice.length; index++) {
            Dice rolledDice = dice[index];
            if (rolledDice.getRerollState() || newTurn) {
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

    public void resetRollsRemaining() {
        if (yahtzeeGame.getTurnsRemaining() > 0) {
            rollButton.setDisable(false);
            roll(true);
        }
        else {
            yahtzeeGame.endGame();
            rollButton.setDisable(true);
            rollsRemaining.setText("Rolls Remaining: " + yahtzeeGame.getRollsRemaining());
        }
    }

    public void restart() {
        rollButton.setDisable(false);
        rollsRemaining.setText("Rolls Remaining: " + yahtzeeGame.getRollsRemaining());
        roll(true);
    }
}
