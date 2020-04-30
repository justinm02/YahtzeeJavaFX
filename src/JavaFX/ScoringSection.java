package JavaFX;

import Gameplay.ScoringCard;
import Gameplay.YahtzeeGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

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

        Text scoringCardTitle = new Text("Scoring Card");
        scoringCardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        scoringCardTitle.setUnderline(true);

        mainGrid.add(turnsRemaining, 2, 8);
        mainGrid.add(scoringCardTitle, 2, 11);

        Text totalPointsScore = new Text();

        addRestartFunctionality(mainGrid, turnsRemaining);

        addLowerScoring(scoringOptions, mainGrid, turnsRemaining, totalPointsScore);

        addUpperScoring(scoringOptions, mainGrid, turnsRemaining, totalPointsScore);

        addHighScores(mainGrid);
    }

    private void addRestartFunctionality(GridPane mainGrid, Text turnsRemaining) {
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
                }
            }
        );

        mainGrid.add(restartButton, 1, 8);
    }

    private void addLowerScoring(String[] scoringOptions, GridPane mainGrid, Text turnsRemaining, Text totalPointsScore) {
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
                            totalPointsScore.setText(Integer.toString(scoringCard.getPointsTotal()));

                            yahtzeeGame.newTurn();
                            diceRollingSection.resetRollsRemaining();

                            turnsRemaining.setText("Turns Remaining: " + yahtzeeGame.getTurnsRemaining());

                            updateHighScores(mainGrid);
                        }
                    }
            );

            mainGrid.add(lowerScoringText, 0, index + 12);
            mainGrid.add(scoringButton, 1, index + 12);
        }
    }

    private void addUpperScoring(String[] scoringOptions, GridPane mainGrid, Text turnsRemaining, Text totalPointsScore) {
        Text totalPoints = new Text("Total Points");

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

                            turnsRemaining.setText("Turns Remaining: " + yahtzeeGame.getTurnsRemaining());

                            diceRollingSection.resetRollsRemaining();

                            updateHighScores(mainGrid);
                        }
                    }
            );

            mainGrid.add(upperScoringText, 3, index + 6);
            mainGrid.add(scoringButton, 4, index + 6);
        }
    }

    private void addHighScores(GridPane mainGrid) {
        Text highScoreTitle = new Text("High Scores");
        highScoreTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        highScoreTitle.setUnderline(true);

        mainGrid.add(highScoreTitle, 10, 1);

        try(BufferedReader bReader = new BufferedReader(new FileReader("highscores.txt"))) {
            String playerLine = bReader.readLine();

            int row = 2;
            while (playerLine != null) {
                Scanner playerScanner = new Scanner(playerLine);
                String playerName = playerScanner.next();
                Integer playerScore = playerScanner.nextInt();

                Text playerPlace = new Text(row-1 + ")");
                Text playerNameText = new Text(playerName);
                Text playerScoreText = new Text(Integer.toString(playerScore));

                playerPlace.setFont(new Font(16));
                playerNameText.setFont(new Font(16));
                playerScoreText.setFont(new Font(16));

                mainGrid.add(playerPlace, 9, row);
                mainGrid.add(playerNameText, 10, row);
                mainGrid.add(playerScoreText, 11, row);

                playerLine = bReader.readLine();
                row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateScore(Button scoringButton, String[] scoringOptions, int scoringIndex) {
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
            scoringButton.setDisable(false);
            scoringButton.setText("0");
        }
    }

    private void updateHighScores(GridPane mainGrid) {
        if (yahtzeeGame.getTurnsRemaining() == 0) {
            String playerName = getPlayerName();

            try (BufferedReader bReader = new BufferedReader(new FileReader("highscores.txt"))) {
                Map<Integer, String> playerMap = new TreeMap<Integer, String>();
                ArrayList<Integer> highScores = new ArrayList<Integer>();

                String playerInfo = bReader.readLine();

                while (playerInfo != null) {
                    Scanner playerScanner = new Scanner(playerInfo);
                    String name = playerScanner.next();
                    Integer score = playerScanner.nextInt();
                    highScores.add(score);
                    playerMap.put(score, name);
                    System.out.println(playerMap.toString() + " test");

                    playerInfo = bReader.readLine();
                }
                playerMap.put(scoringCard.getPointsTotal(), playerName);
                highScores.add(scoringCard.getPointsTotal());

                Collections.sort(highScores, Collections.reverseOrder());
                int[] newHighScores = new int[Math.min(highScores.size(), 5)];
                String[] newHighScorePlayers = new String[Math.min(highScores.size(), 5)];

                for (int i = 0; i < newHighScorePlayers.length; i++) {
                    int highScore = highScores.get(i);

                    newHighScores[i] = highScore;
                    newHighScorePlayers[i] = playerMap.get(highScore);
                }

                FileWriter fileWriter = new FileWriter("highscores.txt");
                fileWriter.write("");

                fileWriter = new FileWriter("highscores.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter highScoresWriter = new PrintWriter(bufferedWriter);

                for (int i = 0; i < newHighScores.length; i++) {
                    highScoresWriter.println(newHighScorePlayers[i] + " " + newHighScores[i]);
                }
                highScoresWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //addHighScores(mainGrid);
        }
    }

    private String getPlayerName() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Nice score! Enter your name below!");
        textInputDialog.setTitle("Yahtzee Game Complete!");

        try {
            Image yahtzeeImage = new Image(new FileInputStream("yahtzeeGame.jpg"));
            ImageView yahtzeeImageView = new ImageView(yahtzeeImage);
            yahtzeeImageView.setFitHeight(100);
            yahtzeeImageView.setFitWidth(100);
            textInputDialog.setGraphic(yahtzeeImageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        textInputDialog.showAndWait();

        return textInputDialog.getEditor().getText();
    }
}
