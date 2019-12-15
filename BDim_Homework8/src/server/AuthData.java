package server;

import java.util.ArrayList;
import java.util.List;

public class AuthData implements Autherization {
    private class User {
        private String login;
        private String password;
        private String nick;

        public User(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }
    private List<User> users;

    public AuthData() {
        this.users = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            users.add(new User("l" + i, "p" + i, "nick" + i));
        }
    }
    @Override
    public String getNick(String login, String password) {
        String nick = null;
        for (User user : users) {
            if (user.login.equals(login) && user.password.equals(password)) {
                nick = user.nick;
                break;
            }
        }
        return nick;
    }
    @Override
    public boolean registration (String nick, String login, String password){
        for (User user : users) {
            if (user.login.equals(login)) {
                return false;
            }
        }
        users.add(new User(login, password, nick));
        return true;
    }
}