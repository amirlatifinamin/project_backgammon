package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class LayoutCreator {
    Controller controller;
    public static int tileSize = 60;
    public static int width = 8;
    public static int height = 8;
    private Group tiles = new Group();
    private Group redPieces = new Group();
    private Group grayPieces = new Group();
    private Timer timer = new Timer(4, 9.5);
    private TurnManger turnManger = new TurnManger();
    private ScoreBoard scoreBoard = new ScoreBoard();
    private Tile[][] boardTiles = new Tile[width][height];

    public LayoutCreator(Controller controller) {
        this.controller = controller;
    }


    public Pane layout() {
        Pane board = new Pane();
        board.getChildren().add(turnManger.getToggles());
        board.getChildren().add(timer);
        board.getChildren().add(scoreBoard.getGroup());
        board.getChildren().addAll(tiles, redPieces, grayPieces);
        board.setPrefSize((width + 2) * tileSize, (height + 3) * tileSize);
        board.setStyle("-fx-background-color: #21242E");
        for (int width_index = 0; width_index < width; width_index++) {
            for (int height_index = 0; height_index < height; height_index++) {
                Tile newTile = new Tile(width_index, height_index, (width_index + height_index) % 2 == 0);
                boardTiles[width_index][height_index] = newTile;
                tiles.getChildren().add(newTile);
                if ((height_index <= 2) && (width_index + height_index) % 2 != 0) {
                    Piece newPiece = controller.makeHandlePiece(width_index, height_index, PieceType.Gray,
                            boardTiles, redPieces, grayPieces, scoreBoard, turnManger, timer);
                    boardTiles[width_index][height_index].setPiece(newPiece);
                    grayPieces.getChildren().add(newPiece);
                }
                if ((height_index >= 5) && (width_index + height_index) % 2 != 0) {
                    Piece newPiece = controller.makeHandlePiece(width_index, height_index, PieceType.Red,
                            boardTiles, redPieces, grayPieces, scoreBoard, turnManger, timer);
                    boardTiles[width_index][height_index].setPiece(newPiece);
                    redPieces.getChildren().add(newPiece);
                }
            }
        }
        return board;
    }
}
