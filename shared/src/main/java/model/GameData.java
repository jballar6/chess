package model;
import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, String game) {
    public GameData setGameID(int newGameID) {
        return new GameData(newGameID, whiteUsername, blackUsername, gameName, game);
    }

    public GameData setWhiteUsername(String newWhiteUsername) {
        return new GameData(gameID, newWhiteUsername, blackUsername, gameName, game);
    }

    public GameData setBlackUsername(String newBlackUsername) {
        return new GameData(gameID, whiteUsername, newBlackUsername, gameName, game);
    }
}
