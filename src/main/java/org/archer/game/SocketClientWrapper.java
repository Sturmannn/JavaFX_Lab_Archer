package org.archer.game;

import com.google.gson.Gson;
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
            Response response = receiveResponse();
            if (response == null) {
                break;
            }
            model.setArchers(response.getArchers());
//            model.getArchers().forEach(archer -> System.out.println(archer.getArrows().toString()));
            model.setTargets(response.getTargets());
        }
    }

    public Response receiveResponse() {
        try {
            String responseStr = dis.readUTF();
            return gson.fromJson(responseStr, Response.class);
        } catch (Exception e) {
            System.err.println("Error in SocketClientWrapper receiveMessage: " + e.getMessage());
            return null;
        }
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
