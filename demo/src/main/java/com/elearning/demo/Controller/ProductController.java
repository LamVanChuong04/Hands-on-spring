package com.elearning.demo.Controller;

import com.elearning.demo.Model.Products;
import com.elearning.demo.Service.ServiceImp.ProductServiceImp;
import com.elearning.demo.Service.ServiceImp.TestRaceCondition;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ProductController {
    private final ProductServiceImp productServiceImp;
    private final TestRaceCondition testRaceCondition;
    public ProductController(ProductServiceImp productServiceImp,  TestRaceCondition testRaceCondition) {
        this.productServiceImp = productServiceImp;
        this.testRaceCondition = testRaceCondition;
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

    @GetMapping("/product")
    public List<Products> getProductsByName(@RequestParam String name) {
        return productServiceImp.findProductByName(name);
    }

    @PutMapping("/products/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId) throws Exception {
        testRaceCondition.test(productId);
        return "Updated product successfully";
    }

    @PutMapping("/atomic-update/{productId}")
    public String atomicUpdate(@PathVariable("productId") Long productId) throws Exception {
        testRaceCondition.atomicUpdate(productId);
        return "-----------------------------";
    }
}





