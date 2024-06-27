package tw.team1.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.team1.forum.model.Category;
import tw.team1.forum.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // 可以在這裡添加與板塊相關的業務邏輯

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }


    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
    public Category getByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryname(categoryName);
    }
    
}

