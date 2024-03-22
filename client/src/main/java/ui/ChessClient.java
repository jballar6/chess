package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import models.AuthData;

import java.util.Arrays;

public class ChessClient {
    private AuthData visitorData = null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "logout" -> logout();
                case "list" -> listGames(params);
                case "create" -> createGame(params);
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException e) {
            return e.getMessage();
        }
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    login <USERNAME> <PASSWORD> - to play chess
                    quit - playing chess
                    help - with possible commands
                    """;
        } else {
            return """
                    create <NAME> - a game
                    list - games
                    join <ID> [WHITE|BLACK|<empty>] - a game
                    observe <ID> - a game
                    logout - when you are done
                    quit - playing chess
                    help - with possible commands
                    """;
        }
    }

    private String observeGame(String[] params) {
        return null;
    }

    private String joinGame(String[] params) {
        return null;
    }

    private String createGame(String[] params) throws ResponseException {
        assertSignedIn();
        if (params.length == 1) {
            var gameName = params[0];
            var result = server.createGame(gameName, visitorData.authToken());
            return String.format("Game #%d created: %s", result.gameID(), gameName);
        }
        throw new ResponseException(400, "Expected: <GAMENAME>");
    }

    private String listGames(String[] params) throws ResponseException {
        assertSignedIn();
        var listGamesResponse = server.listGames(visitorData.authToken());
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game : listGamesResponse.games()) {
            result.append(gson.toJson(game)).append('\n');
        }
        return result.toString();
    }

    private String logout() throws ResponseException {
        assertSignedIn();
        server.logoutUser(visitorData.authToken());
        state = State.SIGNEDOUT;
        return String.format("%s logged out", visitorData.username());
    }

    private String login(String[] params) throws ResponseException {
        if (state == State.SIGNEDIN) {
            return "You are already logged in on this device!";
        }
        if (params.length == 2) {
            var username = params[0];
            var password = params[1];
            var result = server.loginUser(username, password);
            state = State.SIGNEDIN;
            visitorData = result;
            return String.format("Logged in as %s", result.username());
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    private String register(String[] params) throws ResponseException {
        if (params.length == 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            var result = server.registerUser(username, password, email);
            state = State.SIGNEDIN;
            visitorData = result;
            return String.format("User registered: %s. Welcome to Chess!", result.username());
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>");
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in to perform that action.");
        }
    }
}
