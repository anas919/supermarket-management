package com.market.shop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class Product {
  @Id
  @GeneratedValue
  private long id;
  @Size(min=3,message = "Product must contains at least 3 characters")
  private String name;


  public Product() {
    
  }

  public Product(long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }
  
  public long getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public void setId(long id) {
    this.id = id;
  }
  public void setName(String name) {
    this.name = name;
  }

}
