package ui;

import exception.ResponseException;
import java.util.Arrays;
import static ui.EscapeSequences.*;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl, Repl repl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "logout" -> logout(params);
                case "list" -> listGames(params);
                case "create" -> createGame(params);
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "quit" -> "quit";
                default -> help();
            };
//        } catch (ResponseException e) {
//            return e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    private String createGame(String[] params) {
        return null;
    }

    private String listGames(String[] params) {
        return null;
    }

    private String logout(String[] params) {
        return null;
    }

    private String login(String[] params) {
        return null;
    }

    private String register(String[] params) {
        return null;
    }
}
