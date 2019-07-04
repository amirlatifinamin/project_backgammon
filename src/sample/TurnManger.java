package sample;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static sample.LayoutCreator.tileSize;


public class TurnManger {
    private int turn;
    private Group toggles = new Group();
    private Circle redTurnToggle = new Circle();
    private Circle grayTurnToggle = new Circle();

    public TurnManger() {
        turn = 1;
        redTurnToggle.setRadius(8);
        redTurnToggle.setFill(Paint.valueOf("#21242E"));
        redTurnToggle.relocate(1.1 * tileSize, 9.5 * tileSize + 4);
        grayTurnToggle.setRadius(8);
        grayTurnToggle.setFill(Paint.valueOf("#707070"));
        grayTurnToggle.relocate(1.1 * tileSize, 10 * tileSize + 4);
        toggles.getChildren().addAll(redTurnToggle, grayTurnToggle);
    }

    public Group getToggles() {
        return toggles;
    }

    public int getTurn() {
        return turn;
    }

    public void changeTurn() {
        turn *= -1;
        redTurnToggle.setFill(Paint.valueOf(turn != 1 ? "#760D0D" : "#21242E"));
        grayTurnToggle.setFill(Paint.valueOf(turn != 1 ? "#21242E" : "#707070"));
    }
}
