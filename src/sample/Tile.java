package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.LayoutCreator.tileSize;


public class Tile extends Rectangle {
    Piece piece;

    public Tile(int x, int y, boolean isLight) {
        setWidth(tileSize);
        setHeight(tileSize);
        setSmooth(true);
        relocate((x + 1) * tileSize, (y + 1) * tileSize);
        setFill(isLight ? Color.valueOf("#244152") : Color.valueOf("#1E1E1E"));
        setArcHeight(20);
        setArcWidth(20);
    }

    //FFE4E4
    //4BC04B
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

}
