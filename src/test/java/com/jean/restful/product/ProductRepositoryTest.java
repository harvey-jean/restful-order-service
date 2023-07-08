package com.jean.restful.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Harvey's on 7/7/2023.
 */
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepositoryUnderTest;

    @Test
    void testingFindProductsByNameSuccessfulCaseMethod(){

        //Given
       // Long id = 1L;
        String name = "Samsung Galaxy G20";
        String description = "Smartphone 10th generation";
        LocalDate dateManufacture = LocalDate.now();
        Product product = new Product(null,name,description, dateManufacture, null, null);

        //When
        productRepositoryUnderTest.save(product);

        //Then
        List<Product> products = productRepositoryUnderTest.findByName(name);
        assertFalse(products.isEmpty());
        assertNotNull(products.get(0));

    }

    @Test
    void testingFindProductsByNameFailedCaseMethod(){

        //Given
        // Long id = 1L;
        String name = "Samsung Galaxy G20";
        String description = "Smartphone 10th generation";
        LocalDate dateManufacture = LocalDate.now();
        Product product = new Product(null,name,description, dateManufacture, null, null);

        //When
        productRepositoryUnderTest.save(product);

        //Then
        List<Product> products = productRepositoryUnderTest.findByName("Not exist");
        assertTrue(products.isEmpty());
        //assertNull(!products.isEmpty() ? products.get(0) : products);

    }

}