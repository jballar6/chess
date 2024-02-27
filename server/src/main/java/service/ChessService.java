package service;

import dataAccess.DataAccess;

public class ChessService {

    private final DataAccess dataAccess;

    public ChessService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    //endpoints:
    // clear application
    public void clearDatabase() {
        dataAccess.clearDatabase();
    }
    // register
    // login
    // logout
    // list games
    // create game
    // join game
}
