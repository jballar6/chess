package dataAccess;

import models.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<String, AuthData> authTokens = new HashMap<>();
    final private HashMap<Integer, GameData> games = new HashMap<>();
    final private HashMap<String, UserData> users = new HashMap<>();

    private Integer nextId = 0;

    public Integer authSize() {
        return authTokens.size();
    }

    public Integer gamesSize() {
        return games.size();
    }

    public Integer usersSize() {
        return users.size();
    }

    @Override
    public void clear() {
        authTokens.clear();
        games.clear();
        users.clear();
    }

    public String generateAuth() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void registerUser(UserData user) {
        users.put(user.username(), user);
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public UserData getUser(UserData user) {
        return users.get(user.username());
    }

    @Override
    public AuthData createAuth(String username) {
        var auth = new AuthData(generateAuth(), username);

        authTokens.put(auth.authToken(), auth);

        return auth;
    }

    @Override
    public boolean getAuth(String authToken) {
        return authTokens.containsKey(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        authTokens.remove(authToken);
    }

    @Override
    public boolean getAuthFromUser(String username) throws DataAccessException {
        throw new DataAccessException("Incorrect data needed, use authToken");
    }

    @Override
    public void deleteAuthFromUser(String username) throws DataAccessException {
        throw new DataAccessException("Incorrect data needed, use authToken");
    }

    @Override
    public Collection<GameData> listGames() {
        return games.values();
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public GameData createGame(GameData game) {
        nextId++;
        game = game.setGameID(nextId);
        games.put(nextId, game);
        return game;
    }

    @Override
    public void joinGame(String authToken, int gameID, String playerColor) {
        var game = games.get(gameID);
        var user = authTokens.get(authToken);
        if (Objects.equals(playerColor, "BLACK")) {
            games.put(gameID, game.setBlackUsername(user.username()));
        } else {
            games.put(gameID, game.setWhiteUsername(user.username()));
        }
    }
}
