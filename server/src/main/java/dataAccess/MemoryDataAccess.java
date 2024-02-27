package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

public class MemoryDataAccess implements DataAccess {
    @Override
    public void clear() {

    }

    @Override
    public void registerUser(UserData user) throws DataAccessException {

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
    public void listGames(AuthData auth) throws DataAccessException {

    }

    @Override
    public void createGame(AuthData auth) throws DataAccessException {

    }

    @Override
    public void updateGame(GameData game, AuthData auth) throws DataAccessException {

    }

    @Override
    public void joinGame(AuthData auth) throws DataAccessException {

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
