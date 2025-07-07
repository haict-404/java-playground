package com.example.persistence.controller;

import com.example.persistence.entity.Account;
import com.example.persistence.repository.AccountRepository;
import com.example.persistence.service.AccountService;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
  private final AccountRepository accountRepository;
  private final EntityManager entityManager;

  @PutMapping("/account")
  public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
    if (account.getAccountId() == null || !accountRepository.existsById(account.getAccountId())) {
      return ResponseEntity.notFound().build();
    }
    var acc = accountRepository.save(account);
    return ResponseEntity.ok(acc);
  }

  @PostMapping("/account")
  public ResponseEntity<Account> createAccount(@RequestBody Account account) {
    var acc = accountRepository.save(account);
    return ResponseEntity.ok(acc);
  }

  @GetMapping("/account/{accountId}/revision")
  public List<Account> getRevision(@PathVariable Integer accountId) {
    AuditReader reader = AuditReaderFactory.get(entityManager);
    Revisions<Integer, Account> revisions = accountRepository.findRevisions(accountId);
    return revisions.stream()
        .map(Revision::getEntity)
        .toList();
  }
}
