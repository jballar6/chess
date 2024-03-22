package dataAccess;

import com.google.gson.Gson;
import models.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import exception.ResponseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLDataAccess implements DataAccess {

    public MySQLDataAccess() throws ResponseException {
        configureDatabase();
    }

    private String generateAuth() {
        return UUID.randomUUID().toString();
    }

    private String encrpytPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
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
            var password = encrpytPassword(user.password());
            user = user.setPassword(password);
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

    private AuthData getUserFromAuth(String authToken) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT json FROM authdata WHERE auth=?";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, AuthData.class);
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        try {
            var auth = new AuthData(generateAuth(), username);
            var statement = "INSERT INTO authdata (auth, name, json) VALUES (?, ?, ?)";
            var json = new Gson().toJson(auth);
            executeUpdate(statement, auth.authToken(), auth.username(), json);
            return auth;
        } catch (ResponseException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public boolean getAuth(String authToken) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT auth FROM authdata WHERE auth=?";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try {
            var statement = "DELETE FROM authdata WHERE auth=?";
            executeUpdate(statement, authToken);
        } catch (ResponseException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public boolean getAuthFromUser(String username) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT name, auth FROM authdata WHERE name=?";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void deleteAuthFromUser(String username) throws DataAccessException {
        try {
            var statement = "DELETE FROM authdata WHERE name=?";
            executeUpdate(statement, username);
        } catch (ResponseException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        var result = new ArrayList<GameData>();
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT id, json FROM games";
            try (var ps = connection.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return result;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT json FROM games WHERE id=?";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        try {
            var statement = "INSERT INTO games (name, json) VALUES (?, ?)";
            var id = getGameCount();
            if (id == 0) {
                game = game.setGameID(1);
            } else {
                game = game.setGameID(id+1);
            }
            var json = new Gson().toJson(game);
            executeUpdate(statement, game.gameName(), json);
            return game;
        } catch (ResponseException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

   private int getGameCount() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT COUNT(*) FROM games";
            try (var ps = connection.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    rs.next();
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void joinGame(String authToken, int gameID, String playerColor) throws DataAccessException {
        try {
            var user = getUserFromAuth(authToken);
            var game = getGame(gameID);
            String statement;

            if (user != null) {
                if (Objects.equals(playerColor, "BLACK")) {
                    game = game.setBlackUsername(user.username());
                    statement = "UPDATE games SET blackplayer = ?, json = ? WHERE id=?";
                } else {
                    game = game.setWhiteUsername(user.username());
                    statement = "UPDATE games SET whiteplayer = ?, json = ? WHERE id=?";
                }

                var json = new Gson().toJson(game);
                executeUpdate(statement, user.username(), json, gameID);
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, GameData.class);
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
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `blackplayer` varchar(256),
              `whiteplayer` varchar(256),
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
