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
  private String description;
  private String barcode;
  private long quantity;
  private double price;


  public Product() {
    
  }

  public Product(long id, String name,String description,String barcode,long quantity,double price) {
    super();
    this.id = id;
    this.name = name;
    this.description = description;
    this.barcode = barcode;
    this.quantity = quantity;
    this.price = price;
  }
  
  public long getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public String getDescription() {
    return description;
  }
  public String getBarcode() {
    return barcode;
  }
  public double getPrice() {
    return price;
  }
  public long getQuantity() {
    return quantity;
  }
  public void setId(long id) {
    this.id = id;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public void setPrice(double price) {
    this.price = price;
  }
  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }
  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

}
