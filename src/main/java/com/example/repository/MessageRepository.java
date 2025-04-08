package com.example.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
      // JPA method naming convention. Retrieve all messages by postedBy
    List <Message> findAllBypostedBy(int id);

    //returns message postedBy the specified postedBy id
     Optional<Message> findBypostedBy(int id);

    // finds message by message id
    Message findByMessageId(int message_id);
    
}
