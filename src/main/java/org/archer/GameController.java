package org.archer;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import org.archer.elements.*;
import org.archer.game.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class GameController implements IObserver {

    @FXML
    private AnchorPane mainWindow;
    @FXML
    private Label shotCountLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Circle nearTargetCircle;
    @FXML
    private Circle distantTargetCircle;
    @FXML
    private Line nearTargetLine;
    @FXML
    private Line distantTargetLine;
    @FXML
    private AnchorPane shooterPane;
    @FXML
    private Polygon shooter;
    @FXML
    private AnchorPane mainField;
    @FXML
    private VBox scorePane;

    Model model = BModel.getModel();
    SocketClientWrapper socket_client = null;
    int port = 3124;
    InetAddress ip = null;
    String nickName = null;

    @FXML
    private void initialize() {
        model.addObserver(this);
    }

    @FXML
    protected void onStartGameButtonClick() {
        if (socket_client != null) {
            Message msg = new Message(null, MessageAction.START);
            socket_client.sendMessage(msg);
        }
    }

    @FXML
    protected void onStopGameButtonClick() {
        if (socket_client != null) {
            Message msg = new Message(null, MessageAction.STOP);
            socket_client.sendMessage(msg);
//            socket_client = null;
        }
    }

    @FXML
    protected void onPauseGameButtonClick() {
        if (socket_client != null) {
            Message msg = new Message(null, MessageAction.PAUSE);
            socket_client.sendMessage(msg);
        }
    }

    @FXML
    protected void onShotButtonClick() {
        if (socket_client != null) {
            Message msg = new Message(null, MessageAction.SHOOT);
//            System.out.println("Message: " + msg.toString());
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
//    public boolean isNicknameTaken(String nickName) {
////        int port = 3124;
////        InetAddress ip;
//        Socket cs;
//        DataInputStream dis;
//        DataOutputStream dos;
//        Gson gson = new Gson();
//
//        try {
//            ip = InetAddress.getLocalHost();
//            cs = new Socket(ip, port); // клиент-сокет
//            dis = new DataInputStream(cs.getInputStream());
//            dos = new DataOutputStream(cs.getOutputStream());
//
//            // Отправляем сообщение серверу
//            Message msg = new Message(new Archer(0, 0, nickName), MessageAction.NICKNAME_CHECK);
//            System.out.println("Message: " + msg.toString());
//            String messageStr = gson.toJson(msg);
//            dos.writeUTF(messageStr);
//
//            // Получаем ответ от сервера
//            String responseStr = dis.readUTF();
//            Response response = gson.fromJson(responseStr, Response.class);
//
//            // Проверяем, есть ли уже такие имена среди лучников
//            List<Archer> archers = response.getArchers();
//            System.out.println("Archers: " + archers.toString());
//            for (Archer archer : archers) {
//                if (archer.getNickName().equals(nickName)) {
//                    return true;
//                }
//            }
//
//            // Закрываем соединение
//            cs.close();
//        } catch (IOException e) {
//            System.err.println("Error in isNicknameTaken: " + e.getMessage());
//        }
//
//        return false;
//    }
public int connect() { // 0 - error, 1 - nickname is taken, 2 - connected, 3 - overflown
    if (socket_client == null) {
        try {
            System.out.println("В сокете клиента");
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
            if (response.getArchers().stream().anyMatch(a -> a.getNickName().equals(nickName))) {
                System.err.println("Nickname is already taken");
//                socket_client = null;
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
    }
    else {
        System.out.println("В коннекшн эстаблише");
        // Проверяем, занят ли ник
        Message checkMsg = new Message(new Archer(0, 0, nickName), MessageAction.NICKNAME_CHECK);
        socket_client.sendMessage(checkMsg);
        Response response = socket_client.receiveResponse();
        if (response.getArchers().stream().anyMatch(a -> a.getNickName().equals(nickName))) {
            System.err.println("Nickname is already taken");
//                socket_client = null;
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
        synchronized (model) {
            Response response = socket_client.getMessageQueue().getNextResponse();
            Platform.runLater(() -> {
                shooterPane.getChildren().clear();
                mainField.getChildren().clear();
                scorePane.getChildren().clear();

                mainField.getChildren().addAll(model.getTargets().getNearTargetLineFX(), model.getTargets().getDistantTargetLineFX(),
                        model.getTargets().getNearTargetCircleFX(), model.getTargets().getDistantTargetCircleFX());
                for (Archer archer : model.getArchers()) {
                    Label archerNick = new Label("Nick: " + archer.getNickName());
                    Label archerPoints = new Label("Points: " + archer.getScore().getPoints());
                    Label archerShotCount = new Label("Shot count: " + archer.getScore().getShotCount());
                    ScorePane archerScore = new ScorePane(archerNick, archerPoints, archerShotCount);
                    scorePane.getChildren().add(archerScore.getScorePane());

                    shooterPane.getChildren().add(archer.getArcherFX());
                    archer.getArrows().forEach(arrow -> {
                        mainField.getChildren().add(arrow.getArrowHeadFX());
                        mainField.getChildren().add(arrow.getArrowShaftFX());
                    });
                    if (archer.isWinner()) {
                        System.out.println("Winner: " + archer.getNickName());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setHeaderText(null);
                        alert.setContentText("Winner: " + archer.getNickName());

                        alert.show();
                    }
                }
            });
        }
    }
    public void ShowWinner(String nickName) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Winner: " + nickName);

            alert.showAndWait();
        });
    }
}