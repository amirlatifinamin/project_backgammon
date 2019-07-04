package sample;

import javafx.scene.Group;
import javafx.scene.control.Alert;

import static sample.LayoutCreator.tileSize;


public class Controller {

    public Piece makeHandlePiece(int x, int y, PieceType pieceType, Tile[][] boardTiles,
                                 Group redPieces, Group grayPieces, ScoreBoard scoreBoard, TurnManger turnManger, Timer timer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Finished");
        alert.setHeaderText("The game is finished");
        Piece piece = new Piece(x, y, pieceType);
        piece.setOnMouseReleased(e -> {
            int newXPosition = findPieceBoardPosition(e.getSceneX());
            int newYPosition = findPieceBoardPosition(e.getSceneY());
            int oldXPosition = findPieceBoardPosition(piece.getOldX());
            int oldYPosition = findPieceBoardPosition(piece.getOldY());

            MoveType moveType = findMoveType(newXPosition, newYPosition, piece, boardTiles, turnManger.getTurn());
            switch (moveType) {
                case None:
                    piece.abortMove();
                    break;
                case Normal:
                    boardTiles[newXPosition][newYPosition].setPiece(piece);
                    boardTiles[oldXPosition][oldYPosition].setPiece(null);
                    piece.move(newXPosition, newYPosition);
                    if (newYPosition == 0 || newYPosition == 7) {
                        piece.setKing();
                    }
                    turnManger.changeTurn();
                    break;
                case Kill:
                    Piece killedPiece = boardTiles[(newXPosition + oldXPosition) / 2][(newYPosition + oldYPosition) / 2].getPiece();
                    boardTiles[(newXPosition + oldXPosition) / 2][(newYPosition + oldYPosition) / 2].setPiece(null);
                    boardTiles[newXPosition][newYPosition].setPiece(piece);
                    boardTiles[oldXPosition][oldYPosition].setPiece(null);
                    if (newYPosition == 0 || newYPosition == 7) {
                        piece.setKing();
                    }
                    if (killedPiece.pieceType == PieceType.Gray) {
                        grayPieces.getChildren().remove(killedPiece);
                        if (grayPieces.getChildren().isEmpty()) {
                            timer.stop();
                            alert.setContentText("Red is the winner");
                            alert.show();
                        }
                    } else {
                        redPieces.getChildren().remove(killedPiece);
                        if (redPieces.getChildren().isEmpty()) {
                            alert.setContentText("Gray is the winner");
                            alert.show();
                            timer.stop();
                        }
                    }

                    piece.move(newXPosition, newYPosition);
                    scoreBoard.changeScore(killedPiece.pieceType);
                    if (shouldChangeTurn(piece, newXPosition, newYPosition, boardTiles)) {
                        turnManger.changeTurn();
                    }
                    break;
            }
        });
        return piece;
    }

    public MoveType findMoveType(int newX, int newY, Piece piece, Tile[][] boardTiles, int turn) {
        int oldXPosition = findPieceBoardPosition(piece.getOldX());
        int oldYPosition = findPieceBoardPosition(piece.getOldY());

        if (piece.pieceType.moveDirection != turn) {
            return MoveType.None;
        }

        if (newX < 0 || newX >= tileSize || newY < 0 || newY >= tileSize) {
            return MoveType.None;
        }

        if (boardTiles[newX][newY].hasPiece()) {
            return MoveType.None;
        }

        if ((Math.abs(oldXPosition - newX) == 1) && (Math.abs(newY - oldYPosition) == 1)) {
            if (piece.isKing() || (newY - oldYPosition == piece.pieceType.moveDirection)) {
                return MoveType.Normal;
            }
        }
        if ((Math.abs(oldXPosition - newX)) == 2 && (Math.abs(newY - oldYPosition) == 2)
                && (boardTiles[(newX + oldXPosition) / 2][(newY + oldYPosition) / 2].getPiece().pieceType != piece.pieceType)) {
            if (piece.isKing() || (newY - oldYPosition == piece.pieceType.moveDirection * 2)) {
                return MoveType.Kill;
            }
        }
        return MoveType.None;
    }

    public int findPieceBoardPosition(double coordinate) {
        return (int) (coordinate) / tileSize - 1;
    }

