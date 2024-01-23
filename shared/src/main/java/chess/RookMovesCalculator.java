package chess;

import java.util.Collection;
import java.util.HashSet;

public class RookMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new HashSet<>();

        addCardinalMoves(moves, board, position);

        return moves;
    }

    private void addCardinalMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ChessPiece myPiece = board.getPiece(position);

        //up
        int r = row;
        while (r + 1 < 9) {
            r++;
            ChessPosition endPosition = new ChessPosition(r, col);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //down
        r = row;
        while (r - 1 > 0) {
            r--;
            ChessPosition endPosition = new ChessPosition(r, col);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //left
        int c = col;
        while (c - 1 > 0) {
            c--;
            ChessPosition endPosition = new ChessPosition(row, c);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //right
        c = col;
        while (c + 1 < 9) {
            c++;
            ChessPosition endPosition = new ChessPosition(row, c);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }
    }
}
