package org.archer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.archer.elements.*;
import org.archer.game.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.InetAddress;
import java.net.Socket;

public class GameController implements IObserver {

    @FXML
    private AnchorPane shooterPane;
    @FXML
    private AnchorPane mainField;
    @FXML
    private VBox scorePane;

    final Model model = BModel.getModel();
    SocketClientWrapper socket_client = null;
    final int port = 3124;
    InetAddress ip = null;
    String nickName = null;

    @FXML
    private void initialize() {
        model.addObserver(this);
    }

    @FXML
    protected void onStartGameButtonClick() {
        if (socket_client != null) {
            Message msg = new Message(new Archer(0, 0, nickName), MessageAction.START);
            socket_client.sendMessage(msg);
        }
    }

    @FXML
    protected void onStopGameButtonClick() {
        if (socket_client != null) {
            Message msg = new Message(new Archer(0, 0, nickName), MessageAction.STOP);
            socket_client.sendMessage(msg);
        }
    }

    @FXML
    protected void onPauseGameButtonClick() {
        if (socket_client != null) {
            Message msg = new Message(new Archer(0, 0, nickName), MessageAction.PAUSE);
            socket_client.sendMessage(msg);
        }
    }

    @FXML
    protected void onShotButtonClick() {
        if (socket_client != null) {
            Message msg = new Message(new Archer(0, 0, nickName), MessageAction.SHOOT);
            socket_client.sendMessage(msg);
        }
    }

    @FXML
    protected void shooterChangePosition(MouseEvent event) {
        if (socket_client != null) {
            double x = event.getX();
            double y = event.getY();
            Message msg = new Message(new Archer(x, y, nickName), MessageAction.CHANGE_POSITION);
            socket_client.sendMessage(msg);
        }
    }

    public void setNickname(String nickname) {
        this.nickName = nickname;
    }

    public int connect() { // 0 - error, 1 - nickname is taken, 2 - connected, 3 - overflown
        if (socket_client == null) {
            try {
                System.out.println("В сокете клиента" + " " + nickName);
                ip = InetAddress.getLocalHost();
                Socket cs = new Socket(ip, port); // клиент-сокет
                System.out.println("Client connected to socket");
                socket_client = new SocketClientWrapper(cs);

                // Проверяем, занят ли ник
                Message checkMsg = new Message(new Archer(0, 0, nickName), MessageAction.NICKNAME_CHECK);
                socket_client.sendMessage(checkMsg);
                Response response = socket_client.receiveResponse();
                if (response.getArchers().size() == 4)
                    return 3;
                if (response.getArchers().values().stream().anyMatch(a -> a.getNickName().equals(nickName))) {
                    System.err.println("Nickname is already taken");
                    return 1;
                }

                // Если ник не занят, отправляем сообщение CONNECT
                Message connectMsg = new Message(new Archer(0, 0, nickName), MessageAction.CONNECT);
                socket_client.Start();
                socket_client.sendMessage(connectMsg);
                return 2;
            } catch (Exception e) {
                System.err.println("Error in GameController connect: " + e.getMessage());
                return 0;
            }
        } else {
            System.out.println("В коннекшн эстаблише");
            // Проверяем, занят ли ник
            Message checkMsg = new Message(new Archer(0, 0, nickName), MessageAction.NICKNAME_CHECK);
            socket_client.sendMessage(checkMsg);
            Response response = socket_client.receiveResponse();
            if (response.getArchers().values().stream().anyMatch(a -> a.getNickName().equals(nickName))) {
                System.err.println("Nickname is already taken");
                return 1;
            }

            // Если ник не занят, отправляем сообщение CONNECT
            Message connectMsg = new Message(new Archer(0, 0, nickName), MessageAction.CONNECT);
            socket_client.Start();
            socket_client.sendMessage(connectMsg);
            return 2;
        }
    }

    @Override
    public void eventHandler(Model model) {
        synchronized(model) {
            Platform.runLater(() -> {
                    shooterPane.getChildren().clear();
            mainField.getChildren().clear();
            scorePane.getChildren().clear();

            mainField.getChildren().addAll(model.getTargets().getNearTargetLineFX(), model.getTargets().getDistantTargetLineFX(),
                    model.getTargets().getNearTargetCircleFX(), model.getTargets().getDistantTargetCircleFX());
            for (Archer archer: model.getArchers().values()) {
                Label archerNick = new Label("Nick: " + archer.getNickName());
                Label archerPoints = new Label("Points: " + archer.getScore().getPoints());
                Label archerShotCount = new Label("Shot count: " + archer.getScore().getShotCount());
                ScorePane archerScore = new ScorePane(archerNick, archerPoints, archerShotCount, archer.getColor());
                scorePane.getChildren().add(archerScore.getScorePane());

                shooterPane.getChildren().add(archer.getArcherFX());
                archer.getArrows().forEach(arrow -> {
                        mainField.getChildren().add(arrow.getArrowHeadFX());
                mainField.getChildren().add(arrow.getArrowShaftFX());
                    });
            }
            });
            for (Archer archer: model.getArchers().values()) {
                if (archer.isWinner()) {
                    Platform.runLater(() -> {
                            System.out.println("Winner: " + archer.getNickName());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText(null);
                    alert.setContentText("Winner: " + archer.getNickName());

                    alert.show();
                    });
                }
            }
        }
    }
}