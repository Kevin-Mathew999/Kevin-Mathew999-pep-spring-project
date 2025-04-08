package com.example.controller;

import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.util.List;
import com.example.service.MessageService;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.Optional;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


@Controller
 public class SocialMediaController {

    @Autowired
    private MessageRepository messageRepository;
    private MessageService messageService;
    private AccountRepository accountRepository;

    // get all messages handler
    @GetMapping("/messages")
    @ResponseBody
    public List<Message> getAllMessages(){
        return messageRepository.findAll();

}
    //get message by id handler
    @GetMapping("/messages/{message_id}")
    @ResponseBody
    public Optional<Message> getMessageById(@PathVariable Integer message_id){
        return messageRepository.findById(message_id);
    }
    
    // delete message by id handler
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Object> deleteMessageById(@PathVariable Integer message_id){
        long beforedelete = messageRepository.count();
        if(messageRepository.existsById(message_id)){
            messageRepository.deleteById(message_id);
            long afterdelete = messageRepository.count();
            return ResponseEntity.ok(beforedelete - afterdelete);
        }else{
            return ResponseEntity.ok().build();
        }
    }
    // update message by id
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Object> updateMessageById(@PathVariable Integer message_id, @RequestBody String messageText){
        Optional<Message> targetmessage = messageRepository.findById(message_id);
        if(messageRepository.existsById(message_id)){
            if(messageService.validMessage(messageText)){
                Message message = targetmessage.get();
                message.setMessageText(messageText);
                messageRepository.save(message);
                return ResponseEntity.ok(1);
            }else{
                return ResponseEntity.status(400).build();
            }
        }else{
            return ResponseEntity.status(400).build();
        }
    }

    // retrieve all messages by account id handler
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity <Object> retrieveAllMessagesByAccountID(@PathVariable Integer accountId){
        List <Message> messages = messageRepository.findAllBypostedBy(accountId);

        if(messages.isEmpty()){
            return ResponseEntity.status(200).build();

        }
        return ResponseEntity.ok(messages);
    }

    //processing the creation of new messages handler
    @PostMapping("/messages")
    public ResponseEntity <Message> messageCreationValidation(@RequestBody Message message){
        Optional<Account> candidate = accountRepository.findById(message.getPostedBy());

        if(candidate.isPresent()){
            if(messageService.validMessage(message.getMessageText())){
                Message savedMessage = messageRepository.save(message);

                return ResponseEntity.ok(savedMessage);
            }else{
                return ResponseEntity.status(400).build();
            }
        }else{
            return ResponseEntity.status(400).build();

        }
    }

    // user login handler
    @PostMapping("/login")
    public ResponseEntity <Account> loginValidation(@RequestBody Account account){
        if(accountRepository.existsByUsernameAndPassword(account.getUsername(),account.getPassword())){
            return ResponseEntity.ok(accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword()));

        }else{
            return ResponseEntity.status(401).build();
        }
    }

    // new user registration handler
    @PostMapping("/register")
    public ResponseEntity <Account> accountRegistration(@RequestBody Account account){
        if(messageService.validCredentials(account)){
            if(!accountRepository.existsByUsername(account.getUsername())){
                Account savedaccount = accountRepository.save(account);
                return ResponseEntity.ok(savedaccount);

            }else{
                return ResponseEntity.status(409).build();
            }
        }else{
            return ResponseEntity.status(400).build();
        }
    }

    


 }