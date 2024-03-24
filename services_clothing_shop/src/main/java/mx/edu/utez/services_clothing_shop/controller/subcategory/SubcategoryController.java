package mx.edu.utez.services_clothing_shop.controller.subcategory;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.RequestPutSubcategoryDTO;
import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.RequestSubcategoryByIdDTO;
import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.RequestSubcategoryDTO;
import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.ResponseSubcategoryDTO;
import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.service.subcategory.SubcategoryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("venta-ropa/api/subcategories")
@CrossOrigin(origins = "*")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/get-subcategories")
    public ResponseEntity<CustomResponse<Page<ResponseSubcategoryDTO>>> getSubcategories(Pageable page) {
        try {
            Page<BeanSubcategory> subcategories = subcategoryService.getSubcategories(page);
            Page<ResponseSubcategoryDTO> responseDTOList = subcategories.map(ResponseSubcategoryDTO::toSubcategoryDTO);
            return new ResponseEntity<>(new CustomResponse<>(responseDTOList, "Subcategorias encontradas", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener las subcategorias: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-subcategory")
    public ResponseEntity<CustomResponse<ResponseSubcategoryDTO>> getSubcategory(@Valid @RequestBody RequestSubcategoryByIdDTO subcategory) {
        try {
            BeanSubcategory retrievedSubcategory = subcategoryService.getSubcategory(subcategory.getIdSubcategory());
            ResponseSubcategoryDTO responseDTO = ResponseSubcategoryDTO.toSubcategoryDTO(retrievedSubcategory);
            return new ResponseEntity<>(new CustomResponse<>(responseDTO, "Subcategoria encontrada", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener la subcategoria: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post-subcategory")
    public ResponseEntity<CustomResponse<BeanSubcategory>> postSubcategory(@Valid @RequestBody RequestSubcategoryDTO subcategory) {
        try {
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

    @PutMapping("/put-subcategory")
    public ResponseEntity<CustomResponse<BeanSubcategory>> putSubcategory(@Valid @RequestBody RequestPutSubcategoryDTO subcategory) {
        try {
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

    @PutMapping("/put-status-subcategory")
    public ResponseEntity<CustomResponse<Boolean>> putStatusSubcategory(@Valid @RequestBody RequestSubcategoryByIdDTO subcategory) {
        try {
            subcategoryService.putStatusSubcategory(subcategory.getIdSubcategory());
            return new ResponseEntity<>(new CustomResponse<>(true, "Estatus de subcategoria actualizado", false, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al actualizar el estatus de la subcategoria: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
