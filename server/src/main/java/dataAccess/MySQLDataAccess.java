package dataAccess;

import com.google.gson.Gson;
import models.*;
import org.springframework.security.core.parameters.P;
import server.ResponseException;

import java.sql.*;
import java.util.Collection;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLDataAccess implements DataAccess {

    public MySQLDataAccess() throws ResponseException {
        configureDatabase();
    }

    public String generateAuth() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void clear() throws DataAccessException {
        try {
            var statements = new String[]{"TRUNCATE users", "TRUNCATE authdata", "TRUNCATE games"};
            for (var statement : statements) {
                executeUpdate(statement);
            }
        } catch (ResponseException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void registerUser(UserData user) throws DataAccessException {
        try {
            var statement = "INSERT INTO users (name, password, email, json) VALUES (?, ?, ?, ?)";
            var json = new Gson().toJson(user);
            executeUpdate(statement, user.username(), user.password(), user.email(), json);
        } catch (ResponseException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public boolean userExists(String user) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT name FROM users WHERE name=?";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setString(1, user);
                try (var rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public UserData getUser(UserData user) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT name, json FROM users WHERE name=?";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setString(1, user.username());
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, UserData.class);
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        try {
            var auth = new AuthData(generateAuth(), username);
            var statement = "INSERT INTO authData (auth, name) VALUES (?, ?)";
            executeUpdate(statement, auth.authToken(), auth.username());
            return auth;
        } catch (ResponseException e) {
            throw new DataAccessException(e.getMessage());
        }
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
              `name` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`name`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS  authdata (
              `auth` varchar(256) NOT NULL,
              `name` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`auth`),
              INDEX(auth),
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `blackplayer` int,
              `whiteplayer` int,
              `observers` int,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`)
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
