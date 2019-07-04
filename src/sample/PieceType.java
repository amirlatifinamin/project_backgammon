package sample;

public enum PieceType {
    Red(-1), Gray(1);

    int moveDirection;

    PieceType(int moveDirection) {
        this.moveDirection = moveDirection;
    }
}
