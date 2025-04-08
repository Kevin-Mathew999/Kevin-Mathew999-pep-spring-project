package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
      // JPA method naming convention. Retrieve all messages by account Id
    List <Message> findAllBypostedBy(int id);
    
}
