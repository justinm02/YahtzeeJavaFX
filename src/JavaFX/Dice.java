package JavaFX;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Dice {
    private Text diceDescription;
    private ImageView diceImageView;
    private CheckBox rerollCheckBox;

    public Dice() {
        diceDescription = new Text("1");

        try {
            Image diceImage = new Image(new FileInputStream("dice1.png"));
            diceImageView = new ImageView(diceImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        rerollCheckBox = new CheckBox();
    }

    public Dice(Text diceDescription, ImageView diceImageView, CheckBox rerollCheckBox) {
        this.diceDescription = diceDescription;
        this.diceImageView = diceImageView;
        this.rerollCheckBox = rerollCheckBox;
    }

    public Text getDiceDescription() {
        return diceDescription;
    }

    public void setDiceDescription(Text diceDescription) {
        this.diceDescription = diceDescription;
    }

    public ImageView getDiceImageView() {
        return diceImageView;
    }

    public void setDiceImageView(ImageView diceImageView) {
        this.diceImageView = diceImageView;
    }

    public boolean getRerollState() {
        return rerollCheckBox.isSelected();
    }

    public void setRerollCheckBox(CheckBox rerollCheckBox) {
        this.rerollCheckBox = rerollCheckBox;
    }
}
