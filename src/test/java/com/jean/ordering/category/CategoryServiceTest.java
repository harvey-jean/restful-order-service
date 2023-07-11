package com.jean.ordering.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

/**
 * Created by Harvey's on 7/11/2023.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryDTOMapper categoryDTOMapper;

    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    private CategoryService underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest = new CategoryService(categoryRepository, categoryDTOMapper);
    }

    @Test
    void itShouldAddNewCategory() {
        // Given category
        Category category = new Category(1L,
                "Phone",
                "This is Phone Category", null, null);

        // When
        underTest.addCategory(category);

        // Then
        then(categoryRepository).should().save(categoryArgumentCaptor.capture());
        Category categoryArgumentCaptorValue = categoryArgumentCaptor.getValue();
        assertThat(categoryArgumentCaptorValue).isEqualTo(category);
    }


}