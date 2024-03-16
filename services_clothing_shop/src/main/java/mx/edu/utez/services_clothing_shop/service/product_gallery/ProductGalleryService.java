package mx.edu.utez.services_clothing_shop.service.product_gallery;

import org.springframework.transaction.annotation.Transactional;
import mx.edu.utez.services_clothing_shop.model.image_product_status.BeanImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.image_product_status.IImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
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

    @Transactional(rollbackFor = Exception.class)
    public List<BeanProductGallery> postProductGallery(BeanProduct product, List<String> images) {
        List<BeanProductGallery> productGallery;
        BeanImageProductStatus imageProductStatus = IImageProductStatus.findByStatus("Predeterminada");
        if (images.size() > 1) {
            BeanImageProductStatus imageProductStatus2 = IImageProductStatus.findByStatus("Habilitada");
            IProductGallery.postProductGallery(product.getIdProduct().toString(), images.get(0), imageProductStatus.getIdStatus().toString());
            for (int i = 1; i < images.size(); i++) {
                IProductGallery.postProductGallery(product.getIdProduct().toString(), images.get(i), imageProductStatus2.getIdStatus().toString());
            }
        } else {
            IProductGallery.postProductGallery(product.getIdProduct().toString(), images.get(0), imageProductStatus.getIdStatus().toString());
        }
        productGallery = IProductGallery.findByProduct(product);
        return productGallery;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<BeanProductGallery> putProductGalley(List<BeanProductGallery> productGallery) {
        List<BeanProductGallery> productGalleryList = new ArrayList<>();
        for (BeanProductGallery gallery : productGallery) {
            BeanImageProductStatus imageProductStatus = IImageProductStatus.findByStatus(gallery.getStatus().getStatus());
            gallery.setStatus(imageProductStatus);
            productGalleryList.add(IProductGallery.save(gallery));
        }
        return productGalleryList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductGallery(BeanProduct product) {
        IProductGallery.deleteAllByProduct(product);
    }
}
