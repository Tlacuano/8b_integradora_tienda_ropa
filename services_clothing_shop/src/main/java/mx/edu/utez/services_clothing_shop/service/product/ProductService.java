package mx.edu.utez.services_clothing_shop.service.product;

import mx.edu.utez.services_clothing_shop.controller.product.dto.RequestProductBySearchQueryDTO;
import mx.edu.utez.services_clothing_shop.controller.product.dto.RequestProductDTO;
import mx.edu.utez.services_clothing_shop.model.image_product_status.BeanImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.image_product_status.IImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product.IProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.product_gallery.IProductGallery;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.BeanRequestSellProduct;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.IRequestsSellProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.subcategory.ISubCategory;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
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
    private final ISubCategory iSubCategory;
    private final IUser iUser;
    private final IRequestsSellProduct iRequestsSellProduct;
    private final IRequestStatus iRequestStatus;
    private final EmailService emailService;


    public ProductService(IProduct iProduct, IProductGallery iProductGallery, IImageProductStatus iImageProductStatus, ISubCategory iSubCategory, IUser iUser, IRequestsSellProduct iRequestsSellProduct, IRequestStatus iRequestStatus, EmailService emailService) {
        this.iProduct = iProduct;
        this.iProductGallery = iProductGallery;
        this.iImageProductStatus = iImageProductStatus;
        this.iSubCategory = iSubCategory;
        this.iUser = iUser;
        this.iRequestsSellProduct = iRequestsSellProduct;
        this.iRequestStatus = iRequestStatus;
        this.emailService = emailService;
    }

    @Transactional
    public Page<BeanProduct> getProducts(Pageable page) {
        return iProduct.findAll(page);
    }

    @Transactional
    public Page<BeanProduct> getProductsByCategory(String category, Pageable page) {
        return iProduct.findAllByCategory(category, page);
    }

    @Transactional
    public Page<BeanProduct> getProductsBySearchQuery(RequestProductBySearchQueryDTO payload, Pageable page) {
        if (!payload.getSubcategory().isEmpty()) {
            return iProduct.findAllByProductNameAndSubcategory(payload.getQuery(), payload.getCategory(), payload.getSubcategory(), page);
        } else {
            return iProduct.findAllByProductNameContainingIgnoreCase(payload.getQuery(), payload.getCategory(), page);
        }
    }

    @Transactional
    public Page<BeanProduct> getProductsBySubcategory(String category, String subcategory, Pageable page) {
        return iProduct.findAllBySubcategory(category, subcategory, page);
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
    public boolean postProduct(RequestProductDTO payload) {
        BeanUser user = iUser.findByEmail(payload.getEmail());
        if(user == null){
            throw new CustomException("user.email.exists");
        }

        BeanSubcategory subcategory = iSubCategory.findByIdSubcategory(payload.getSubcategory());
        if(subcategory == null){
            throw new CustomException("subcategory.notfound");
        }

        if(payload.getProductGallery().size() <= 2){
            throw new CustomException("product.productGallery.size.min");
        }
        if(payload.getProductGallery().size() > 5){
            throw new CustomException("product.productGallery.size.max");
        }

        //save product
        BeanProduct product = new BeanProduct();
        product.setProductName(payload.getProductName());
        product.setDescription(payload.getDescription());
        product.setPrice(payload.getPrice());
        product.setAmount(payload.getAmount());
        product.setSubcategory(subcategory);
        product.setUser(user);
        product.setStatus(true);

        product = iProduct.saveAndFlush(product);

        //save gallery
        BeanImageProductStatus defaultStatus = iImageProductStatus.findByStatus("Principal");
        BeanImageProductStatus enabledStatus = iImageProductStatus.findByStatus("Habilitada");


        for (String gallery : payload.getProductGallery()) {
            BeanProductGallery productGallery = new BeanProductGallery();
            productGallery.setProduct(product);
            productGallery.setImage(gallery);

            if(payload.getProductGallery().indexOf(gallery) == 0){
                productGallery.setStatus(defaultStatus);
            }else{
                productGallery.setStatus(enabledStatus);
            }

            iProductGallery.save(productGallery);
        }


        //save request to sell product
        BeanRequestStatus pendingStatus = iRequestStatus.findByStatus("Pendiente").get();

        BeanRequestSellProduct requestSellProduct = new BeanRequestSellProduct();
        requestSellProduct.setProduct(product);
        requestSellProduct.setStatus(pendingStatus);

        iRequestsSellProduct.save(requestSellProduct);

        emailService.sendEmail(user.getEmail(),
                "Solicitud registrada",
                "Solitud de venta de producto registrada exitosamente",
                "Tu producto ya esta en proceso de revisión, te notificaremos cuando este disponible en la tienda",
                "");


        return true;
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
