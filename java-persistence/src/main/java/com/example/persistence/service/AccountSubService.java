package com.example.persistence.service;

import com.example.persistence.entity.Account;
import com.example.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountSubService {

  private final AccountRepository accountRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  protected void updateOther() {
    var accountOpt = accountRepository.findById(735827);

    if (accountOpt.isPresent()) {
      Account account = accountOpt.get();
      account.setLastName(null); //this will lead to error because of not-null constraint, this transaction will be rolled back
      System.out.println("Account updated: " + account);
    } else {
      System.out.println("Account not found");
    }
  }
}
