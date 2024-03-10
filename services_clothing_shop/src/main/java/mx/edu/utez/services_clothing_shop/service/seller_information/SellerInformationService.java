package mx.edu.utez.services_clothing_shop.service.seller_information;

import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.model.seller_information.ISellerInformation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class SellerInformationService {
    private final ISellerInformation iSellerInformation;
    public SellerInformationService(ISellerInformation iSellerInformation){
        this.iSellerInformation = iSellerInformation;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanSellerInformation>> getSellersInformation(){
        try {
            List<BeanSellerInformation> sellersInformation = iSellerInformation.findAll();
            return ResponseEntity.ok(sellersInformation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanSellerInformation> getSellerInformation(BeanSellerInformation sellerInformation){
        try {
            if(iSellerInformation.existsByIdSellerInformation(sellerInformation.getIdSellerInformation())){
                return ResponseEntity.ok(iSellerInformation.findByIdSellerInformation(sellerInformation.getIdSellerInformation()));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSellerInformation> postSellerInformation(BeanSellerInformation sellerInformation){
        try {
            return ResponseEntity.status(200).body(iSellerInformation.save(sellerInformation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSellerInformation> putSellerInformation(BeanSellerInformation sellerInformation){
        try {
            if(iSellerInformation.existsByIdSellerInformation(sellerInformation.getIdSellerInformation())){
                return ResponseEntity.status(200).body(iSellerInformation.save(sellerInformation));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
