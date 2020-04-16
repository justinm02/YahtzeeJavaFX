package Gameplay;

import java.io.FileNotFoundException;

public class YahtzeeGame {
    private int[] rolls;
    private int rollsRemaining;

    public YahtzeeGame() {
        rolls = new int[5];
        rollsRemaining = 3;
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
    }

    public int getRollsRemaining() {
        return rollsRemaining;
    }
}
