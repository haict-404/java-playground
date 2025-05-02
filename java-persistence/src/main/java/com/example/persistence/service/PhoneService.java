package com.example.persistence.service;

import com.example.persistence.entity.Phone;
import com.example.persistence.repository.PhoneRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneService {
  private final PhoneRepository phoneRepository;

  @Transactional(readOnly = true)
  public List<Phone> getPhones() {
    return phoneRepository.findByCondition(true);
  }
}
