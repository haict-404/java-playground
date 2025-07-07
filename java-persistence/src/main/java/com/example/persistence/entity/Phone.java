package com.example.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone")
@Audited(withModifiedFlag = true)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Integer phoneId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    @Audited
    private Account account;

    @Column(name = "phone")
    private String phone;

    @Column(name = "phone_type")
    private String phoneType;

    @Column(name = "primary_phone")
    private Boolean primaryPhone;

    @Column(name = "update_ts")
    private ZonedDateTime updateTs;

}
