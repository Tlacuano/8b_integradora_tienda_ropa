package mx.edu.utez.services_clothing_shop.controller.subcategory;

import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.ResponseSubcategoryDTO;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.service.subcategory.SubcategoryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venta-ropa/api/subcategories")
@CrossOrigin(origins = "*")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/get-subcategories")
    public ResponseEntity<CustomResponse<List<ResponseSubcategoryDTO>>> getSubcategories() {
        List<BeanSubcategory> subcategories = subcategoryService.getSubcategories().getBody();
        if (subcategories != null) {
            List<ResponseSubcategoryDTO> subcategoryDTOS = subcategories.stream().map(ResponseSubcategoryDTO::toSubcategoryDTO).toList();
            return new ResponseEntity<>(new CustomResponse<>(subcategoryDTOS, "Subcategorias encontradas", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Subcategorias no encontradas", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/get-subcategory")
    public ResponseEntity<CustomResponse<ResponseSubcategoryDTO>> getSubcategory(@RequestBody BeanSubcategory subcategory) {
        BeanSubcategory retrievedSubcategory = subcategoryService.getSubcategory(subcategory.getIdSubcategory()).getBody();
        if (retrievedSubcategory != null) {
            ResponseSubcategoryDTO responseDTO = ResponseSubcategoryDTO.toSubcategoryDTO(retrievedSubcategory);
            return new ResponseEntity<>(new CustomResponse<>(responseDTO, "Subcategoria encontrada", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Subcategoria no encontrada", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/post-subcategory")
    public ResponseEntity<CustomResponse<BeanSubcategory>> postSubcategory(@RequestBody BeanSubcategory subcategory) {
        BeanSubcategory newSubcategory = subcategoryService.postSubcategory(subcategory).getBody();
        if (newSubcategory != null) {
            return new ResponseEntity<>(new CustomResponse<>(newSubcategory, "Subcategoria guardada", false, 201), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Subcategoria no guardada", true, 400), HttpStatus.BAD_REQUEST);
        }
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
