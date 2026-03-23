package com.elearning.demo.Service.ServiceImp;

import com.elearning.demo.Model.Products;
import com.elearning.demo.Repository.ProductRepository;
import com.elearning.demo.Service.IService.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements IProductService {
    private final ProductRepository productRepository;
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Products product) {
        Products pr=new Products();
        pr.setProductName(product.getProductName());
        pr.setProductPrice(product.getProductPrice());
        pr.setProductDescription(product.getProductDescription());
        pr.setProductStock(product.getProductStock());
        productRepository.save(pr);

    }

    @Override
    public List<Products> findAllProducts() {
        return productRepository.findAll();
    }
}
