package com.elearning.demo.Service.ServiceImp;

import com.elearning.demo.Model.Products;
import com.elearning.demo.Repository.ProductRepository;
import com.elearning.demo.Service.IService.IProductService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
    @Cacheable(value = "products", key = "'all'")
    @Override
    public List<Products> findAllProducts() {
        log.info("Getting all products");
        return productRepository.findAllProducts();
    }
    @Cacheable(value = "products", key = "#name")
    @Override
    public List<Products> findProductByName(String name) {
        return productRepository.findByProductName(name);
    }
    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    @Override
    public Products updateProduct(Long productId) {
        System.out.println(Thread.currentThread().getName()+ " is reading data");

        Products pr = productRepository.findById(productId).orElseThrow(
                ()-> new RuntimeException("product not found"));

        System.out.println(Thread.currentThread().getName()+ " stock = " + pr.getProductStock());
        pr.setProductStock(pr.getProductStock() - 1);

        return productRepository.save(pr);


    }
    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    @Override
    public void automicUpdateProduct(Long productId) {
        int updated = productRepository.atomicUpdate(productId);
        if (updated==0){
            throw new RuntimeException(" Updated failed");
        }

    }
}
