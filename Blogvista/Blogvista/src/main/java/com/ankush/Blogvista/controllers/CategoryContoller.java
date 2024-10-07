package com.ankush.Blogvista.controllers;

import com.ankush.Blogvista.payloads.ApiResponse;
import com.ankush.Blogvista.payloads.CategoryDto;
import com.ankush.Blogvista.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryContoller {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto cat=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(cat, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        CategoryDto cat=this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(cat,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
        CategoryDto cat=this.categoryService.getCategory(categoryId);
        return new ResponseEntity<>(cat,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(@Valid @RequestBody CategoryDto categoryDto){

        return new ResponseEntity<>(this.categoryService.getCategories(),HttpStatus.OK);
    }



}
