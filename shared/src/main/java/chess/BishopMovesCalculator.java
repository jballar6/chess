package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMovesCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new HashSet<>();

        //Helper function
        addDiagonalMoves(moves, board, position);

        return moves;
    }

    private void addDiagonalMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {
        //upper right check
        ChessPosition upperRight = new ChessPosition(position.getRow() + 1, position.getColumn() + 1);
        addInDiagonalDirection(moves, board, position, upperRight);

        //upper left check
        ChessPosition upperLeft = new ChessPosition(position.getRow() + 1, position.getColumn() - 1);
        addInDiagonalDirection(moves, board, position, upperLeft);

        //lower left check
        ChessPosition lowerLeft = new ChessPosition(position.getRow() - 1, position.getColumn() - 1);
        addInDiagonalDirection(moves, board, position, lowerLeft);

        //lower right check
        ChessPosition lowerRight = new ChessPosition(position.getRow() - 1, position.getColumn() + 1);
        addInDiagonalDirection(moves, board, position, lowerRight);
    }

    private void addInDiagonalDirection(Collection<ChessMove> moves, ChessBoard board, ChessPosition startPosition, ChessPosition endPosition) {
        while (endPosition.isValidPosition()) {
            moves.add(new ChessMove(startPosition, endPosition, null));
        }
    }
}