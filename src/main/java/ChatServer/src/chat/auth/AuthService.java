package ChatServer.src.chat.auth;

public interface AuthService {

    void start();
    String getUsernameByLoginAndPassword(String login, String password);
    void close();

}
