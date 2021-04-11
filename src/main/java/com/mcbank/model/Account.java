package com.mcbank.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="accounts")
public class Account {

  @Id
  @GeneratedValue
  private Long id;
  private double amount;
  private Date creationDate;
  private AccountType type;
  @Column(name="user_id")
  private Long userId;

  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "accountId"
  )
  private List<Transaction> transactions;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public List<Transaction> getTransactions() {

    return transactions == null ? new ArrayList<>() : transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
