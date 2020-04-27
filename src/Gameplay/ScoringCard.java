package Gameplay;

import java.util.ArrayList;
import java.util.Arrays;

public class ScoringCard {
    private int ones;
    private int twos;
    private int threes;
    private int fours;
    private int fives;
    private int sixes;
    private int threeKind;
    private int fourKind;
    private int fullHouse;
    private int smallStraight;
    private int largeStraight;
    private int yahtzee;
    private int chance;
    private int pointsTotal;
    private int lowerSum;
    private int bonus;

    public ScoringCard() {
        ones = 0;
        twos = 0;
        threes = 0;
        fours = 0;
        fives = 0;
        sixes = 0;
        threeKind = 0;
        fourKind = 0;
        fullHouse = 0;
        smallStraight = 0;
        largeStraight = 0;
        yahtzee = 0;
        chance = 0;

        pointsTotal = 0;
        lowerSum = 0;
        bonus = 0;
    }

    public void setOnes(int[] rolls) {
        ones = getCount(rolls, 1) * 1;

        pointsTotal += ones;
        lowerSum += ones;
        setBonus();
    }

    public void setTwos(int[] rolls) {
        twos = getCount(rolls, 2) * 2;

        pointsTotal += twos;
        lowerSum += twos;
        setBonus();
    }

    public void setThrees(int[] rolls) {
        threes = getCount(rolls, 3) * 3;

        pointsTotal += threes;
        lowerSum += threes;
        setBonus();
    }

    public void setFours(int[] rolls) {
        fours = getCount(rolls, 4) * 4;

        pointsTotal += fours;
        lowerSum += fours;
        setBonus();
    }

    public void setFives(int[] rolls) {
        fives = getCount(rolls, 5) * 5;

        pointsTotal += fives;
        lowerSum += fives;
        setBonus();
    }

    public void setSixes(int[] rolls) {
        sixes = getCount(rolls, 6) * 6;

        pointsTotal += sixes;
        lowerSum += sixes;
        setBonus();
    }

    public void setThreeKind(int[] rolls) {
        boolean givePoints = false;
        int total = 0;

        for (int roll : rolls) {
            if (getCount(rolls, roll) >= 3) {
                givePoints = true;
            }
            total += roll;
        }

        threeKind = givePoints ? total : 0;

        pointsTotal += threeKind;
    }

    public void setFourKind(int[] rolls) {
        boolean givePoints = false;
        int total = 0;

        for (int roll : rolls) {
            if (getCount(rolls, roll) >= 4) {
                givePoints = true;
            }
            total += roll;
        }

        fourKind = (givePoints ? total : 0);

        pointsTotal += fourKind;
    }

    public void setFullHouse(int[] rolls) {
        ArrayList<Integer> rollsUniques = new ArrayList<>();

        for (int roll : rolls) {
            if (!rollsUniques.contains(roll)) {
                rollsUniques.add(roll);
            }
        }

        if (rollsUniques.size() != 2) {
            fullHouse = 0;
        } else {
            int firstNum = rollsUniques.get(0);
            int secondNum = rollsUniques.get(1);

            if ((getCount(rolls, firstNum) == 2 && getCount(rolls, secondNum) == 3) || (getCount(rolls, firstNum) == 3 && getCount(rolls, secondNum) == 2)) {
                fullHouse = 25;
            } else {
                fullHouse = 0;
            }
        }

        pointsTotal += fullHouse;
    }

    public void setSmallStraight(int[] rolls) {
        Arrays.sort(rolls);

        System.out.println(Arrays.toString(rolls));

        int ascendingCounter = 1;
        boolean isSmallStraight = false;
        int previousRoll = rolls[0];

        for (int i = 1; i < rolls.length; i++) {
            if (rolls[i] == previousRoll + 1) {
                ascendingCounter++;
            } else {
                ascendingCounter = 1;
            }

            if (ascendingCounter == 3) {
                isSmallStraight = true;
            }

            previousRoll = rolls[i];
        }

        smallStraight = (isSmallStraight ? 30 : 0);

        pointsTotal += smallStraight;
    }

    public void setLargeStraight(int[] rolls) {
        Arrays.sort(rolls);

        int ascendingCounter = 1;
        boolean isLargeStraight = false;
        int previousRoll = rolls[0];

        for (int i = 1; i < rolls.length; i++) {
            if (rolls[i] == previousRoll + 1) {
                ascendingCounter++;
            } else {
                ascendingCounter = 1;
            }

            if (ascendingCounter == 4) {
                isLargeStraight = true;
            }

            previousRoll = rolls[i];
        }

        largeStraight = (isLargeStraight ? 40 : 0);

        pointsTotal += largeStraight;
    }

    public void setYahtzee(int[] rolls) {
        if (getCount(rolls, rolls[0]) == 5) {
            yahtzee = 50;
        } else {
            yahtzee = 0;
        }

        pointsTotal += yahtzee;
    }

    public void setChance(int[] rolls) {
        int sum = 0;

        for (int roll : rolls) {
            sum += roll;
        }

        chance = sum;

        pointsTotal += chance;
    }

    public int getCount(int[] rolls, int num) {
        int count = 0;

        for (int roll : rolls) {
            if (roll == num) {
                count++;
            }
        }

        return count;
    }

    public void setBonus() {
        if (lowerSum >= 63) {
            bonus = 35;
        }
    }

    public void resetScores() {
        ones = 0;
        twos = 0;
        threes = 0;
        fours = 0;
        fives = 0;
        sixes = 0;
        threeKind = 0;
        fourKind = 0;
        fullHouse = 0;
        smallStraight = 0;
        largeStraight = 0;
        yahtzee = 0;
        chance = 0;

        pointsTotal = 0;
        lowerSum = 0;
        bonus = 0;
    }

    public void addBonus() {
        pointsTotal += bonus;
    }

    public int getOnes() {
        return ones;
    }

    public int getTwos() {
        return twos;
    }

    public int getThrees() {
        return threes;
    }

    public int getFours() {
        return fours;
    }

    public int getFives() {
        return fives;
    }

    public int getSixes() {
        return sixes;
    }

    public int getThreeKind() {
        return threeKind;
    }

    public int getFourKind() {
        return fourKind;
    }

    public int getFullHouse() {
        return fullHouse;
    }

    public int getSmallStraight() {
        return smallStraight;
    }

    public int getLargeStraight() {
        return largeStraight;
    }

    public int getYahtzee() {
        return yahtzee;
    }

    public int getChance() {
        return chance;
    }

    public int getPointsTotal() {
        return pointsTotal;
    }

    public int getLowerSum() {
        return lowerSum;
    }

    public int getBonus() {
        return bonus;
    }
}
