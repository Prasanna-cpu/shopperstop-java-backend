package com.kumar.shopperstop.Service.Category;


import com.kumar.shopperstop.Exceptions.CategoryNotFoundException;
import com.kumar.shopperstop.Exceptions.ExistingCategoryException;
import com.kumar.shopperstop.Model.Category.Category;
import com.kumar.shopperstop.Repository.Category.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService{

    private final CategoryRepository categoryRepository;

    /**
     * @param id
     * @return
     */
    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        Category category=categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException("Given category not found"));
        return category;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Category getCategoryByName(String name) throws CategoryNotFoundException {
        Category category=categoryRepository.findByName(name).orElseThrow(()->new CategoryNotFoundException("Given category not found"));
        return category;
    }

    /**
     * @param category
     * @return
     */
    @Override
    public Category addCategory(Category category) throws ExistingCategoryException {
        return Optional.of(category)
                .filter(c->!categoryRepository.existsByName(c.getName()))
                .orElseThrow(()->new ExistingCategoryException("Given category already exists"));
    }

    /**
     * @param category
     * @return
     */
    @Override
    public Category updateCategory(Category category, Long id) throws CategoryNotFoundException {
//         return Optional.ofNullable(getCategoryById(id)).map(oldCategory->{
//            oldCategory.setName(category.getName());
//            return categoryRepository.save(oldCategory);
//         }).orElseThrow(()->{
//             new CategoryNotFoundException("Given category not found");
//             return null;
//         });
           Category existingCategory=getCategoryById(id);
           if(existingCategory==null){
               throw new CategoryNotFoundException("Given category not found");
           }
           existingCategory.setName(category.getName());
           return categoryRepository.save(existingCategory);

    }

    /**
     * @param id
     */
    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,()->{
            new CategoryNotFoundException("Given category not found");
        });
    }

    /**
     * @return
     */
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories;
    }
}
