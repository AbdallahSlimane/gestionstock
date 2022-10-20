package com.abdproject.gestionstock.services.Implementation;

import com.abdproject.gestionstock.dto.CategoryDto;
import com.abdproject.gestionstock.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void saveCategoryWithSuccess(){
        CategoryDto categoryTested = CategoryDto.builder()
                .code("cat test")
                .descritpion("descritption test")
                .identreprise(1)
                .build();

        CategoryDto savedCategory = categoryService.save(categoryTested);

        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId());
        assertEquals(categoryTested.getCode(), savedCategory.getCode());
        assertEquals(categoryTested.getDescritpion(), savedCategory.getDescritpion());
        assertEquals(categoryTested.getIdentreprise(), savedCategory.getIdentreprise());
    }

    @Test
    public void UpdateCategoryWithSuccess() {
        CategoryDto categoryTested = CategoryDto.builder()
                .code("cat test")
                .descritpion("descritption test")
                .identreprise(1)
                .build();

        CategoryDto savedCategory = categoryService.save(categoryTested);

        CategoryDto categoryToUpdate = savedCategory;
        categoryToUpdate.setCode("Cat update");

        savedCategory = categoryService.save(categoryToUpdate);

        assertNotNull(categoryToUpdate);
        assertNotNull(categoryToUpdate.getId());
        assertEquals(categoryToUpdate.getCode(), savedCategory.getCode());
        assertEquals(categoryToUpdate.getDescritpion(), savedCategory.getDescritpion());
        assertEquals(categoryToUpdate.getIdentreprise(), savedCategory.getIdentreprise());
    }
}