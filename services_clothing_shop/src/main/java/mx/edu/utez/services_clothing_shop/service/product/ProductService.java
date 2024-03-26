package mx.edu.utez.services_clothing_shop.service.product;

import mx.edu.utez.services_clothing_shop.model.image_product_status.BeanImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.image_product_status.IImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product.IProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.product_gallery.IProductGallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final IProduct iProduct;
    private final IProductGallery iProductGallery;
    private final IImageProductStatus iImageProductStatus;

    public ProductService(IProduct iProduct, IProductGallery iProductGallery, IImageProductStatus iImageProductStatus) {
        this.iProduct = iProduct;
        this.iProductGallery = iProductGallery;
        this.iImageProductStatus = iImageProductStatus;
    }

    @Transactional
    public Page<BeanProduct> getProducts(Pageable page) {
        return iProduct.findAll(page);
    }

    @Transactional
    public Page<BeanProduct> getProductsByUserEmail(String email, Pageable page) {
        return iProduct.findAllByUser_Email(email, page);
    }

    @Transactional
    public BeanProduct getProduct(UUID idProduct) {
        return iProduct.findByIdProduct(idProduct);
    }

    @Transactional
    public BeanProduct postProduct(BeanProduct product, List<String> images) {
        BeanProduct productSaved = iProduct.saveAndFlush(product);
        saveProductGallery(productSaved, images);
        return productSaved;
    }

    @Transactional
    public BeanProduct putProduct(BeanProduct product, List<BeanProductGallery> productGallery) {
        BeanProduct updatedProduct = iProduct.saveAndFlush(product);
        updateProductGallery(productGallery);
        return updatedProduct;
    }

    @Transactional
    public void putStatusProduct(UUID idProduct) {
        BeanProduct product = getProduct(idProduct);
        product.setStatus(!product.isStatus());
        iProduct.saveAndFlush(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(UUID idProduct) {
        iProduct.deleteByIdProduct(idProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductGallery(BeanProduct product) {
        iProductGallery.deleteAllByProduct(product);
    }

    private void saveProductGallery(BeanProduct product, List<String> images) {
        BeanImageProductStatus defaultStatus = iImageProductStatus.findByStatus("Predeterminada");
        BeanImageProductStatus enabledStatus = iImageProductStatus.findByStatus("Habilitada");
        String defaultStatusId = defaultStatus.getIdStatus().toString();
        String enabledStatusId = enabledStatus.getIdStatus().toString();

        for (int i = 0; i < images.size(); i++) {
            String image = images.get(i);
            String statusId = (i == 0) ? defaultStatusId : enabledStatusId;
            iProductGallery.postProductGallery(product.getIdProduct().toString(), image, statusId);
        }
    }

    private void updateProductGallery(List<BeanProductGallery> productGallery) {
        for (BeanProductGallery gallery : productGallery) {
            BeanImageProductStatus imageProductStatus = iImageProductStatus.findByStatus(gallery.getStatus().getStatus());
            gallery.setStatus(imageProductStatus);
            iProductGallery.saveAndFlush(gallery);
        }
    }
}
