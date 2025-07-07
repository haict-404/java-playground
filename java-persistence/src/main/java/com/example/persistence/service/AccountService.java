package com.example.persistence.service;

import com.example.persistence.entity.Account;
import com.example.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final AccountSubService accountSubService;

  @Transactional
  public void updateAccount() {
    var accountOpt = accountRepository.findById(662339);
    if (accountOpt.isPresent()) {
      Account account = accountOpt.get();
      /*
      This setter will be persisted in the database when the transaction is committed (method completed)
      -> entityManager.persist(account); not needed, as the entity is already managed
      */
      account.setLastName("main");

      try {
        /*
        Calling updateOther() in another class -> proxy is used -> AOP is working and transaction propagation is working
        If updateOther() fails, the main transaction will still be committed because it is in new transaction
        */
        accountSubService.updateOther();

        /*
        If use updateOther() in this class -> proxy is not used -> AOP not working and transaction propagation is not working
        -> main transaction will be rolled back too
        */
//        updateOther();

        System.out.println("Account updated: " + account);
      } catch (Exception e) {
        System.out.println("Error updating account: " + e.getMessage());
      }
    } else {
      System.out.println("Account not found");
    }
  }

  // This method is called directly in class, not through the proxy
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  protected void updateOther() {
    var accountOpt = accountRepository.findById(735827);

    if (accountOpt.isPresent()) {
      Account account = accountOpt.get();
      account.setLastName(null); //this will lead to error in db because of not-null constraint
      System.out.println("Account updated: " + account);
    } else {
      System.out.println("Account not found");
    }
  }

}
