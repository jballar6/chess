package chess;

import chess.ChessGame.TeamColor;
import java.util.Collection;

public interface ChessRuleBook {
    Collection<ChessMove> validMoves(ChessBoard board, ChessPosition startPosition);

    Boolean isInCheck(ChessBoard board, TeamColor team);

    Boolean isInCheckmate(ChessBoard board, TeamColor team);

    Boolean isInStalemate(ChessBoard board, TeamColor team);
}