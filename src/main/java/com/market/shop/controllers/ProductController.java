package com.market.shop.controllers;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.shop.models.Product;
import com.market.shop.repositories.ProductRepository;

import jakarta.validation.Valid;

@Controller
public class ProductController {

  private ProductRepository productRepository;

  public ProductController(ProductRepository productRepository){
    super();
    this.productRepository = productRepository;
  }
  
  @RequestMapping("products")
  public String allProducts(ModelMap model) {
    List<Product> products = productRepository.findAll();
    model.put("products",products);
    return "products";
  }

  @RequestMapping(value="create-product", method = RequestMethod.GET)
  public String createProduct(ModelMap model) {
    Product product = new Product(0,"product Name");
    model.put("product", product);
    return "create-product";
  }

  @RequestMapping(value="add-product", method = RequestMethod.POST)
  public String addProduct(@Valid Product product, BindingResult result) {
    if(result.hasErrors()){
      return "create-product";
    }
    productRepository.save(product);
    return "redirect:products";
  }

  @RequestMapping(value="delete-product", method = RequestMethod.GET)
  public String deleteProduct(@RequestParam int id) {
    productRepository.deleteById(id);
    return "redirect:products";
  }

  @RequestMapping(value="edit-product", method = RequestMethod.GET)
  public String editProduct(@RequestParam int id, ModelMap model) {
    Product product = productRepository.findById(id).get();
    model.put("product", product);
    return "edit-product";
  }

  @RequestMapping(value="update-product", method = RequestMethod.POST)
  public String updateProduct(@Valid Product product, BindingResult result) {
    if(result.hasErrors()){
      return "create-product";
    }
    productRepository.save(product);
    return "redirect:products";
  }

}
