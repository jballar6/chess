package dataAccess;

public interface DataAccess {
    void clearDatabase();

    void registerUser();

    void loginUser();

    void logoutUser();

    void listGames();

    void createGame();

    void joinGame();
}
