package com.example.persistence.repository;

import com.example.persistence.entity.Phone;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
  @Query(value = "SELECT p FROM Phone p JOIN FETCH p.account WHERE (:condition IS NULL OR p.phoneId > 5000)")
  List<Phone> findByCondition(boolean condition);
}
