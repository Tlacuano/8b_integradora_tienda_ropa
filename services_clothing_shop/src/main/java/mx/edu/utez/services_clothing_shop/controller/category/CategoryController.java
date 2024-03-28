package mx.edu.utez.services_clothing_shop.controller.category;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.category.dto.RequestCategoryDTO;
import mx.edu.utez.services_clothing_shop.controller.category.dto.RequestPutCategoryDTO;
import mx.edu.utez.services_clothing_shop.controller.category.dto.RequestCategoryByIdDTO;
import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.service.category.CategoryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("venta-ropa/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get-categories")
    public ResponseEntity<Object> getCategories(Pageable page) {
        try {
            Page<BeanCategory> categories = categoryService.getCategories(page);
            return new ResponseEntity<>(new CustomResponse<>(categories, "Categorías encontradas", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener las categorías: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-category")
    public ResponseEntity<Object> getCategory(@Valid @RequestBody RequestCategoryByIdDTO category) {
        try {
            BeanCategory retrievedCategory = categoryService.getCategory(category.getIdCategory());
            return new ResponseEntity<>(new CustomResponse<>(retrievedCategory, "Categoría encontrada", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener la categoría: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post-category")
    public ResponseEntity<Object> postCategory(@Valid @RequestBody RequestCategoryDTO category) {
        try {
            BeanCategory newCategory = new BeanCategory();
            newCategory.setCategory(category.getCategory());
            newCategory.setImage(category.getImage());
            newCategory.setStatus(category.isStatus());
            newCategory = categoryService.postCategory(newCategory);
            return new ResponseEntity<>(new CustomResponse<>(newCategory, "Categoría guardada", false, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al guardar la categoría: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/put-category")
    public ResponseEntity<Object> putCategory(@Valid @RequestBody RequestPutCategoryDTO category) {
        try {
            BeanCategory updatedCategory = new BeanCategory();
            updatedCategory.setIdCategory(category.getIdCategory());
            updatedCategory.setCategory(category.getCategory());
            updatedCategory.setImage(category.getImage());
            updatedCategory = categoryService.putCategory(updatedCategory);
            return new ResponseEntity<>(new CustomResponse<>(updatedCategory, "Categoría actualizada", false, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al actualizar la categoría: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/put-status-category")
    public ResponseEntity<Object> putStatusCategory(@Valid @RequestBody RequestCategoryByIdDTO category) {
        try {
            categoryService.putStatusCategory(category.getIdCategory());
            return new ResponseEntity<>(new CustomResponse<>(true, "Estado de la categoría actualizado", false, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(false, "Error al actualizar el estado de la categoría: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
