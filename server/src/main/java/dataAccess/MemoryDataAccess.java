package dataAccess;

import chess.ChessGame;
import java.util.HashMap;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<Integer, ChessGame> games = new HashMap<Integer, ChessGame>();

    @Override
    public void clearDatabase() {
        games.clear();
    }

    @Override
    public void registerUser() {
    }

    @Override
    public void loginUser() {
    }

    @Override
    public void logoutUser() {
    }

    @Override
    public void listGames() {
    }

    @Override
    public void createGame() {
    }

    @Override
    public void joinGame() {
    }
}
