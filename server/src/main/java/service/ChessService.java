package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import models.*;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.ListGamesResponse;
import server.ResponseException;

import javax.xml.crypto.Data;

public class ChessService {

    private final DataAccess dataAccess;

    public ChessService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void clear() throws ResponseException {
        try {
            dataAccess.clear();
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

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

    public AuthData loginUser(UserData userData) throws ResponseException {
        //return the username and authToken
        try {
            if (!dataAccess.userExists(userData.username())) {
                throw new ResponseException(401, "Error: unauthorized ");
            }
            var user = dataAccess.getUser(userData);
            if (!user.password().equals(userData.password())) {
                throw new ResponseException(401, "Error: unauthorized ");
            }
            else {
                return dataAccess.createAuth(userData.username());
            }
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

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

    public ListGamesResponse listGames(String authToken) throws ResponseException {
        try {
            if (!dataAccess.getAuth(authToken)) {
                throw new ResponseException(401, "Error: unauthorized ");
            }
            var gamesCollection = dataAccess.listGames();
            return new ListGamesResponse(gamesCollection);
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

    public CreateGameResponse createGame(String authToken, GameData game) throws ResponseException {
        try {
            if (game.gameName() == null) {
                throw new ResponseException(400, "Error: bad request ");
            }
            else if (!dataAccess.getAuth(authToken)) {
                throw new ResponseException(401, "Error: unauthorized ");
            }
            else {
                var newGame = dataAccess.createGame(game);
                return new CreateGameResponse(newGame.gameID());
            }
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

    public void joinGame(String authToken, JoinGameRequest joinGameRequest) throws ResponseException {
        try {
            if (!dataAccess.getAuth(authToken)) {
                throw new ResponseException(401, "Error: unauthorized ");
            }
            if (dataAccess.getGame(joinGameRequest.gameID()) == null) {
                throw new ResponseException(400, "Error: bad request ");
            }
            if ((joinGameRequest.playerColor() == null)) {
                return;
            }
            var playerColor = joinGameRequest.playerColor();
            if (dataAccess.getGame(joinGameRequest.gameID()).blackUsername() == null && playerColor.equals("BLACK")) {
                dataAccess.joinGame(authToken, joinGameRequest.gameID(), joinGameRequest.playerColor());
            } else if (dataAccess.getGame(joinGameRequest.gameID()).whiteUsername() == null && playerColor.equals("WHITE")) {
                dataAccess.joinGame(authToken, joinGameRequest.gameID(), joinGameRequest.playerColor());
            } else {
                throw new ResponseException(403, "Error: already taken ");
            }
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }
}
