package org.archer.game;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.archer.elements.Archer;
import org.archer.elements.BModel;
import org.archer.elements.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClientWrapper {
    Model model = BModel.getModel();
    Gson gson = new Gson();
    Socket client_socket = null;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    MessageQueue messageQueue = new MessageQueue();

    boolean isRunning = false;

    public SocketClientWrapper(Socket client_socket) {
        this.client_socket = client_socket;
        try {
            is = client_socket.getInputStream();
            os = client_socket.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
        } catch (Exception e) {
            System.err.println("Error in SocketClientWrapper constructor: " + e.getMessage());
        }
//        new Thread(this::run).start();
    }

    public void Start() {
        if (isRunning) return;
        isRunning = true;
        new Thread(this::run).start();
    }

    private void run() {
        while (true) {
            synchronized (model) {
            Response response = receiveResponse();
            if (response == null) {
                break;
            }

                model.setArchers(response.getArchers());
                model.setTargets(response.getTargets());
                for (Archer archer : model.getArchers()) {
                    System.out.println(archer);
                }
                model.notifyObservers();
            }
//            model.setArchers(response.getArchers());
//            model.setTargets(response.getTargets());
//            for (Archer archer : model.getArchers()) {
////
//                System.out.println("Archer: " + archer.isWinner());
//            }
////            synchronized (model) {
//                model.notifyObservers();
////            }
        }
    }
    public void ShowWinner(String nickName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Winner: " + nickName);

        alert.showAndWait();
    }

    public Response receiveResponse() {
        try {
            String responseStr = dis.readUTF();
            Response response = gson.fromJson(responseStr, Response.class);
            messageQueue.addResponse(response); // добавляем полученный ответ в очередь
//            System.out.println("Response: " + response.getArchers());
            return response;
        } catch (Exception e) {
            System.err.println("Error in SocketClientWrapper receiveMessage: " + e.getMessage());
            return null;
        }
    }

    public MessageQueue getMessageQueue() {
        return messageQueue;
    }

    public void sendMessage(Message message) {
        try {
            String messageStr = gson.toJson(message);
            dos.writeUTF(messageStr);
        } catch (Exception e) {
            System.err.println("Error in SocketClientWrapper sendMessage: " + e.getMessage());
        }
    }
}
