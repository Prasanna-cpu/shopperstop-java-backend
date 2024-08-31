package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.Exceptions.CategoryNotFoundException;
import com.kumar.shopperstop.Exceptions.ExistingCategoryException;
import com.kumar.shopperstop.Model.Category.Category;
import com.kumar.shopperstop.Response.ApiResponse;
import com.kumar.shopperstop.Service.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> getAllCategories() {

        try{
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Categories found",categories));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error in getting categories",e.getMessage()));
        }
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) throws ExistingCategoryException {
        Category category=categoryService.addCategory(name);

        try{
            return ResponseEntity.ok(new ApiResponse("Category added",category));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error in adding categories",e.getMessage()));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) throws CategoryNotFoundException {
        Category category=categoryService.getCategoryById(id);
        try{
            return ResponseEntity.ok(new ApiResponse("Category found",category));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error in getting categories",e.getMessage()));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) throws CategoryNotFoundException {
        Category category=categoryService.getCategoryByName(name);
        try{
            return ResponseEntity.ok(new ApiResponse("Category found",category));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error in getting categories",e.getMessage()));
        }
    }

    @DeleteMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.deleteCategory(id);
        try{
            return ResponseEntity.ok(new ApiResponse("Category deleted",null));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error in deleting categories",e.getMessage()));
        }
    }










}
