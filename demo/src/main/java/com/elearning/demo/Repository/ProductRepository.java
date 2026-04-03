package com.elearning.demo.Repository;

import com.elearning.demo.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    @Query(value = "SELECT * FROM Products", nativeQuery = true)
    List<Products> findAllProducts();

    @Query(value = "select p from Products p where p.productName = :name")
    List<Products> findByProductName(@Param("name") String name);

    @Modifying
    @Query(value = "update Products p set p.productStock = p.productStock - 1 where p.productId = :id and p.productStock>0")
    int atomicUpdate(@Param("id") Long id);
}
