package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.repository.MessageRepository;
import java.util.List;
import com.example.entity.Message;
import com.example.entity.Account;
import java.util.Optional;


@Service
public class MessageService {
    //declaring the message repository 
    private final MessageRepository messageRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
    //return a list of all messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();

    }

    // returning message by its id
    public Optional<Message> findMessageById(int id){
       return messageRepository.findById(id);
       
    }

    // delete message by its id
    public void deleteMessageById(int id){
        messageRepository.deleteById(id);
    }

    //update message by its id
    public void updateMessageById(int id, Message message){

    }

    //shortcut to check if a message body meets criteria
    
    
     public Boolean validMessage(String messagetext){
        return messagetext != null && !messagetext.isEmpty() && messagetext.length() <= 255;
        
    //     if(!messagetext.isEmpty() && messagetext.length() < 255 ){
    //         return false;
    //     }else{
    //         return true;
       }
    }

     



