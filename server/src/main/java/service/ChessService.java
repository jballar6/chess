package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;

public class ChessService {

    private final DataAccess dataAccess;

    public ChessService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    //endpoints:
    // clear application
    public void clear() {
        dataAccess.clear();
    }
    // register
    public void registerUser(String username, String password, String email) throws DataAccessException {
        dataAccess.registerUser(username, password, email);
    }
    // login
    // logout
    // list games
    // create game
    // join game
}
