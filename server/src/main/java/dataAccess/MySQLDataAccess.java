package dataAccess;

import models.*;
import server.ResponseException;

import java.sql.*;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLDataAccess implements DataAccess {

    public MySQLDataAccess() throws DataAccessException, ResponseException {
        configureDatabase();
    }

    @Override
    public void clear() {

    }

    @Override
    public void registerUser(UserData user) throws DataAccessException {

    }

    @Override
    public boolean userExists(String user) throws DataAccessException {
        return false;
    }

    @Override
    public UserData getUser(UserData user) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public boolean getAuth(String username) throws DataAccessException {
        return false;
    }

    @Override
    public void deleteAuth(String username) throws DataAccessException {

    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        return null;
    }

    @Override
    public void joinGame(String authToken, int gameID, String playerColor) throws DataAccessException {

    }

    private int executeUpdate(String statement, Object... params) throws ResponseException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var ps = connection.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `auth` int,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(auth),
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `blackplayer` int,
              `whiteplayer` int,
              `observers` int,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(type)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws ResponseException {
        DatabaseManager.createDatabase();
        try (var connection = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = connection.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }
}
