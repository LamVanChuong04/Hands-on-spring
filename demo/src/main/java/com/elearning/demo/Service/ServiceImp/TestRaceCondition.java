package com.elearning.demo.Service.ServiceImp;

import com.elearning.demo.Model.Products;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestRaceCondition {
    private final ProductServiceImp productService;

    public void test(Long productId) throws InterruptedException{
        // 2 thread
        Thread th1 = new Thread(()->{
            try{
                Products pr = productService.updateProduct( productId);
                System.out.println(Thread.currentThread().getName() +" updated product successfully");
                System.out.println(Thread.currentThread().getName() + " currently stock = " + pr.getProductStock());
            }catch(RuntimeException e){
               System.out.println(Thread.currentThread().getName()+ "updated fail "+e.getMessage());
            }
        });

        Thread th2 = new Thread(()->{
            try{
                Products pr2 = productService.updateProduct(productId);
                System.out.println(Thread.currentThread().getName() +" updated product successfully");
                System.out.println(Thread.currentThread().getName() + " currently stock = " + pr2.getProductStock());
            }catch(RuntimeException e){
                System.out.println(Thread.currentThread().getName()+ "updated fail "+e.getMessage());
            }
        });
        th1.start();
        th2.start();
        th1.join();
        th2.join();

    }

    public void atomicUpdate(Long productId) throws InterruptedException{
        // 2 thread
        Thread th1 = new Thread(()->{
            try{
                productService.automicUpdateProduct( productId);
                System.out.println(Thread.currentThread().getName() +" updated product successfully");
            }catch(RuntimeException e){
                System.out.println(Thread.currentThread().getName()+ " updated fail "+e.getMessage());
            }
        });

        Thread th2 = new Thread(()->{
            try{
                productService.automicUpdateProduct( productId);
                System.out.println(Thread.currentThread().getName() +" updated product successfully");
            }catch(RuntimeException e){
                System.out.println(Thread.currentThread().getName()+ " updated fail "+e.getMessage());
            }
        });
        Thread th3 = new Thread(()->{
            try{
                productService.automicUpdateProduct( productId);
                System.out.println(Thread.currentThread().getName() +" updated product successfully");
            }catch(RuntimeException e){
                System.out.println(Thread.currentThread().getName()+ " updated fail "+e.getMessage());
            }
        });
        th1.start();
        th2.start();
        th3.start();
        th1.join();
        th2.join();
        th3.join();

    }

}
