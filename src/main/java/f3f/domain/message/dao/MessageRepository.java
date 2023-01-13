package f3f.domain.message.dao;

import f3f.domain.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {


//    @Query("select m from Message m  join fetch m.sender.id where m.sender.id = :sender_id")
//    List<Message> getSendListByUserId(@Param("sender_id") long sender_id);
}

