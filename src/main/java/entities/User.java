package entities;

public class User {
    private Long id;
    private String username;
    private String password_hash;
    private String salt;
    public User() {

    }

    public User(String username, String password_hash, String salt) {
        this.username = username;
        this.password_hash = password_hash;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
