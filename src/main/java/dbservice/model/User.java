package dbservice.model;

public class User {
    private long id;
    private String login;
    private String pass;
    private long lastTime;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public User(long id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }

    public User(long id, String login, String pass, long lastTime) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.lastTime = lastTime;
    }

    public User(String login, String pass, long lastTime) {
        this.login = login;
        this.pass = pass;
        this.lastTime = lastTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", lastTime=" + lastTime +
                '}';
    }
}
