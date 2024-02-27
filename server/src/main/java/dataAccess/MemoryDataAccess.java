package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<Integer, AuthData> authTokens = new HashMap<>();
    final private HashMap<Integer, GameData> games = new HashMap<>();
    final private HashMap<Integer, UserData> users = new HashMap<>();

    private int nextId = 0;

    @Override
    public void clear() {
        authTokens.clear();
        games.clear();
        users.clear();
    }

    @Override
    public void registerUser(String username, String password, String email) throws DataAccessException {
        var user = new UserData(username, password, email);

        users.put(nextId++, user);
    }

    @Override
    public void getUser(UserData user) throws DataAccessException {

    }

    @Override
    public void loginUser(UserData user) throws DataAccessException {

    }

    @Override
    public void logoutUser(UserData user, AuthData auth) throws DataAccessException {

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

    @Override
    public void createAuth() throws DataAccessException {

    }

    @Override
    public void getAuth(AuthData auth) throws DataAccessException {

    }

    @Override
    public void deleteAuth(AuthData auth) throws DataAccessException {

    }
}
