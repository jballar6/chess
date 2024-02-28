package serviceTests;

import dataAccess.MemoryDataAccess;
import model.GameData;
import model.UserData;
import requests.JoinGameRequest;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import service.ChessService;

import static org.junit.jupiter.api.Assertions.*;

class ChessServiceTest {

    @Test
    void clear() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        service.registerUser(testUser1);
        service.clear();
        assert(dao.usersSize() == 0);
        assert(dao.authSize() == 0);
        assert(dao.gamesSize() == 0);
    }

    @Test
    void registerUserSuccess() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        service.registerUser(testUser1);
        assert(dao.usersSize() == 1);
    }

    @Test
    void registerUserFailure() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        service.registerUser(testUser1);
        assertThrows(ResponseException.class, () -> service.registerUser(testUser1));
    }

    @Test
    void loginUserSuccess() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        service.registerUser(testUser1);
        var auth = service.loginUser(testUser1);
        assertNotNull(auth.authToken());
    }

    @Test
    void loginUserFailure() {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        assertThrows(ResponseException.class, () -> service.loginUser(testUser1));
    }

    @Test
    void logoutUserSuccess() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        var auth = service.registerUser(testUser1);
        service.logoutUser(auth.authToken());
        assert(dao.authSize() == 0);
    }

    @Test
    void logoutUserFailure() {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        assertThrows(ResponseException.class, () -> service.logoutUser(""));
    }

    @Test
    void listGamesSuccess() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        var auth = service.registerUser(testUser1);
        var game = new GameData(0, null, null, "testGame", "");
        service.createGame(auth.authToken(), game);
        assert(!service.listGames(auth.authToken()).games().isEmpty());
    }

    @Test
    void listGamesFailure() {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        assertThrows(ResponseException.class, () -> service.listGames(null));
    }

    @Test
    void createGameSuccess() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        var auth = service.registerUser(testUser1);
        var game = new GameData(0, null, null, "testGame", "");
        var createGameResponse = service.createGame(auth.authToken(), game);
        assert(createGameResponse.gameID() > 0);
    }

    @Test
    void createGameFailure() {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        var game = new GameData(0, null, null, "testGame", "");
        assertThrows(ResponseException.class, () -> service.createGame(null, game));
    }

    @Test
    void joinGameSuccess() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var testUser1 = new UserData("user", "pass", "email@email.com");
        var auth = service.registerUser(testUser1);
        var game = new GameData(0, null, null, "testGame", "");
        var createGameResponse = service.createGame(auth.authToken(), game);
        var joinGame = new JoinGameRequest("BLACK", createGameResponse.gameID());
        assertDoesNotThrow(() -> service.joinGame(auth.authToken(), joinGame));
    }

    @Test
    void joinGameFailure() throws ResponseException {
        var dao = new MemoryDataAccess();
        var service = new ChessService(dao);
        var game = new GameData(0, null, null, "testGame", "");
        var joinGame = new JoinGameRequest("BLACK", game.gameID());
        assertThrows(ResponseException.class, () -> service.joinGame(null, joinGame));
    }
}