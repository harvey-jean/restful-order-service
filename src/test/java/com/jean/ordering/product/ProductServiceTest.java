package com.jean.ordering.product;

import com.jean.ordering.category.Category;
import com.jean.ordering.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;;
import static org.mockito.BDDMockito.then;

/**
 * Created by Harvey's on 7/7/2023.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductDTOMapper productDTOMapper;
    @Mock
    private CategoryService categoryService;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    private ProductService underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest = new ProductService(productRepository, productDTOMapper, categoryService);
    }
    @Test
    void itShouldAddNewProduct(){
        //Given a Product
        //First let's save 1 category for testing purpose
        Category category = new Category(1L,"Phone","This is Phone Category", null, null);
        categoryService.addCategory(category);
        //Request
        Product product = new Product(null, "Iphone X","New Generation",null, null, category);

        //When
        underTest.addProduct(product);

        //Then
        then(productRepository).should().save(productArgumentCaptor.capture());
        Product productArgumentCaptorValue = productArgumentCaptor.getValue();
        assertThat(productArgumentCaptorValue).isEqualTo(product);
    }

}