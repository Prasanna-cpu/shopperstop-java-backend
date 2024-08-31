package com.kumar.shopperstop.Service.Category;

import com.kumar.shopperstop.Exceptions.CategoryNotFoundException;
import com.kumar.shopperstop.Exceptions.ExistingCategoryException;
import com.kumar.shopperstop.Model.Category.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id) throws CategoryNotFoundException;
    Category getCategoryByName(String name) throws CategoryNotFoundException;
    Category addCategory(Category category) throws ExistingCategoryException;
    Category updateCategory(Category category, Long id) throws CategoryNotFoundException;

    void deleteCategory(Long id) throws CategoryNotFoundException;
    List<Category> getAllCategories();

}
