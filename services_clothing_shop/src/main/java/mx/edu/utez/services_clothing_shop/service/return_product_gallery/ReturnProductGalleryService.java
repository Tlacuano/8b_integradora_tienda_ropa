package mx.edu.utez.services_clothing_shop.service.return_product_gallery;

import mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto.ResponseAllReturnProductGalleryDTO;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.BeanReturnProductGallery;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.IReturnProductGallery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReturnProductGalleryService {
    private final IReturnProductGallery iReturnProductGallery;
    private final ErrorDictionary errorDictionary;
    public ReturnProductGalleryService(IReturnProductGallery iReturnProductGallery, ErrorDictionary errorDictionary){
        this.iReturnProductGallery = iReturnProductGallery;
        this.errorDictionary = errorDictionary;
    }

    @Transactional(readOnly = true)
    public List<ResponseAllReturnProductGalleryDTO> getReturnProductGalleries(){
        List<Object[]> returnProductGalleriesData = iReturnProductGallery.findEssentialReturnProductGalleryInfo();
        if(returnProductGalleriesData.isEmpty()){
            throw new RuntimeException(errorDictionary.getErrorMessage("returnProductGallery.notfound"));
        }
        return returnProductGalleriesData.stream()
                .map(this::mapToResponseAllDTO)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanReturnProductGallery> getReturnProductGallery(BeanReturnProductGallery returnProductGallery) {
        try {
            if(iReturnProductGallery.existsByIdImage(returnProductGallery.getIdImage())) {
                return ResponseEntity.ok(iReturnProductGallery.findByIdImage(returnProductGallery.getIdImage()));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public  ResponseEntity<BeanReturnProductGallery> postReturnProductGallery(BeanReturnProductGallery returnProductGallery) {
        try {
            return ResponseEntity.status(200).body(iReturnProductGallery.save(returnProductGallery));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanReturnProductGallery> putReturnProductGallery(BeanReturnProductGallery returnProductGallery) {
        try {
            if(iReturnProductGallery.existsByIdImage(returnProductGallery.getIdImage())){
                return ResponseEntity.status(200).body(iReturnProductGallery.save(returnProductGallery));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseAllReturnProductGalleryDTO mapToResponseAllDTO(Object[] row){
        ResponseAllReturnProductGalleryDTO responseDTO = new ResponseAllReturnProductGalleryDTO();
        responseDTO.setImage((String) row[0]);
        responseDTO.setRequestReturnProductId((UUID) row[1]);
        return responseDTO;
    }

}
