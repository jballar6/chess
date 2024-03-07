package dataAccess;

import models.*;

import java.util.Collection;

public class MySQLDataAccess implements DataAccess {
    @Override
    public void clear() {

    }

    @Override
    public void registerUser(UserData user) throws DataAccessException {

    }

    @Override
    public boolean userExists(String user) throws DataAccessException {
        return false;
    }

    @Override
    public UserData getUser(UserData user) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public boolean getAuth(String username) throws DataAccessException {
        return false;
    }

    @Override
    public void deleteAuth(String username) throws DataAccessException {

    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        return null;
    }

    @Override
    public void joinGame(String authToken, int gameID, String playerColor) throws DataAccessException {

    }
}
