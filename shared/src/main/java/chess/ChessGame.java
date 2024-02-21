package chess;

import java.util.*;

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
    private boolean blackCastled = false;
    private boolean whiteCastled = false;

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
     * @return Which team is the opponent of passed in team
     */
    public TeamColor getOpponentTeam(TeamColor teamColor) {
        if (teamColor == TeamColor.BLACK) {
            return TeamColor.WHITE;
        } else {
            return TeamColor.BLACK;
        }
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
     * Can be used to find if any move causes check
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> pieceMoves = piece.pieceMoves(board, startPosition);

        Iterator<ChessMove> itr = pieceMoves.iterator();
        while (itr.hasNext()) {
            ChessMove move = itr.next();
            try {
                simpleTestMove(move);
                setBoard(boardHistory.getLast());
            } catch (InvalidMoveException e) {
                itr.remove();
            }
        }

        return pieceMoves;
    }

    /**
     * Makes a simple test move that is expected to be reverted
     * to confirm if a potential board state is valid
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    private void simpleTestMove(ChessMove move) throws InvalidMoveException {
        ChessPiece movingPiece = board.getPiece(move.getStartPosition());
        TeamColor teamColor = movingPiece.getTeamColor();

        boardHistory.add(new ChessBoard(board.getBoardCopy()));

        board.removePiece(move.getEndPosition());
        board.addPiece(move.getEndPosition(), movingPiece);
        board.removePiece(move.getStartPosition());

        if (isInCheck(teamColor)) {
            setBoard(boardHistory.getLast());
            throw new InvalidMoveException("Move resulted in King being in check!");
        }
        if (movingPiece.getPieceType() == ChessPiece.PieceType.KING && teamColor == TeamColor.WHITE) {
            if (move.getEndPosition().getColumn() == move.getStartPosition().getColumn()+2 || move.getEndPosition().getColumn() == move.getStartPosition().getColumn()-2) {

            }
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece movingPiece = board.getPiece(move.getStartPosition());

        TeamColor teamColor = movingPiece.getTeamColor();
        if (movingPiece.getTeamColor() != getTeamTurn()) {
            throw new InvalidMoveException("Invalid move, moving out of turn.");
        }

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

            setTeamTurn(getOpponentTeam(getTeamTurn()));
        } else {
            throw new InvalidMoveException("Invalid move. Try again.");
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

        TeamColor opponentTeam = getOpponentTeam(teamColor);

        Collection<ChessPosition> opponentTeamPieces = board.getTeamPieces(opponentTeam);

        for (ChessPosition pos : opponentTeamPieces) {
            Collection<ChessMove> opponentPieceMoves = board.getPiece(pos).pieceMoves(board, pos);

            for (ChessMove move : opponentPieceMoves) {
                ChessPosition endPos = move.getEndPosition();
                if (endPos.equals(kingLocation)) {
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

        //Test if King is surrounded by teammates
        Collection<ChessMove> kingMoves = board.getPiece(kingLocation).pieceMoves(board, kingLocation);
        if (kingMoves.isEmpty()) {
            return false;
        }

        //Test if King can make a move out of check
        kingMoves = validMoves(kingLocation);
        return kingMoves.isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessPosition> teamPieces = board.getTeamPieces(teamColor);

        for (ChessPosition pos : teamPieces) {
            Collection<ChessMove> pieceMoves = validMoves(pos);
            if (!pieceMoves.isEmpty()) {
                return false;
            }
        }

        return true;
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
