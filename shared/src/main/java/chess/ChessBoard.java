package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessBoard() {
    }

    /**
     * Overloaded constructor for use when a new board must
     * be made with a custom arrangement of pieces
     */
    public ChessBoard(ChessPiece[][] squares) {
        this.squares = squares;
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.deepToString(squares) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (position.getRow() <= 8 && position.getRow() >= 1 && position.getColumn() <= 8 && position.getColumn() >= 1) {
            squares[position.getRow() - 1][position.getColumn() - 1] = piece;
        } else {
            throw new IllegalArgumentException("Given position is out of bounds.");
        }
    }

    /**
     * Removes a chess piece from the chessboard
     *
     * @param position where to remove the piece at
     */
    public void removePiece(ChessPosition position) {
        if (position.getRow() <= 8 && position.getRow() >= 1 && position.getColumn() <= 8 && position.getColumn() >= 1) {
            squares[position.getRow() - 1][position.getColumn() - 1] = null;
        } else {
            throw new IllegalArgumentException("Given position is out of bounds.");
        }
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (position.getRow() <= 8 && position.getRow() >= 1 && position.getColumn() <= 8 && position.getColumn() >= 1) {
            return squares[position.getRow() - 1][position.getColumn() - 1];
        } else {
            return null;
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.squares = new ChessPiece[8][8];
        ChessPiece.PieceType[] pieceOrder = {
            ChessPiece.PieceType.ROOK,
            ChessPiece.PieceType.KNIGHT,
            ChessPiece.PieceType.BISHOP,
            ChessPiece.PieceType.QUEEN,
            ChessPiece.PieceType.KING,
            ChessPiece.PieceType.BISHOP,
            ChessPiece.PieceType.KNIGHT,
            ChessPiece.PieceType.ROOK,
        };

        for (int i = 0; i < 8; i++) {
            squares[0][i] = new ChessPiece(ChessGame.TeamColor.WHITE, pieceOrder[i]);
            squares[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            squares[2][i] = null;
            squares[3][i] = null;
            squares[4][i] = null;
            squares[5][i] = null;
            squares[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            squares[7][i] = new ChessPiece(ChessGame.TeamColor.BLACK, pieceOrder[i]);
        }
    }

    /**
     * Creates a copy of the current board state
     *
     * @return the 2D array copy of the existing board state when called
     */
    public ChessPiece[][] getBoardCopy() {
        return Arrays.stream(squares).map(ChessPiece[]::clone).toArray(ChessPiece[][]::new);
    }

    public Collection<ChessPosition> getTeamPieces(ChessGame.TeamColor teamColor) {
        Collection<ChessPosition> teamPieces = new HashSet<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                var pos = new ChessPosition(i+1, j+1);
                if (getPiece(pos) == null) {
                    continue;
                }
                if (getPiece(pos).getTeamColor() == teamColor) {
                    teamPieces.add(pos);
                }
            }
        }

        return teamPieces;
    }

    public ChessPosition getKingLocation(ChessGame.TeamColor teamColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                var pos = new ChessPosition(i+1, j+1);
                if (getPiece(pos) != null && getPiece(pos).getPieceType() == ChessPiece.PieceType.KING && getPiece(pos).getTeamColor() == teamColor) {
                    return pos;
                }
            }
        }

        return null;
    }
}
