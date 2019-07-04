package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static sample.LayoutCreator.tileSize;

public class Piece extends StackPane {
    private double radius = tileSize * 0.4;
    double mouseX, mouseY;
    private double oldX, oldY;
    public PieceType pieceType;
    private Circle piece;

    public boolean isKing() {
        return isKing;
    }

    public void setKing() {
        isKing = true;
        this.piece.setFill(pieceType == PieceType.Red ? Color.valueOf("#DF0000") : Color.valueOf("#D1D1D1"));
    }

    private boolean isKing;

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(int x, int y, PieceType pieceType) {
        this.pieceType = pieceType;
        isKing = false;
        this.piece = new Circle(radius);
        move((x), (y));
        piece.setFill(pieceType == PieceType.Red ? Color.valueOf("#760D0D") : Color.valueOf("#707070"));

        piece.setTranslateX((tileSize - tileSize * 0.8) / 2);
        piece.setTranslateY((tileSize - tileSize * 0.8) / 2);
        getChildren().addAll(piece);
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() + oldX - mouseX, e.getSceneY() + oldY - mouseY);
        });
    }

    public void move(double x, double y) {
        oldX = (x + 1) * tileSize;
        oldY = (y + 1) * tileSize;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
