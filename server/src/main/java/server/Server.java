package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.*;
import service.ChessService;
import spark.*;

import java.util.Map;

public class Server {
    private final ChessService service;

    public Server() {
        this.service = new ChessService(new MemoryDataAccess());
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.delete("/db", this::clear);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::loginUser);
        Spark.delete("/session", this::logoutUser);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        Spark.exception(ResponseException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private void exceptionHandler(ResponseException e, Request request, Response response) {
        response.body(new Gson().toJson(Map.of("message", e.getMessage())));
        response.status(e.StatusCode());
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object joinGame(Request request, Response response) {
        return null;
    }

    private Object createGame(Request request, Response response) throws ResponseException {
        String authToken = request.headers("authorization");
        var game = new Gson().fromJson(request.body(), GameData.class);
        service.createGame(authToken, game);
        response.status(200);
        return new Gson().toJson(game.gameID());
    }

    private Object listGames(Request request, Response response) {
        return null;
    }

    private Object logoutUser(Request request, Response response) throws ResponseException {
        String authToken = request.headers("authorization");
        service.logoutUser(authToken);
        response.status(200);
        return "{}";
    }

    private Object loginUser(Request request, Response response) throws ResponseException {
        var user = new Gson().fromJson(request.body(), UserData.class);
        AuthData auth = service.loginUser(user);
        response.status(200);
        return new Gson().toJson(auth);
    }

    private Object registerUser(Request request, Response response) throws ResponseException {
        var user = new Gson().fromJson(request.body(), UserData.class);
        AuthData auth = service.registerUser(user);
        response.status(200);
        return new Gson().toJson(auth);
    }

    private Object clear(Request request, Response response) {
        service.clear();
        response.status(200);
        return "{}";
    }
}
