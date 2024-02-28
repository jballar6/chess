package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.*;
import server.ResponseException;

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
    public AuthData registerUser(UserData userData) throws ResponseException {
        try {
            if (userData.username() == null || userData.password() == null || userData.email() == null) {
                throw new ResponseException(400, "Error: bad request ");
            }
            else if (!dataAccess.userExists(userData.username())) {
                dataAccess.registerUser(userData);

                return dataAccess.createAuth(userData.username());
            }
            else {
                throw new ResponseException(403, "Error: already taken ");
            }
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

    // login
    public AuthData loginUser(UserData userData) throws ResponseException {
        //return the username and authToken
        try {
            if (!dataAccess.userExists(userData.username())) {
                throw new ResponseException(401, "Error: unauthorized ");
            }
            else {
                return dataAccess.createAuth(userData.username());
            }
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }
    // logout
    public void logoutUser(String authToken) throws ResponseException {
        try {
            if (!dataAccess.getAuth(authToken)) {
                throw new ResponseException(401, "Error: unauthorized ");
            } else {
                dataAccess.deleteAuth(authToken);
            }
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }
    // list games
    public void listGames() {
    }
    // create game
    public void createGame() {

    }
    // join game
    public void joinGame() {

    }
}
