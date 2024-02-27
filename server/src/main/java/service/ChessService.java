package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.*;

public class ChessService {

    private final DataAccess dataAccess;

    public ChessService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    //endpoints:
    // clear application
    public void clear() {
        dataAccess.clear();
    }
    // register
    public AuthData registerUser(UserData userData) throws DataAccessException {
        if (!dataAccess.getUser(userData.username())) {
            dataAccess.registerUser(userData);

            return dataAccess.createAuth(userData.username());
        }

        //return a success message?
        return new AuthData("test", "test");
    }

    // login
    public void loginUser(UserData userData) throws DataAccessException {
        //return the username and authToken
    }
    // logout
    public void logoutUser(UserData userData, AuthData authData) throws DataAccessException {
    }
    // list games
    public void listGames() throws DataAccessException {
    }
    // create game
    public void createGame() throws DataAccessException {

    }
    // join game
    public void joinGame() throws DataAccessException {

    }
}
