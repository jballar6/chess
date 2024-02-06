package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;
    private final List<ChessBoard> boardHistory = new ArrayList<>();

    public ChessGame() {
        this.board = new ChessBoard();
        this.teamTurn = TeamColor.WHITE;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamTurn=" + teamTurn +
                ", board=" + board +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);

        return piece.pieceMoves(board, startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (move.getEndPosition().getRow() > 8 || move.getEndPosition().getRow() < 1 || move.getEndPosition().getColumn() > 8 || move.getEndPosition().getColumn() < 1) {
            throw new InvalidMoveException("Invalid move. Try again.");
        }
        if (move.getStartPosition().getRow() > 8 || move.getStartPosition().getRow() < 1 || move.getStartPosition().getColumn() > 8 || move.getStartPosition().getColumn() < 1) {
            throw new InvalidMoveException("Invalid move. Try again.");
        }

        ChessPiece movingPiece = board.getPiece(move.getStartPosition());
        TeamColor teamColor = movingPiece.getTeamColor();
        Collection<ChessMove> movingPieceValidMoves = validMoves(move.getStartPosition());

        if (movingPieceValidMoves.contains(move)) {
            boardHistory.add(new ChessBoard(board.getBoardCopy()));

            board.removePiece(move.getEndPosition());

            if (movingPiece.getPieceType() == ChessPiece.PieceType.PAWN && move.getPromotionPiece() != null) {
                board.addPiece(move.getEndPosition(), new ChessPiece(teamColor, move.getPromotionPiece()));
            } else {
                board.addPiece(move.getEndPosition(), movingPiece);
            }

            board.removePiece(move.getStartPosition());

            if (isInCheck(teamTurn)) {
                setBoard(boardHistory.getLast());
                throw new InvalidMoveException("King was in check!");
            }

            if (teamTurn == TeamColor.BLACK) {
                setTeamTurn(TeamColor.WHITE);
            } else {
                setTeamTurn(TeamColor.BLACK);
            }
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingLocation = board.getKingLocation(teamColor);
        if (kingLocation == null) {
            return false;
        }

        TeamColor opponentTeam;
        if (teamColor == TeamColor.BLACK) {
            opponentTeam = TeamColor.WHITE;
        } else {
            opponentTeam = TeamColor.BLACK;
        }

        Collection<ChessPosition> opponentTeamPieces = board.getTeamPieces(opponentTeam);

        for (ChessPosition pos : opponentTeamPieces) {
            Collection<ChessMove> opponentPieceMoves = board.getPiece(pos).pieceMoves(board, pos);

            for (ChessMove move : opponentPieceMoves) {
                ChessPosition endPos = move.getEndPosition();
                if (endPos.getRow() == kingLocation.getRow() && endPos.getColumn() == kingLocation.getColumn()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPosition kingLocation = board.getKingLocation(teamColor);
        if (kingLocation == null) {
            return false;
        }

        Collection<ChessMove> kingMoves = board.getPiece(kingLocation).pieceMoves(board, kingLocation);
        if (kingMoves.isEmpty()) {
            return false;
        }

        for (ChessMove kingMove : kingMoves) {
            try {
                setTeamTurn(teamColor);
                makeMove(kingMove);
                setBoard(boardHistory.getLast());
                return false;
            } catch (InvalidMoveException ignored) {
            }
        }

        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
