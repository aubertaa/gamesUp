package com.gamesUP.gamesUP.controller.Game;

import com.gamesUP.gamesUP.DTO.Game.CategoryDTO;
import com.gamesUP.gamesUP.DTO.Game.CategoryResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.Game.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PreAuthorize("hasRole('administrateur')")
    @PostMapping
    public void addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.addCategory(categoryDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("Category already exists");
        }
    }

    @PreAuthorize("hasRole('administrateur')")
    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
    }

    @PreAuthorize("hasRole('administrateur')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
