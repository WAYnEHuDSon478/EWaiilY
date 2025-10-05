// 代码生成时间: 2025-10-06 03:53:25
package com.example.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.HttpResponse;
import io.micronaut.core.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 师生互动工具控制器
 */
@Controller("/api/interaction")
public class TeacherStudentInteractionService {

    private final Map<String, Message> messageStore = new HashMap<>();

    /**
     * 发送消息
     *
     * @param message 消息对象
     * @return HTTP响应
     */
    @Post("/messages")
    public HttpResponse<Message> sendMessage(@Body Message message) {
        try {
            message.setId(UUID.randomUUID().toString());
            messageStore.put(message.getId(), message);
            return HttpResponse.ok(message);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    /**
     * 获取特定消息
     *
     * @param messageId 消息ID
     * @return HTTP响应
     */
    @Get("/messages/{messageId}")
    public HttpResponse<Message> getMessage(@PathVariable String messageId) {
        Message message = messageStore.get(messageId);
        if (message == null) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(message);
    }

    /**
     * 消息对象
     */
    public static class Message {
        private String id;
        private String sender;
        private String receiver;
        private String content;

        public Message() {
        }

        public Message(String sender, String receiver, String content) {
            this.sender = sender;
            this.receiver = receiver;
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}