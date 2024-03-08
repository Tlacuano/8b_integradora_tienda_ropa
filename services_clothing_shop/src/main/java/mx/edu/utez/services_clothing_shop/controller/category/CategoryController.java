package mx.edu.utez.services_clothing_shop.controller.category;

import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.service.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venta-ropa/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get-categories")
    public ResponseEntity<List<BeanCategory>> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/get-category")
    public ResponseEntity<BeanCategory> getCategory(@RequestBody BeanCategory category) {
        return categoryService.getCategory(category);
    }

    @PostMapping("/post-category")
    public ResponseEntity<BeanCategory> postCategory(@RequestBody BeanCategory category) {
        return categoryService.postCategory(category);
    }

    @PutMapping("/put-category")
    public ResponseEntity<BeanCategory> putCategory(@RequestBody BeanCategory category) {
        return categoryService.putCategory(category);
    }

    @PutMapping("/put-status-category")
    public ResponseEntity<Boolean> putStatusCategory(@RequestBody BeanCategory category) {
        return categoryService.putStatusCategory(category);
    }
}
