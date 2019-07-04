package sample;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static sample.LayoutCreator.tileSize;

public class ScoreBoard {
    private Group group = new Group();
    private Rectangle grayBackground = new Rectangle();
    private Rectangle redBackground = new Rectangle();
    private Text grayScoreText = new Text();
    private Text redScoreText = new Text();
    private StackPane red = new StackPane();
    private StackPane gray = new StackPane();
    private Integer redScore = 0;
    private Integer grayScore = 0;

    public ScoreBoard() {
        redScoreText.setText("0");
        redScoreText.setFill(Paint.valueOf("#FFFFFF"));
        redBackground.setFill(Paint.valueOf("#760D0D"));
        redBackground.setWidth(tileSize);
        redBackground.setHeight(tileSize * 0.4);
        redBackground.setArcWidth(20);
        redBackground.setArcHeight(20);
        red.setAlignment(Pos.CENTER);
        red.relocate(1.5 * tileSize, 9.5 * tileSize);
        red.getChildren().addAll(redBackground, redScoreText);
        grayScoreText.setText("0");
        grayBackground.setFill(Paint.valueOf("#707070"));
        grayBackground.setWidth(tileSize);
        grayBackground.setHeight(tileSize * 0.4);
        grayBackground.setArcWidth(20);
        grayBackground.setArcHeight(20);
        gray.setAlignment(Pos.CENTER);
        gray.relocate(1.5 * tileSize, 10 * tileSize);
        gray.getChildren().addAll(grayBackground, grayScoreText);
        group.getChildren().addAll(red, gray);
    }

    public Group getGroup() {
        return group;
    }

    public void changeScore(PieceType pieceType) {
        if (pieceType == PieceType.Gray) {
            redScore++;
            redScoreText.setText(redScore.toString());
        } else {
            grayScore++;
            grayScoreText.setText(grayScore.toString());
        }
    }
}
