package mx.edu.utez.services_clothing_shop.controller.subcategory;

import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.RequestPutSubcategoryDTO;
import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.RequestSubcategoryById;
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
        Page<BeanSubcategory> subcategories = subcategoryService.getSubcategories(page);
        if (subcategories != null) {
            Page<ResponseSubcategoryDTO> responseDTOList = subcategories.map(ResponseSubcategoryDTO::toSubcategoryDTO);
            return new ResponseEntity<>(new CustomResponse<>(responseDTOList, "Subcategorias encontradas", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Subcategorias no encontradas", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/get-subcategory")
    public ResponseEntity<CustomResponse<ResponseSubcategoryDTO>> getSubcategory(@RequestBody RequestSubcategoryById subcategory) {
        BeanSubcategory retrievedSubcategory = subcategoryService.getSubcategory(subcategory.getIdSubcategory());
        if (retrievedSubcategory != null) {
            ResponseSubcategoryDTO responseDTO = ResponseSubcategoryDTO.toSubcategoryDTO(retrievedSubcategory);
            return new ResponseEntity<>(new CustomResponse<>(responseDTO, "Subcategoria encontrada", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Subcategoria no encontrada", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/post-subcategory")
    public ResponseEntity<CustomResponse<BeanSubcategory>> postSubcategory(@RequestBody RequestSubcategoryDTO subcategory) {
        BeanSubcategory newSubcategory = new BeanSubcategory();
        newSubcategory.setSubcategory(subcategory.getSubcategory());
        newSubcategory.setImage(subcategory.getImage());
        BeanCategory category = new BeanCategory();
        category.setIdCategory(subcategory.getIdCategory());
        newSubcategory.setCategory(category);
        newSubcategory.setStatus(subcategory.isStatus());
        newSubcategory = subcategoryService.postSubcategory(newSubcategory);
        if (newSubcategory != null) {
            return new ResponseEntity<>(new CustomResponse<>(newSubcategory, "Subcategoria guardada", false, 201), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Subcategoria no guardada", true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put-subcategory")
    public ResponseEntity<CustomResponse<BeanSubcategory>> putSubcategory(@RequestBody RequestPutSubcategoryDTO subcategory) {
        BeanSubcategory updatedSubcategory = new BeanSubcategory();
        updatedSubcategory.setIdSubcategory(subcategory.getIdSubcategory());
        updatedSubcategory.setSubcategory(subcategory.getSubcategory());
        updatedSubcategory.setImage(subcategory.getImage());
        BeanCategory category = new BeanCategory();
        category.setIdCategory(subcategory.getIdCategory());
        updatedSubcategory.setCategory(category);
        updatedSubcategory = subcategoryService.putSubcategory(updatedSubcategory);
        if (updatedSubcategory != null) {
            return new ResponseEntity<>(new CustomResponse<>(updatedSubcategory, "Subcategoria actualizada", false, 201), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Subcategoria no actualizada", true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put-status-subcategory")
    public ResponseEntity<CustomResponse<Boolean>> putStatusSubcategory(@RequestBody RequestSubcategoryById subcategory) {
        Boolean updatedStatus = subcategoryService.putStatusSubcategory(subcategory.getIdSubcategory());
        if (updatedStatus != null) {
            return new ResponseEntity<>(new CustomResponse<>(updatedStatus, "Estatus de subcategoria actualizado", false, 201), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Estatus de subcategoria no actualizado", true, 400), HttpStatus.BAD_REQUEST);
        }
    }


}
