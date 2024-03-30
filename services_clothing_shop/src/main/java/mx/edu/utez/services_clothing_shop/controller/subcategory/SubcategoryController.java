package mx.edu.utez.services_clothing_shop.controller.subcategory;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.*;
import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.subcategory.ISubCategory;
import mx.edu.utez.services_clothing_shop.service.subcategory.SubcategoryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("venta-ropa/api/subcategories")
@CrossOrigin(origins = "*")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/get-subcategories")
    public ResponseEntity<Object> getSubcategories(Pageable page) {
        try {
            Page<BeanSubcategory> subcategories = subcategoryService.getSubcategories(page);
            Page<ResponseSubcategoryDTO> responseDTOList = subcategories.map(ResponseSubcategoryDTO::toSubcategoryDTO);
            return new ResponseEntity<>(new CustomResponse<>(responseDTOList, "Subcategorias encontradas", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener las subcategorias: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-by-category")
    public ResponseEntity<CustomResponse<List<ISubCategory.SubcategoryNameProjection>>> getSubcategoriesByCategory(@Valid @RequestBody RequestSubcategoryByCategoryDTO payload) {
        try {
            List<ISubCategory.SubcategoryNameProjection> subcategories = subcategoryService.getSubcategoriesByCategory(payload.getCategory());
            return new ResponseEntity<>(new CustomResponse<>(subcategories, "Subcategorias encontradas", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener las subcategorias por categoria: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-subcategory")
    public ResponseEntity<Object> getSubcategory(@Valid @RequestBody RequestSubcategoryByIdDTO subcategory, BindingResult result) {
        try {
            if (result.hasFieldErrors()) {
                return validation(result);
            }
            BeanSubcategory retrievedSubcategory = subcategoryService.getSubcategory(subcategory.getIdSubcategory());
            ResponseSubcategoryDTO responseDTO = ResponseSubcategoryDTO.toSubcategoryDTO(retrievedSubcategory);
            return new ResponseEntity<>(new CustomResponse<>(responseDTO, "Subcategoria encontrada", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener la subcategoria: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post-subcategory")
    public ResponseEntity<Object> postSubcategory(@Valid @RequestBody RequestSubcategoryDTO subcategory, BindingResult result) {
        try {
            if (result.hasFieldErrors()) {
                return validation(result);
            }
            BeanSubcategory newSubcategory = new BeanSubcategory();
            newSubcategory.setSubcategory(subcategory.getSubcategory());
            newSubcategory.setImage(subcategory.getImage());
            BeanCategory category = new BeanCategory();
            category.setIdCategory(subcategory.getIdCategory());
            newSubcategory.setCategory(category);
            newSubcategory.setStatus(subcategory.isStatus());
            newSubcategory = subcategoryService.postSubcategory(newSubcategory);
            return new ResponseEntity<>(new CustomResponse<>(newSubcategory, "Subcategoria guardada", false, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al guardar la subcategoria: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/put-subcategory")
    public ResponseEntity<Object> putSubcategory(@Valid @RequestBody RequestPutSubcategoryDTO subcategory, BindingResult result) {
        try {
            if (result.hasFieldErrors()) {
                return validation(result);
            }
            BeanSubcategory updatedSubcategory = new BeanSubcategory();
            updatedSubcategory.setIdSubcategory(subcategory.getIdSubcategory());
            updatedSubcategory.setSubcategory(subcategory.getSubcategory());
            updatedSubcategory.setImage(subcategory.getImage());
            BeanCategory category = new BeanCategory();
            category.setIdCategory(subcategory.getIdCategory());
            updatedSubcategory.setCategory(category);
            updatedSubcategory = subcategoryService.putSubcategory(updatedSubcategory);
            return new ResponseEntity<>(new CustomResponse<>(updatedSubcategory, "Subcategoria actualizada", false, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al actualizar la subcategoria: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/put-status-subcategory")
    public ResponseEntity<Object> putStatusSubcategory(@Valid @RequestBody RequestSubcategoryByIdDTO subcategory, BindingResult result) {
        try {
            if (result.hasFieldErrors()) {
                return validation(result);
            }
            subcategoryService.putStatusSubcategory(subcategory.getIdSubcategory());
            return new ResponseEntity<>(new CustomResponse<>(true, "Estatus de subcategoria actualizado", false, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al actualizar el estatus de la subcategoria: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Object> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return new ResponseEntity<>(new CustomResponse<>(errors, "El objeto no cumple con los requisitos", true, 400), HttpStatus.BAD_REQUEST);
    }
}
