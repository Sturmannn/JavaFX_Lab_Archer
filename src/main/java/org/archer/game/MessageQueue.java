package org.archer.game;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private BlockingQueue<Response> queue = new LinkedBlockingQueue<>();

    public void addResponse(Response response) {
        queue.add(response);
    }

    public Response getNextResponse() {
        return queue.poll();
    }
}