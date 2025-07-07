package com.example.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
@Audited(withModifiedFlag = true)
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
  @SequenceGenerator(name = "account_id_seq", sequenceName = "postgres_air.account_account_id_seq", allocationSize = 1)
  @Column(name = "account_id", nullable = false)
  private Integer accountId;

  @Column(name = "login", nullable = false)
  private String login;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "frequent_flyer_id")
  private Integer frequentFlyerId;

  @Column(name = "update_ts")
  private ZonedDateTime updateTs;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Phone> phones;
}
