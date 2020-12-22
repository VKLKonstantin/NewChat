package ChatClient.src.client.controllers;


import ChatClient.src.client.NetworkClient;
import ChatClient.src.client.models.Network;

import java.awt.*;

public class AuthController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;

    private Network network;
    private NetworkClient networkCliet;


    @FXML
    public void checkAuth() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isBlank() || password.isBlank()) {
            NetworkClient.showErrorMessage("Ошибка авторизации", "Ошибка ввода", "Поля не должны быть пустыми");
            return;
        }

        String authErrorMessage = network.sendAuthCommand(login, password);
        if (authErrorMessage != null) {
            NetworkClient.showErrorMessage("Ошибка авторизации", "Что-то не то", authErrorMessage);
        } else {
            networkCliet.openMainChatWindow();
        }

    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setNetworkClient(NetworkClient networkCliet) {
        this.networkCliet = networkCliet;
    }
}
