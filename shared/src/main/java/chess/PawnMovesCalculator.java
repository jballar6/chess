package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new HashSet<>();

        addPawnMoves(moves, board, position);

        return moves;
    }

    private void addPawnMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {
    }
}