    public boolean shouldChangeTurn(Piece piece, int newX, int newY, Tile[][] boardTiles) {
        if (!piece.isKing()) {
            if ((piece.pieceType == PieceType.Gray && newY == 6) || (piece.pieceType == PieceType.Red && newY == 1)) {
                return true;
            }
            if (newX <= 1) {
                if (boardTiles[newX + 2][newY + 2 * piece.pieceType.moveDirection].hasPiece() ||
                        !boardTiles[newX + 1][newY + piece.pieceType.moveDirection].hasPiece() ||
                        boardTiles[newX + 1][newY + piece.pieceType.moveDirection].getPiece().pieceType == piece.pieceType) {
                    return true;
                }
            } else if (newX >= 6) {
                if (boardTiles[newX - 2][newY + 2 * piece.pieceType.moveDirection].hasPiece() ||
                        !boardTiles[newX - 1][newY + piece.pieceType.moveDirection].hasPiece() ||
                        boardTiles[newX - 1][newY + piece.pieceType.moveDirection].getPiece().pieceType == piece.pieceType) {
                    return true;
                }
            } else {
                if ((boardTiles[newX + 2][newY + 2 * piece.pieceType.moveDirection].hasPiece() ||
                        !boardTiles[newX + 1][newY + piece.pieceType.moveDirection].hasPiece() ||
                        boardTiles[newX + 1][newY + piece.pieceType.moveDirection].getPiece().pieceType == piece.pieceType)
                        && (boardTiles[newX - 2][newY + 2 * piece.pieceType.moveDirection].hasPiece() ||
                        !boardTiles[newX - 1][newY + piece.pieceType.moveDirection].hasPiece() ||
                        boardTiles[newX - 1][newY + piece.pieceType.moveDirection].getPiece().pieceType == piece.pieceType)) {
                    return true;
                }
            }
        } else {
            if (newY <= 1) {
                if (newX <= 1) {
                    if (boardTiles[newX + 2][newY + 2].hasPiece() || !boardTiles[newX + 1][newY + 1].hasPiece() ||
                            boardTiles[newX + 1][newY + 1].getPiece().pieceType == piece.pieceType) {
                        return true;
                    }
                } else if (newX >= 6) {
                    if (boardTiles[newX - 2][newY + 2].hasPiece() || !boardTiles[newX - 1][newY + 1].hasPiece() ||
                            boardTiles[newX - 1][newY + 1].getPiece().pieceType == piece.pieceType) {
                        return true;
                    }
                } else {
                    if ((boardTiles[newX - 2][newY + 2].hasPiece() || !boardTiles[newX - 1][newY + 1].hasPiece() ||
                            boardTiles[newX - 1][newY + 1].getPiece().pieceType == piece.pieceType)
                            && (boardTiles[newX + 2][newY + 2].hasPiece() || !boardTiles[newX + 1][newY + 1].hasPiece() ||
                            boardTiles[newX + 1][newY + 1].getPiece().pieceType == piece.pieceType)) {
                        return true;
                    }
                }
            } else if (newY >= 6) {
                if (newX <= 1) {
                    if (boardTiles[newX + 2][newY - 2].hasPiece() || !boardTiles[newX + 1][newY - 1].hasPiece() ||
                            boardTiles[newX + 1][newY - 1].getPiece().pieceType == piece.pieceType) {
                        return true;
                    }
                } else if (newX >= 6) {
                    if (boardTiles[newX - 2][newY - 2].hasPiece() || !boardTiles[newX - 1][newY - 1].hasPiece() ||
                            boardTiles[newX - 1][newY - 1].getPiece().pieceType == piece.pieceType) {
                        return true;
                    }
                } else {
                    if ((boardTiles[newX + 2][newY - 2].hasPiece() || !boardTiles[newX + 1][newY - 1].hasPiece() ||
                            boardTiles[newX + 1][newY - 1].getPiece().pieceType == piece.pieceType)
                            && (boardTiles[newX - 2][newY - 2].hasPiece() || !boardTiles[newX - 1][newY - 1].hasPiece() ||
                            boardTiles[newX - 1][newY - 1].getPiece().pieceType == piece.pieceType)) {
                        return true;
                    }
                }
            } else {
                if (newX <= 1) {
                    if ((boardTiles[newX + 2][newY - 2].hasPiece() || !boardTiles[newX + 1][newY - 1].hasPiece() ||
                            boardTiles[newX + 1][newY - 1].getPiece().pieceType == piece.pieceType)
                            && (boardTiles[newX + 2][newY + 2].hasPiece() || !boardTiles[newX + 1][newY + 1].hasPiece() ||
                            boardTiles[newX + 1][newY + 1].getPiece().pieceType == piece.pieceType)) {
                        return true;
                    }
                } else if (newX >= 6) {
                    if ((boardTiles[newX - 2][newY - 2].hasPiece() || !boardTiles[newX - 1][newY - 1].hasPiece() ||
                            boardTiles[newX - 1][newY - 1].getPiece().pieceType == piece.pieceType)
                            && (boardTiles[newX - 2][newY + 2].hasPiece() || !boardTiles[newX - 1][newY + 1].hasPiece() ||
                            boardTiles[newX - 1][newY + 1].getPiece().pieceType == piece.pieceType)) {
                        return true;
                    }
                } else {
                    if ((boardTiles[newX - 2][newY - 2].hasPiece() || !boardTiles[newX - 1][newY - 1].hasPiece() ||
                            boardTiles[newX - 1][newY - 1].getPiece().pieceType == piece.pieceType)
                            && (boardTiles[newX - 2][newY + 2].hasPiece() || !boardTiles[newX - 1][newY + 1].hasPiece() ||
                            boardTiles[newX - 1][newY + 1].getPiece().pieceType == piece.pieceType)
                            && (boardTiles[newX + 2][newY - 2].hasPiece() || !boardTiles[newX + 1][newY - 1].hasPiece() ||
                            boardTiles[newX + 1][newY - 1].getPiece().pieceType == piece.pieceType)
                            && (boardTiles[newX + 2][newY + 2].hasPiece() || !boardTiles[newX + 1][newY + 1].hasPiece() ||
                            boardTiles[newX + 1][newY + 1].getPiece().pieceType == piece.pieceType)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}



