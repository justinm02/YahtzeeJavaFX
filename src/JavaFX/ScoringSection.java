package JavaFX;

import Gameplay.ScoringCard;
import Gameplay.YahtzeeGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class ScoringSection {
    private Stage primaryStage;
    private YahtzeeGame yahtzeeGame;
    private ScoringCard scoringCard;
    private DiceRollingSection diceRollingSection;

    private Button[] scoringButtons;

    public ScoringSection(Stage primaryStage, GridPane mainGrid, YahtzeeGame yahtzeeGame, DiceRollingSection diceRollingSection) {
        this.primaryStage = primaryStage;
        this.yahtzeeGame = yahtzeeGame;
        this.diceRollingSection = diceRollingSection;
        scoringCard = new ScoringCard();
        scoringButtons = new Button[13];

        String[] scoringOptions = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Three Of A Kind", "Four Of A Kind", "Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance"};

        initScoringUI(scoringOptions, mainGrid);
    }

    private void initScoringUI(String[] scoringOptions, GridPane mainGrid) {
        Text turnsRemaining = new Text("Turns Remaining: " + yahtzeeGame.getTurnsRemaining());
        turnsRemaining.setFont(new Font(16));

        mainGrid.add(turnsRemaining, 2, 8);

        addRestartFunctionality(mainGrid, turnsRemaining);

        addLowerScoring(scoringOptions, mainGrid, turnsRemaining);

        addUpperScoring(scoringOptions, mainGrid, turnsRemaining);
    }

    public void addRestartFunctionality(GridPane mainGrid, Text turnsRemaining) {
        Button restartButton = new Button("Restart");

        restartButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    scoringCard.resetScores();
                    yahtzeeGame.restartGame();
                    resetScoringButtons();
                    diceRollingSection.restart();

                    turnsRemaining.setText("Turns Remaining: " + yahtzeeGame.getTurnsRemaining());

                    primaryStage.show();
                }
            }
        );

        mainGrid.add(restartButton, 1, 8);
    }

    public void addLowerScoring(String[] scoringOptions, GridPane mainGrid, Text turnsRemaining) {
        Text bonusText = new Text("Bonus");
        Text lowerSumText = new Text("Lower Sum");
        Text bonusScore = new Text();
        Text lowerSumScore = new Text();

        bonusText.setFont(new Font(16));
        lowerSumText.setFont(new Font(16));
        bonusScore.setFont(new Font(16));
        lowerSumScore.setFont(new Font(16));

        mainGrid.add(bonusText, 0, 18);
        mainGrid.add(lowerSumText, 0, 19);
        mainGrid.add(bonusScore, 1, 18);
        mainGrid.add(lowerSumScore, 1, 19);

        for (int index = 0; index < 6; index++) {
            Text lowerScoringText = new Text(scoringOptions[index]);
            lowerScoringText.setFont(new Font(16));
            Button scoringButton = new Button("0");

            int scoringIndex = index;

            scoringButtons[scoringIndex] = scoringButton;

            scoringButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            scoringButtons[scoringIndex] = scoringButton;

                            updateScore(scoringButton, scoringOptions, scoringIndex);
                            scoringButton.setDisable(true);

                            bonusScore.setText(Integer.toString(scoringCard.getBonus()));
                            lowerSumScore.setText(Integer.toString(scoringCard.getLowerSum()));

                            yahtzeeGame.newTurn();
                            diceRollingSection.resetRollsRemaining();

                            turnsRemaining.setText("Turns Remaining: " + yahtzeeGame.getTurnsRemaining());

                            primaryStage.show();
                        }
                    }
            );

            mainGrid.add(lowerScoringText, 0, index + 12);
            mainGrid.add(scoringButton, 1, index + 12);
        }
    }

    public void addUpperScoring(String[] scoringOptions, GridPane mainGrid, Text turnsRemaining) {
        Text totalPoints = new Text("Total Points");
        Text totalPointsScore = new Text();

        totalPoints.setFont(new Font(16));
        totalPointsScore.setFont(new Font(16));

        mainGrid.add(totalPoints, 3, 19);
        mainGrid.add(totalPointsScore,  4, 19);

        for (int index = 6; index < scoringOptions.length; index++) {
            Text upperScoringText = new Text(scoringOptions[index]);
            upperScoringText.setFont(new Font(16));
            Button scoringButton = new Button("0");

            int scoringIndex = index;

            scoringButtons[scoringIndex] = scoringButton;

            scoringButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            updateScore(scoringButton, scoringOptions, scoringIndex);
                            scoringButton.setDisable(true);

                            totalPointsScore.setText(Integer.toString(scoringCard.getPointsTotal()));

                            yahtzeeGame.newTurn();
                            diceRollingSection.resetRollsRemaining();

                            turnsRemaining.setText("Turns Remaining: " + yahtzeeGame.getTurnsRemaining());

                            primaryStage.show();
                        }
                    }
            );

            mainGrid.add(upperScoringText, 3, index + 6);
            mainGrid.add(scoringButton, 4, index + 6);
        }
    }

    public void updateScore(Button scoringButton, String[] scoringOptions, int scoringIndex) {
        int[] rolls = yahtzeeGame.getRolls();

        if (scoringOptions[scoringIndex].equals("Ones")) {
            System.out.println("rolls: " + Arrays.toString(rolls));
            scoringCard.setOnes(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getOnes()));
            System.out.println("ones: " + scoringCard.getOnes());
        }
        else if (scoringOptions[scoringIndex].equals("Twos")) {
            scoringCard.setTwos(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getTwos()));
        }
        else if (scoringOptions[scoringIndex].equals("Threes")) {
            scoringCard.setThrees(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getThrees()));
        }
        else if (scoringOptions[scoringIndex].equals("Fours")) {
            scoringCard.setFours(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getFours()));
        }
        else if (scoringOptions[scoringIndex].equals("Fives")) {
            scoringCard.setFives(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getFives()));
        }
        else if (scoringOptions[scoringIndex].equals("Sixes")) {
            scoringCard.setSixes(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getSixes()));
        }
        else if (scoringOptions[scoringIndex].equals("Three Of A Kind")) {
            scoringCard.setThreeKind(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getThreeKind()));
        }
        else if (scoringOptions[scoringIndex].equals("Four Of A Kind")) {
            scoringCard.setFourKind(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getFourKind()));
        }
        else if (scoringOptions[scoringIndex].equals("Full House")) {
            scoringCard.setFullHouse(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getFullHouse()));
        }
        else if (scoringOptions[scoringIndex].equals("Small Straight")) {
            scoringCard.setSmallStraight(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getSmallStraight()));
        }
        else if (scoringOptions[scoringIndex].equals("Large Straight")) {
            scoringCard.setLargeStraight(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getLargeStraight()));
        }
        else if (scoringOptions[scoringIndex].equals("Yahtzee")) {
            scoringCard.setYahtzee(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getYahtzee()));
        }
        else if (scoringOptions[scoringIndex].equals("Chance")) {
            scoringCard.setChance(rolls);

            scoringButton.setText(Integer.toString(scoringCard.getChance()));
        }
    }

    private void resetScoringButtons() {
        for (Button scoringButton : scoringButtons) {
            System.out.println("scoringButton: " + scoringButton.getText());
            scoringButton.setDisable(false);
        }
    }
}
