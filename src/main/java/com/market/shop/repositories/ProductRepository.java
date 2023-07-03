package com.market.shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.shop.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  public List<Product> findByName(String name);
}
