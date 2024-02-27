package dataAccess;

import model.*;

import java.util.Collection;
import java.util.HashMap;

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

    @Override
    public void registerUser(UserData user) throws DataAccessException {
        users.put(user.username(), user);
    }

    @Override
    public boolean getUser(String username) throws DataAccessException {
        return users.containsKey(username);
    }

    @Override
    public AuthData createAuth(String username) {
        var auth = new AuthData("test", username);

        authTokens.put(username, auth);

        return auth;
    }

    @Override
    public boolean getAuth(String username) throws DataAccessException {
        return authTokens.containsKey(username);
    }

    @Override
    public void deleteAuth(String username) throws DataAccessException {
        authTokens.remove(username);
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
