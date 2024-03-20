package ui;

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

//    public String eval(String input) {
//        try {
//            var tokens = input.toLowerCase().split(" ");
//            var cmd = (tokens.length > 0) ? tokens[0] : "help";
//            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//            return switch (cmd) {
//                case "register" -> register(params);
//                case "login" -> login(params);
//                case "logout" -> logout(params);
//                case "list" -> listGames(params);
//                case "create" -> creatGame(params);
//                case "join" -> joinGame(params);
//                case "observe" -> observeGame(params);
//                case "quit" -> "quit";
//                case default -> help();
//            };
//        } catch (ResponseException e) {
//            return e.getMessage();
//        }
//    }
}
