package mx.edu.utez.services_clothing_shop.controller.category;

import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.service.category.CategoryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CustomResponse<Page<BeanCategory>>> getCategories(Pageable page) {
        Page<BeanCategory> categories = categoryService.getCategories(page);
        if (categories != null) {
            return new ResponseEntity<>(new CustomResponse<>(categories, "Categorías encontradas", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Categorías no encontradas", true, 404), HttpStatus.NOT_FOUND);
        }
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
