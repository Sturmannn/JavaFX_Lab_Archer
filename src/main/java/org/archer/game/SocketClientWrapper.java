package org.archer.game;

import com.google.gson.Gson;
import org.archer.elements.BModel;
import org.archer.elements.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClientWrapper {
    final Model model = BModel.getModel();
    final Gson gson = new Gson();
    final Socket client_socket;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    boolean isRunning = false;

    public SocketClientWrapper(final Socket client_socket) {
        this.client_socket = client_socket;
        try {
            is = client_socket.getInputStream();
            os = client_socket.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
        } catch (Exception e) {
            System.err.println("Error in SocketClientWrapper constructor: " + e.getMessage());
        }
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
            synchronized(model) {
                model.setArchers(response.getArchers());
                model.setTargets(response.getTargets());
                model.notifyObservers();
            }
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

    public void sendMessage(final Message message) {
        try {
            String messageStr = gson.toJson(message);
            dos.writeUTF(messageStr);
        } catch (Exception e) {
            System.err.println("Error in SocketClientWrapper sendMessage: " + e.getMessage());
        }
    }
}