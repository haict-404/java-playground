package com.example.persistence.service;

import com.example.persistence.entity.Phone;
import com.example.persistence.repository.PhoneRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneService {
  private final PhoneRepository phoneRepository;

  @Transactional
  public List<Phone> getPhonesWithoutJoin() {
    return phoneRepository.findAllWithoutJoin();
  }

  @Transactional
  public List<Phone> getPhonesWithJoin() {
    return phoneRepository.findAllWithJoin();
  }

}
