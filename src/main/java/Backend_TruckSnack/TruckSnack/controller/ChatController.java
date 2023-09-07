package Backend_TruckSnack.TruckSnack.controller;

import Backend_TruckSnack.TruckSnack.domain.ChatMessage;
import Backend_TruckSnack.TruckSnack.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {
    @GetMapping("/chat")
    public String chat() {
        return "chat"; // chat.html 파일명과 일치해야 합니다.
    }
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.getAllMessages")
    @SendTo("/topic/public")
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAllByOrderByTimestampDesc();
    }
}