package mx.edu.utez.services_clothing_shop.controller.subcategory;

import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.service.subcategory.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venta-ropa/api/subcategories")
@CrossOrigin(origins = "*")
public class SubcategoryController {
    private final SubCategoryService subcategoryService;

    public SubcategoryController(SubCategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/get-subcategories")
    public ResponseEntity<List<BeanSubcategory>> getSubcategories() {
        return subcategoryService.getSubcategories();
    }

    @PostMapping("/get-subcategory")
    public ResponseEntity<BeanSubcategory> getSubcategory(@RequestBody BeanSubcategory subcategory) {
        return subcategoryService.getSubcategory(subcategory);
    }

    @PostMapping("/post-subcategory")
    public ResponseEntity<BeanSubcategory> postSubcategory(@RequestBody BeanSubcategory subcategory) {
        return subcategoryService.postSubcategory(subcategory);
    }

    @PutMapping("/put-subcategory")
    public ResponseEntity<BeanSubcategory> putSubcategory(@RequestBody BeanSubcategory subcategory) {
        return subcategoryService.putSubcategory(subcategory);
    }

    @PutMapping("/put-status-subcategory")
    public ResponseEntity<Boolean> putStatusSubcategory(@RequestBody BeanSubcategory subcategory) {
        return subcategoryService.putStatusSubcategory(subcategory);
    }


}
