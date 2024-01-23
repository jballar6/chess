package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new HashSet<>();

        addKnightMoves(moves, board, position);

        return moves;
    }

    private void addKnightMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ChessPiece myPiece = board.getPiece(position);
        ChessPosition endPosition;

        //up-left
        if (row + 2 < 9 && col - 1 > 0) {
            endPosition = new ChessPosition(row + 2, col - 1);

            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }


        //up-right
        if (row + 2 < 9 && col + 1 < 9) {
            endPosition = new ChessPosition(row + 2, col + 1);

            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //left-up
        if (row + 1 < 9 && col - 2 > 0) {
            endPosition = new ChessPosition(row + 1, col - 2);

            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //left-down
        if (row - 1 > 0 && col - 2 > 0) {
            endPosition = new ChessPosition(row - 1, col - 2);

            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //right-up
        if (row + 1 < 9 && col + 2 < 9) {
            endPosition = new ChessPosition(row + 1, col + 2);

            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //right-down
        if (row - 1 > 0 && col + 2 < 9) {
            endPosition = new ChessPosition(row - 1, col + 2);

            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //down-left
        if (row - 2 > 0 && col - 1 > 0) {
            endPosition = new ChessPosition(row - 2, col - 1);

            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //down-right
        if (row - 2 > 0 && col + 1 < 9) {
            endPosition = new ChessPosition(row - 2, col + 1);

            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }
    }
}
