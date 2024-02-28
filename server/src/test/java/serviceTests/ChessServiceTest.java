package serviceTests;

import dataAccess.MemoryDataAccess;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
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
    void listGamesSuccess() {
    }

    @Test
    void listGamesFailure() {
    }

    @Test
    void createGameSuccess() {
    }

    @Test
    void createGameFailure() {
    }

    @Test
    void joinGameSuccess() {
    }

    @Test
    void joinGameFailure() {
    }
}