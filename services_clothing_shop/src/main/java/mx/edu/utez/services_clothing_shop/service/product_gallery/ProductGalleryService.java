package mx.edu.utez.services_clothing_shop.service.product_gallery;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.image_product_status.BeanImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.image_product_status.IImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.product_gallery.IProductGallery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductGalleryService {
    private final IProductGallery IProductGallery;
    private final IImageProductStatus IImageProductStatus;

    public ProductGalleryService(IProductGallery IProductGallery, IImageProductStatus IImageProductStatus) {
        this.IProductGallery = IProductGallery;
        this.IImageProductStatus = IImageProductStatus;
    }
    @Transactional(rollbackOn = Exception.class)
    public List<BeanProductGallery> postProductGallery(String idProduct, List<String> images) {
        List<BeanProductGallery> productGallery = new ArrayList<>();
        BeanImageProductStatus imageProductStatus = IImageProductStatus.findByStatusContainingIgnoreCase("Predeterminada");
        if (images.size() > 1) {
            BeanImageProductStatus imageProductStatus2 = IImageProductStatus.findByStatusContainingIgnoreCase("Habilitada");
            BeanProductGallery newImage = IProductGallery.postProductGallery(idProduct, imageProductStatus2.getIdStatus().toString(), images.get(0));
            productGallery.add(newImage);
            for (int i = 1; i < images.size(); i++) {
                BeanProductGallery newImage2 = IProductGallery.postProductGallery(idProduct, imageProductStatus.getIdStatus().toString(), images.get(i));
                productGallery.add(newImage2);
            }

        } else {
            BeanProductGallery newImage = IProductGallery.postProductGallery(idProduct, imageProductStatus.getIdStatus().toString(), images.get(0));
            productGallery.add(newImage);
        }
        return productGallery;
    }
}
