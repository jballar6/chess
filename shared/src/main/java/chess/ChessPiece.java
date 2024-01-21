package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;
    private final PieceMovesCalculator movesCalculator;

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                ", movesCalculator=" + movesCalculator +
                '}';
    }

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        this.movesCalculator = createMovesCalculator(type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        var piece = board.getPiece(myPosition);

        if (piece != null) {
            return movesCalculator.pieceMoves(board, myPosition);
        }

        return new HashSet<>();
    }

    private PieceMovesCalculator createMovesCalculator(PieceType type) {
        switch (type) {
            case KING:
                return new KingMovesCalculator();
            case QUEEN:
                return new QueenMovesCalculator();
            case BISHOP:
                return new BishopMovesCalculator();
            case KNIGHT:
                return new KnightMovesCalculator();
            case ROOK:
                return new RookMovesCalculator();
            case PAWN:
                return new PawnMovesCalculator();
            default:
                throw new IllegalArgumentException("Unrecognized piece type: " + type);
        }
    }
}