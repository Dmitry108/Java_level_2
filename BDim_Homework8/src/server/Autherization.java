package server;

public interface Autherization {
    String getNick (String login, String password);
    boolean registration (String nick, String login, String password);
}
