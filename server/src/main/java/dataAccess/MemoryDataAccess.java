package dataAccess;

import model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<String, AuthData> authTokens = new HashMap<>();
    final private HashMap<String, GameData> games = new HashMap<>();
    final private HashMap<String, UserData> users = new HashMap<>();

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
    public void registerUser(UserData user) throws DataAccessException {
        users.put(user.username(), user);
    }

    @Override
    public boolean userExists(String username) throws DataAccessException {
        return users.containsKey(username);
    }

    @Override
    public UserData getUser(UserData user) throws DataAccessException {
        return users.get(user);
    }

    @Override
    public AuthData createAuth(String username) {
        var auth = new AuthData(generateAuth(), username);

        authTokens.put(auth.authToken(), auth);

        return auth;
    }

    @Override
    public boolean getAuth(String authToken) throws DataAccessException {
        return authTokens.containsKey(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        authTokens.remove(authToken);
    }

    @Override
    public Collection<GameData> listGames(AuthData auth) throws DataAccessException {
        return games.values();
    }

    @Override
    public void createGame(AuthData auth) throws DataAccessException {

    }

    @Override
    public void updateGame(GameData game, AuthData auth) throws DataAccessException {

    }

    @Override
    public void joinGame(GameData game, AuthData auth) throws DataAccessException {

    }
}
