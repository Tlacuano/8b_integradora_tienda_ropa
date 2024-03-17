package mx.edu.utez.services_clothing_shop.service.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.ResponseAddressDTO;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address.IAddress;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final IAddress iAddress;
    public AddressService(IAddress iAddress){
        this.iAddress = iAddress;
    }
    @Transactional(readOnly = true)
    public List<ResponseAddressDTO> getAddresses(){
            List<BeanAddress> addresses = iAddress.findAll();
            List<ResponseAddressDTO> responseDTOs = addresses.stream()
                    .map(ResponseAddressDTO::toAddressDTO)
                    .collect(Collectors.toList());
            return responseDTOs;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAddress(BeanAddress address){
            if(iAddress.existsByIdAddress(address.getIdAddress())){
                BeanAddress foundAddress = iAddress.findByIdAddress(address.getIdAddress());
                ResponseAddressDTO responseDTO = ResponseAddressDTO.toAddressDTO(foundAddress);
                return ResponseEntity.ok(responseDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public BeanAddress postAddress(BeanAddress address){
        return iAddress.saveAndFlush(address);
    }
    /*@Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanAddress> postAddress(BeanAddress address){
        BeanAddress saveAddress = iAddress.saveAndFlush(address);
        return ResponseEntity.ok(saveAddress);
    }*/

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
