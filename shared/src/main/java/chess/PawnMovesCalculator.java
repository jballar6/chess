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
        int row = position.getRow();
        int col = position.getColumn();
        ChessPiece myPiece = board.getPiece(position);
        ChessGame.TeamColor pieceTeam = myPiece.getTeamColor();

        if (pieceTeam == ChessGame.TeamColor.WHITE) {
            //checking for opponent pieces
            ChessPosition upLeft = new ChessPosition(row + 1, col - 1);
            ChessPiece upLeftPiece = board.getPiece(upLeft);
            ChessPosition upRight = new ChessPosition(row + 1, col + 1);
            ChessPiece upRightPiece = board.getPiece(upRight);

            //adding standard move
            ChessPosition standardEndPosition = new ChessPosition(row + 1, col);

            if (standardEndPosition.getRow() == 8) {
                pawnPromotionMove(moves, board, position, pieceTeam, upLeft, upLeftPiece, upRight, upRightPiece, standardEndPosition);
            } else {
                if (board.getPiece(standardEndPosition) == null) {
                    moves.add(new ChessMove(position, standardEndPosition, null));

                    if (position.getRow() == 2) {
                        ChessPosition firstMoveEndPosition = new ChessPosition(row + 2, col);

                        if (board.getPiece(firstMoveEndPosition) == null) {
                            moves.add(new ChessMove(position, firstMoveEndPosition, null));
                        }
                    }
                }
                if (upRightPiece != null && upRightPiece.getTeamColor() != pieceTeam) {
                    moves.add(new ChessMove(position, upRight, null));
                }
                if (upLeftPiece != null && upLeftPiece.getTeamColor() != pieceTeam) {
                    moves.add(new ChessMove(position, upLeft, null));
                }
            }
        }
        else if (pieceTeam == ChessGame.TeamColor.BLACK) {
            //checking for opponent pieces
            ChessPosition downLeft = new ChessPosition(row - 1, col - 1);
            ChessPiece downLeftPiece = board.getPiece(downLeft);
            ChessPosition downRight = new ChessPosition(row - 1, col + 1);
            ChessPiece downRightPiece = board.getPiece(downRight);

            //adding standard move
            ChessPosition standardEndPosition = new ChessPosition(row - 1, col);

            if (standardEndPosition.getRow() == 1) {
                pawnPromotionMove(moves, board, position, pieceTeam, downLeft, downLeftPiece, downRight, downRightPiece, standardEndPosition);
            } else {
                if (board.getPiece(standardEndPosition) == null) {
                    moves.add(new ChessMove(position, standardEndPosition, null));

                    if (position.getRow() == 7) {
                        ChessPosition firstMoveEndPosition = new ChessPosition(row - 2, col);

                        if (board.getPiece(firstMoveEndPosition) == null) {
                            moves.add(new ChessMove(position, firstMoveEndPosition, null));
                        }
                    }
                }
                if (downRightPiece != null && downRightPiece.getTeamColor() != pieceTeam) {
                    moves.add(new ChessMove(position, downRight, null));
                }
                if (downLeftPiece != null && downLeftPiece.getTeamColor() != pieceTeam) {
                    moves.add(new ChessMove(position, downLeft, null));
                }
            }
        }
    }

    private void pawnPromotionMove(Collection<ChessMove> moves, ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceTeam, ChessPosition downLeft, ChessPiece downLeftPiece, ChessPosition downRight, ChessPiece downRightPiece, ChessPosition standardEndPosition) {
        if (board.getPiece(standardEndPosition) == null) {
            moves.add(new ChessMove(position, standardEndPosition, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(position, standardEndPosition, ChessPiece.PieceType.KNIGHT));
            moves.add(new ChessMove(position, standardEndPosition, ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(position, standardEndPosition, ChessPiece.PieceType.BISHOP));
        }
        if (downRightPiece != null && downRightPiece.getTeamColor() != pieceTeam) {
            moves.add(new ChessMove(position, downRight, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(position, downRight, ChessPiece.PieceType.KNIGHT));
            moves.add(new ChessMove(position, downRight, ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(position, downRight, ChessPiece.PieceType.BISHOP));
        }
        if (downLeftPiece != null && downLeftPiece.getTeamColor() != pieceTeam) {
            moves.add(new ChessMove(position, downLeft, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(position, downLeft, ChessPiece.PieceType.KNIGHT));
            moves.add(new ChessMove(position, downLeft, ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(position, downLeft, ChessPiece.PieceType.BISHOP));
        }
    }
}
