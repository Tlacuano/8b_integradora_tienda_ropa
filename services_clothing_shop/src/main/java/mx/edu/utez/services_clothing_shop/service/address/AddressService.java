package mx.edu.utez.services_clothing_shop.service.address;

import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address.IAddress;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class AddressService {
    private final IAddress iAddress;
    public AddressService(IAddress iAddress){
        this.iAddress = iAddress;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanAddress>> getAddresses(){
        try {
            List<BeanAddress> addresses = iAddress.findAll();
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanAddress> getAddress(BeanAddress address){
        try {
            if(iAddress.existsByIdAddress(address.getIdAddress())){
                return ResponseEntity.ok(iAddress.findByIdAddress(address.getIdAddress()));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanAddress> postAddress(BeanAddress address){
        try {
            return ResponseEntity.status(200).body(iAddress.save(address));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanAddress> putAddress(BeanAddress address){
        try {
            if(iAddress.existsByIdAddress(address.getIdAddress())){
                return ResponseEntity.status(200).body(iAddress.save(address));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
