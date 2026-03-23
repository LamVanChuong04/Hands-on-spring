package com.elearning.demo.Controller;

import com.elearning.demo.Model.Products;
import com.elearning.demo.Service.ServiceImp.ProductServiceImp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ProductController {
    private final ProductServiceImp productServiceImp;
    public ProductController(ProductServiceImp productServiceImp) {
        this.productServiceImp = productServiceImp;
    }

    @PostMapping("/products")
    public String createProduct(@RequestBody Products product) {
        productServiceImp.addProduct(product);
        return "Added product successfully";
    }

    @GetMapping("/products")
    public List<Products> getAllProducts() {
        return productServiceImp.findAllProducts();
    }
}
