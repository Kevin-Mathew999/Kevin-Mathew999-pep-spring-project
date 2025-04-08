package com.example.service;
import org.springframework.stereotype.Service;

import com.example.entity.Account;

@Service
public class AccountService {

      //shortcut to check if account credentials meet criteria
       public Boolean validCredentials(Account account){
        
        if(!account.getUsername().isEmpty() && account.getPassword().length() >= 4 ){
            return true;
        }else{
            System.out.println("Review login credentials criteria");
            return false;
        }
    }
}
