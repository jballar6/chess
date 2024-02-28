package dataAccess;

import models.AuthData;
import models.GameData;
import models.UserData;

import java.util.Collection;

public interface DataAccess {
    void clear();

    void registerUser(UserData user) throws DataAccessException;

    boolean userExists(String user) throws DataAccessException;

    UserData getUser(UserData user) throws DataAccessException;

    AuthData createAuth(String username) throws DataAccessException;

    boolean getAuth(String username) throws DataAccessException;

    void deleteAuth(String username) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;

    GameData createGame(GameData game) throws DataAccessException;

    void joinGame(String authToken, int gameID, String playerColor) throws DataAccessException;
}
