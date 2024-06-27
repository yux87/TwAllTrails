package tw.team1.forum.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tw.team1.forum.model.Category;
import tw.team1.forum.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable int categoryId) {
        return categoryService.getCategoryById(categoryId);
    }
    @GetMapping("/{categoryId}/name")
    public String getCategoryNameById(@PathVariable int categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return category != null ? category.getCategoryname() : "Category not found";
    }
    @GetMapping("/description/{categoryid}")
    public ResponseEntity<?> getCategoryDescription(@PathVariable("categoryid") int categoryid) {
        try {
            Category category = categoryService.getCategoryById(categoryid);
            if (category != null) {
                return new ResponseEntity<>(category, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving category description", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{categoryname}")
    public Category getCategoryByName(@PathVariable String categoryname) {
        return categoryService.getByCategoryName(categoryname);
    }

	@PostMapping
    public Category saveCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable int categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}

