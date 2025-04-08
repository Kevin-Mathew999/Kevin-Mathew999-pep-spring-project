package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Integer>  {
      
  

    // custom login validation to ensure username and password exist
    @Query("Select CASE WHEN Count(a) > 0 THEN true ELSE false END FROM Account a WHERE a.username = :username AND a.password = :password")
    boolean existsByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    //returns account id along with the username and password of a requested account
    @Query("Select a.id,a.username,a.password From Account a WHERE a.username = :username AND a.password =:password")
    Account findAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    // custom method to check for the existence of username
    boolean existsByUsername(String username);


}
