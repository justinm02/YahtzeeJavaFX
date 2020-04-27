package Gameplay;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class YahtzeeGame {
    private int[] rolls;
    private int rollsRemaining;
    private int turnsRemaining;

    public YahtzeeGame() {
        rolls = new int[5];
        rollsRemaining = 3;
        turnsRemaining = 13;
    }

    public int rollDice(int diceIndex) {
        rolls[diceIndex] = getRNG();

        return rolls[diceIndex];
    }

    public int getRNG() {
        return 1 + (int)(Math.random()*6);
    }

    public void useRoll() {
        rollsRemaining--;
    }

    public void newTurn() {
        rollsRemaining = 3;
        turnsRemaining--;
    }

    public void endGame() {
        rollsRemaining = 0;
    }

    public void restartGame() {
        rollsRemaining = 3;
        turnsRemaining = 13;
    }

    public int[] getRolls() {
        return rolls;
    }

    public int getRollsRemaining() {
        return rollsRemaining;
    }

    public int getTurnsRemaining() {
        return turnsRemaining;
    }
}
