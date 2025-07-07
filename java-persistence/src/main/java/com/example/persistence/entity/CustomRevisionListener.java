package com.example.persistence.entity;

import org.hibernate.envers.RevisionListener;

public class CustomRevisionListener implements RevisionListener {

  @Override
  public void newRevision(Object o) {
    CustomRevisionsEntity revision = (CustomRevisionsEntity) o;
    revision.setUsername("system"); // Set the username or any other custom logic
  }
}
