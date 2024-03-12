package mx.edu.utez.services_clothing_shop.service.product;

import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product.IProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.UUID;

@Service
public class ProductService {
    private final IProduct iProduct;

    public ProductService(IProduct iProduct) {
        this.iProduct = iProduct;
    }

    @Transactional(readOnly = true)
    public Page<BeanProduct> getProducts(Pageable page) {
        return iProduct.findAll(page);
    }

    @Transactional(readOnly = true)
    public Page<BeanProduct> getProductsByUser(String email, Pageable page) {
        return iProduct.findAllByUser_Email(email, page);
    }

    @Transactional(readOnly = true)
    public BeanProduct getProduct(UUID idProduct) {
        return iProduct.findByIdProduct(idProduct);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public BeanProduct postProduct(BeanProduct product) {
        return iProduct.save(product);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public BeanProduct putProduct(BeanProduct product) {
        return iProduct.save(product);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Boolean putStatusProduct(UUID idProduct) {
BeanProduct product = iProduct.findByIdProduct(idProduct);
        if (product != null) {
            product.setStatus(!product.isStatus());
            iProduct.save(product);
            return true;
        }
        return false;
    }
}
