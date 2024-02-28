package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

public interface DataAccess {
    void clear();

    void registerUser(UserData user) throws DataAccessException;

    boolean userExists(String user) throws DataAccessException;

    UserData getUser(UserData user) throws DataAccessException;

    AuthData createAuth(String username) throws DataAccessException;

    boolean getAuth(String username) throws DataAccessException;

    void deleteAuth(String username) throws DataAccessException;

    Collection<GameData> listGames(AuthData auth) throws DataAccessException;

    void createGame(GameData game) throws DataAccessException;

    void updateGame(GameData game, AuthData auth) throws DataAccessException;

    void joinGame(GameData game, AuthData auth) throws DataAccessException;
}
