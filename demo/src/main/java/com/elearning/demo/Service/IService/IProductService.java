package com.elearning.demo.Service.IService;

import com.elearning.demo.Model.Products;
import com.elearning.demo.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    public void addProduct(Products product);

    public List<Products> findAllProducts();

    public List<Products> findProductByName(String name);

    public Products updateProduct(Long productId);

    public void automicUpdateProduct(Long productId);
}
