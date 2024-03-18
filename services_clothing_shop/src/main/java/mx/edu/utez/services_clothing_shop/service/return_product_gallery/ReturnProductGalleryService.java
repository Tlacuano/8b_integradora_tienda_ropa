package mx.edu.utez.services_clothing_shop.service.return_product_gallery;

import mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto.RequestPostReturnProductGalleryDTO;
import mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto.RequestPutImageReturnProductGalleryDTO;
import mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto.ResponseAllReturnProductGalleryDTO;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.BeanReturnProductGallery;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.IReturnProductGallery;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReturnProductGalleryService {
    private final IReturnProductGallery iReturnProductGallery;
    private final IRequestsReturnProduct iRequestsReturnProduct;
    private final ErrorDictionary errorDictionary;
    public ReturnProductGalleryService(IReturnProductGallery iReturnProductGallery, IRequestsReturnProduct iRequestsReturnProduct, ErrorDictionary errorDictionary){
        this.iReturnProductGallery = iReturnProductGallery;
        this.errorDictionary = errorDictionary;
        this.iRequestsReturnProduct = iRequestsReturnProduct;
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
    public BeanReturnProductGallery getReturnProductGallery(UUID idReturnProductGallery) {
        Optional<BeanReturnProductGallery> optionalBeanReturnProductGallery = iReturnProductGallery.findById(idReturnProductGallery);
        if(optionalBeanReturnProductGallery.isEmpty()){
            throw new RuntimeException(errorDictionary.getErrorMessage("returnProductGallery.idReturnProductGallery.notfound"));
        }
        return optionalBeanReturnProductGallery.get();
    }

    @Transactional
    public BeanReturnProductGallery postReturnProductGallery(RequestPostReturnProductGalleryDTO payload){
        UUID requestReturnProductId = payload.getRequestReturnProductId();
        BeanRequestReturnProduct requestReturnProduct = iRequestsReturnProduct.findById(requestReturnProductId)
                .orElseThrow(() -> new CustomException(errorDictionary.getErrorMessage("returnProductGallery.idRequestReturnProduct.notfound")));

        BeanReturnProductGallery newReturnProduct = new BeanReturnProductGallery();
        newReturnProduct.setImage(payload.getImage());
        BeanRequestReturnProduct newRequestReturnProduct = new BeanRequestReturnProduct();
        newRequestReturnProduct.setIdRequestReturnProduct(payload.getRequestReturnProductId());
        newReturnProduct.setReturnProduct(requestReturnProduct);
        return iReturnProductGallery.saveAndFlush(newReturnProduct);
    }

    @Transactional
    public BeanReturnProductGallery putReturnProductGallery(RequestPutImageReturnProductGalleryDTO payload) {
        Optional<BeanReturnProductGallery> optionalBeanReturnProductGallery = iReturnProductGallery.findById(payload.getIdImage());
        if(optionalBeanReturnProductGallery.isEmpty()){
            throw new CustomException(errorDictionary.getErrorMessage("returnProductGallery.idImage.notfound"));
        }
        BeanReturnProductGallery existingReturnProductGallery = optionalBeanReturnProductGallery.get();
        existingReturnProductGallery.setImage(payload.getImage());
        return iReturnProductGallery.saveAndFlush(existingReturnProductGallery);
    }

    public ResponseAllReturnProductGalleryDTO mapToResponseAllDTO(Object[] row){
        ResponseAllReturnProductGalleryDTO responseDTO = new ResponseAllReturnProductGalleryDTO();
        responseDTO.setImage((String) row[0]);
        responseDTO.setRequestReturnProductId((UUID) row[1]);
        return responseDTO;
    }

}
