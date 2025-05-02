package com.example.persistence.repository;

import com.example.persistence.entity.Phone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

  @Query(value = "SELECT p FROM Phone p")
  List<Phone> findAllWithoutJoin();

  @Query(value = "SELECT p FROM Phone p JOIN FETCH p.account")
  List<Phone> findAllWithJoin();
}
