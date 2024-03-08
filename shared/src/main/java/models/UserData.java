package models;

public record UserData(String username, String password, String email) {
    public UserData setPassword(String newPassword) {
        return new UserData(username, newPassword, email);
    }
}
