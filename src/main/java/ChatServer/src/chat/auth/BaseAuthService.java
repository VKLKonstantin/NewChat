package ChatServer.src.chat.auth;

import ChatServer.src.chat.User;

import javax.swing.text.html.ListView;
import java.util.List;

public class BaseAuthService implements AuthService{

    public static final List<User> client = List.of(
            new User("User1", "1111", "Konstantin"),
             new User("User2", "2222", "Ivan"),
              new User("User3", "3333", "Alex")
    );

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for(User client : client){
            if (client.getLogin().equals(login)&&client.getPassword().equals(password)){
                return client.getUsername();
            }
        }

        return null;
    }

    @Override
    public void close() {
        System.out.println("Сервис аутентификации завершен");
    }
}
