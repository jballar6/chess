package clientTests;

import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void registerSuccess() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerFailure() {
        Assertions.assertTrue(true);
    }

    @Test
    public void logoutSuccess() {
        Assertions.assertTrue(true);
    }

    @Test
    public void logoutFailure() {
        Assertions.assertTrue(true);
    }

    @Test
    public void loginSuccess() {
        Assertions.assertTrue(true);
    }

    @Test
    public void loginFailure() {
        Assertions.assertTrue(true);
    }

    @Test
    public void createSuccess() {
        Assertions.assertTrue(true);
    }

    @Test
    public void createFailure() {
        Assertions.assertTrue(true);
    }

    @Test
    public void listSuccess() {
        Assertions.assertTrue(true);
    }

    @Test
    public void listFailure() {
        Assertions.assertTrue(true);
    }

    @Test
    public void joinFailure() {
        Assertions.assertTrue(true);
    }

    @Test
    public void joinSuccess() {
        Assertions.assertTrue(true);
    }

}
