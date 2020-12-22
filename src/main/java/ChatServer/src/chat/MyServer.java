package ChatServer.src.chat;


import ChatServer.src.chat.auth.AuthService;
import ChatServer.src.chat.auth.BaseAuthService;
import ChatServer.src.chat.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final ServerSocket serverSocket;
    private final AuthService authService;

    private final List<ClientHandler> clients = new ArrayList<>();

    public MyServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.authService = new BaseAuthService();
    }


    public void start() throws IOException {
        System.out.println("Сервер запущен");

        authService.start();
        try {
            while (true) {
                waitAndProcessNewClientConnection();
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания нового подключения");
        } finally {
            serverSocket.close();
        }

    }

    private void waitAndProcessNewClientConnection() throws IOException {
        System.out.println("Ожидание пользователя...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Клиент подключился");
        processClientConnection(clientSocket);

    }

    private void processClientConnection(Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.handle();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void subscribe(ClientHandler clientHandler) {//добавить пользователя
        clients.add(clientHandler);
    }

    public void unSubscribe(ClientHandler clientHandler) {//удалить пользователя
        clients.remove(clientHandler);
    }

    public boolean isUsernameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }

        }
        return false;
    }

    public void broadcastMessage(String message, ClientHandler sender, boolean isServerInfoMsg) throws IOException {

        for (ClientHandler client : clients) {
            if (client == sender) {
                continue;
            }
            client.sendMessage(isServerInfoMsg ? null :sender.getUsername(), message);
        }
    }
}
