package mx.edu.utez.services_clothing_shop.service.return_product_gallery;

import mx.edu.utez.services_clothing_shop.model.return_product_gallery.BeanReturnProductGallery;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.IReturnProductGallery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReturnProductGalleryService {
    private final IReturnProductGallery iReturnProductGallery;
    public ReturnProductGalleryService(IReturnProductGallery iReturnProductGallery){
        this.iReturnProductGallery = iReturnProductGallery;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanReturnProductGallery>> getReturnProductGalleries(){
        try {
            List<BeanReturnProductGallery> returnProductGalleries = iReturnProductGallery.findAll();
            return ResponseEntity.ok(returnProductGalleries);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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

}
