package mx.edu.utez.services_clothing_shop.service.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.ResponseAddressDTO;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address.IAddress;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final IAddress iAddress;
    public AddressService(IAddress iAddress){
        this.iAddress = iAddress;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ResponseAddressDTO>> getAddresses(){
        try {
            List<BeanAddress> addresses = iAddress.findAll();
            List<ResponseAddressDTO>  responseDTOs = addresses.stream()
                    .map(this::mapToAddressResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseAddressDTO mapToAddressResponseDTO(BeanAddress beanAddress) {
         ResponseAddressDTO dto = new ResponseAddressDTO();
         dto.setIdAddress(beanAddress.getIdAddress());
         dto.setAddress(beanAddress.getAddress());
         dto.setReferencesAddress(beanAddress.getReferencesAddress());
         dto.setPostalCode(beanAddress.getPostalCode());
         dto.setState(beanAddress.getState());
         dto.setStreet(beanAddress.getStreet());
         dto.setNeighborhood(beanAddress.getNeighborhood());
         UUID personId = beanAddress.getPerson() != null ? beanAddress.getPerson().getId_person() : null;
         dto.setUserID(personId);
         UUID statusId = beanAddress.getStatus() != null ? beanAddress.getStatus().getIdStatus() : null;
         dto.setStatusID(statusId);
         List<UUID> orderIds = beanAddress.getOrders().stream()
                 .map(BeanOrder::getIdOrder)
                 .collect(Collectors.toList());
         dto.setOrderIDs(orderIds);
         return dto;
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
