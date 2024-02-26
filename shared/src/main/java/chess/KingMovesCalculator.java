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

    private void getCastleMove(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        if ((position.getRow() == 8 || position.getRow() == 1) && position.getColumn() == 5) {
            int c = col;
            while (c + 1 < 9 && board.getPiece(new ChessPosition(row, c + 1)) == null) {
                c++;
            }

            ChessPosition endPosition = new ChessPosition(row, c);
            if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getPieceType() == ChessPiece.PieceType.ROOK && c == 8) {
                moves.add(new ChessMove(position, new ChessPosition(row, col + 2), null));
            }

            c = col;
            while (c - 1 > 0 && board.getPiece(new ChessPosition(row, c - 1)) == null) {
                c--;
            }

            endPosition = new ChessPosition(row, c);
            if (board.getPiece(endPosition) != null && board.getPiece(endPosition).getPieceType() == ChessPiece.PieceType.ROOK && c == 1) {
                moves.add(new ChessMove(position, new ChessPosition(row, col - 2), null));
            }
        }
    }

    private void addDiagonalMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ChessPiece myPiece = board.getPiece(position);

        //upper right
        if (row+1 < 9 && col+1 < 9) {
            ChessPosition endPosition = new ChessPosition(row + 1, col + 1);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //upper left
        if (row+1 < 9 && col-1 > 0) {
            ChessPosition endPosition = new ChessPosition(row + 1, col - 1);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //lower left
        if (row-1 > 0 && col-1 > 0) {
            ChessPosition endPosition = new ChessPosition(row - 1, col - 1);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //lower right
        if (row-1 > 0 && col+1 < 9) {
            ChessPosition endPosition = new ChessPosition(row - 1, col + 1);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }
    }

    private void addCardinalMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ChessPiece myPiece = board.getPiece(position);

        //up
        if (row+1 < 9) {
            ChessPosition endPosition = new ChessPosition(row + 1, col);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //down
        if (row-1 > 0) {
            ChessPosition endPosition = new ChessPosition(row - 1, col);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //left
        if (col-1 > 0) {
            ChessPosition endPosition = new ChessPosition(row, col - 1);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }

        //right
        if (col+1 < 9) {
            ChessPosition endPosition = new ChessPosition(row, col + 1);

            if (board.getPiece(endPosition) == null) {
                moves.add(new ChessMove(position, endPosition, null));
            } else if (board.getPiece(endPosition).getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(position, endPosition, null));
            }
        }
    }
}
