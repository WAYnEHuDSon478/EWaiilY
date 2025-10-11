// 代码生成时间: 2025-10-11 20:12:02
package com.example.game;

import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnError;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import io.micronaut.websocket.exceptions.WebSocketException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A WebSocket server endpoint for handling multiplayer game network operations.
 */
@ServerWebSocket("/game")
public class MultiplayerGameNetwork {

    private final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // Method to handle new WebSocket connections.
    @OnOpen
    public void onOpen(WebSocketSession session) {
        sessions.put(session.getId(), session);
        session.sendText("Connected to the multiplayer game network.");
    }

    // Method to handle incoming messages.
    @OnMessage
    public void onMessage(WebSocketSession session, String message) {
        // Broadcast the message to all connected sessions.
        sessions.forEach((id, sess) -> {
            if (!id.equals(session.getId())) {
                try {
                    sess.sendText(message);
                } catch (IOException e) {
                    throw new WebSocketException("Failed to send message to session: " + id, e);
                }
            }
        });
    }

    // Method to handle WebSocket session closure.
    @OnClose
    public void onClose(WebSocketSession session) {
        sessions.remove(session.getId());
    }

    // Method to handle errors in WebSocket sessions.
    @OnError
    public void onError(WebSocketSession session, Throwable cause) {
        cause.printStackTrace();
        sessions.remove(session.getId());
    }

    // Method to send a message to a specific session.
    public void sendMessageToSession(String sessionId, String message) {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null) {
            try {
                session.sendText(message);
            } catch (IOException e) {
                throw new WebSocketException("Failed to send message to session: " + sessionId, e);
            }
        } else {
            throw new WebSocketException("Session not found: " + sessionId);
        }
    }

    // Method to broadcast a message to all connected sessions.
    public void broadcastMessage(String message) {
        sessions.forEach((id, session) -> {
            try {
                session.sendText(message);
            } catch (IOException e) {
                throw new WebSocketException("Failed to send message to session: " + id, e);
            }
        });
    }
}
