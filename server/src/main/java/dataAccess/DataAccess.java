package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

public interface DataAccess {
    void clear();

    void registerUser(String username, String password, String email) throws DataAccessException;

    void getUser(UserData user) throws DataAccessException;

    void loginUser(UserData user) throws DataAccessException;

    void logoutUser(UserData user, AuthData auth) throws DataAccessException;

    Collection<GameData> listGames(AuthData auth) throws DataAccessException;

    void createGame(AuthData auth) throws DataAccessException;

    void updateGame(GameData game, AuthData auth) throws DataAccessException;

    void joinGame(GameData game, AuthData auth) throws DataAccessException;

    void createAuth() throws DataAccessException;

    void getAuth(AuthData auth) throws DataAccessException;

    void deleteAuth(AuthData auth) throws DataAccessException;
}
