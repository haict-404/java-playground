package com.example.persistence.repository;

import com.example.persistence.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, RevisionRepository<Account, Integer, Integer> {
    // Additional query methods can be defined here if needed
}
