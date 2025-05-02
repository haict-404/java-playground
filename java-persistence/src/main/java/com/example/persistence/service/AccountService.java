package com.example.persistence.service;

import com.example.persistence.entity.Account;
import com.example.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final AccountSubService accountSubService;

  public void printAccounts() {
    long count = accountRepository.count();
    System.out.println("Total accounts: " + count);
  }

  @Transactional
  public void updateAccount() {
    var accountOpt = accountRepository.findById(662339);
    if (accountOpt.isPresent()) {
      Account account = accountOpt.get();
      account.setLastName("main");
//      entityManager.persist(account);
      try {
        accountSubService.updateOther();
      } catch (Exception e) {
        System.out.println("Error updating account: " + e.getMessage());
      }
      System.out.println("Account updated: " + account);
    } else {
      System.out.println("Account not found");
    }
  }

}
