package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new HashSet<>();

        addCardinalMoves(moves, board, position);

        addDiagonalMoves(moves, board, position);

        return moves;
    }

    private void addDiagonalMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ChessPiece myPiece = board.getPiece(position);

        //upper right
        int r = row;
        int c = col;
        while (r + 1 < 9 && c + 1 < 9) {
            r++;
            c++;
            ChessPosition endPosition = new ChessPosition(r, c);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //upper left
        r = row;
        c = col;
        while (r + 1 < 9 && c - 1 > 0) {
            r++;
            c--;
            ChessPosition endPosition = new ChessPosition(r, c);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //lower left
        r = row;
        c = col;
        while (r - 1 > 0 && c - 1 > 0) {
            r--;
            c--;
            ChessPosition endPosition = new ChessPosition(r, c);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //lower right
        r = row;
        c = col;
        while (r - 1 > 0 && c + 1 < 9) {
            r--;
            c++;
            ChessPosition endPosition = new ChessPosition(r, c);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }
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
                break;
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
                break;
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
                break;
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
                break;
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
                break;
            } else {
                break;
            }
        }
    }
}
