package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

public interface DataAccess {
    void clear();

    void registerUser(UserData user) throws DataAccessException;

    void getUser(UserData user) throws DataAccessException;

    void loginUser(UserData user) throws DataAccessException;

    void logoutUser(UserData user, AuthData auth) throws DataAccessException;

    void listGames(AuthData auth) throws DataAccessException;

    void createGame(AuthData auth) throws DataAccessException;

    void updateGame(GameData game, AuthData auth) throws DataAccessException;

    void joinGame(AuthData auth) throws DataAccessException;

    void createAuth() throws DataAccessException;

    void getAuth(AuthData auth) throws DataAccessException;

    void deleteAuth(AuthData auth) throws DataAccessException;
}
