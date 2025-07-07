package com.example.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@Table(name = "air_revisions")
@RevisionEntity(CustomRevisionListener.class)
@Getter
@Setter
public class CustomRevisionsEntity extends DefaultRevisionEntity {
  private String username;
}
