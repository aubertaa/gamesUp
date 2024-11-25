package com.gamesUP.gamesUP.service.Game;

import com.gamesUP.gamesUP.DTO.Game.CategoryDTO;
import com.gamesUP.gamesUP.DTO.Game.CategoryResponseDTO;
import com.gamesUP.gamesUP.Exceptions.RelationConstraintException;
import com.gamesUP.gamesUP.model.Game.Category;
import com.gamesUP.gamesUP.repository.Game.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize some Category objects
        category1 = new Category();
        category1.setId(1L);
        category1.setType("Category One");

        category2 = new Category();
        category2.setId(2L);
        category2.setType("Category Two");
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Act
        List<CategoryResponseDTO> result = categoryService.getAllCategories();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Category One", result.get(0).getType());
        assertEquals("Category Two", result.get(1).getType());
    }

    @Test
    public void testGetCategoryByIdFound() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));

        // Act
        CategoryResponseDTO result = categoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(category1.getType(), result.getType());
    }

    @Test
    public void testGetCategoryByIdNotFound() {
        // Arrange
        Long categoryId = 999L; // Non-existent ID
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act
        CategoryResponseDTO result = categoryService.getCategoryById(categoryId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddCategory() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setType("New Category");

        // Act
        categoryService.addCategory(categoryDTO);

        // Assert
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testUpdateCategoryFound() {
        // Arrange
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setType("Updated Category");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));

        // Act
        categoryService.updateCategory(categoryId, categoryDTO);

        // Assert
        verify(categoryRepository, times(1)).save(category1);
        assertEquals("Updated Category", category1.getType());
    }

    @Test
    public void testUpdateCategoryNotFound() {
        // Arrange
        Long categoryId = 999L; // Non-existent ID
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setType("Updated Category");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act
        categoryService.updateCategory(categoryId, categoryDTO);

        // Assert
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));

        // Act
        categoryService.deleteCategory(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testDeleteCategoryWithRelationConstraint() {
        // Arrange
        Long categoryId = 1L;
        doThrow(new DataIntegrityViolationException("")).when(categoryRepository).deleteById(categoryId);

        // Act and Assert
        Exception exception = assertThrows(RelationConstraintException.class, () -> categoryService.deleteCategory(categoryId));

        assertEquals("Cannot delete category, related entities exist.", exception.getMessage());
    }
}
