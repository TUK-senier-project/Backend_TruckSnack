package Backend_TruckSnack.TruckSnack.repository;

import Backend_TruckSnack.TruckSnack.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByOrderByTimestampDesc();
}
