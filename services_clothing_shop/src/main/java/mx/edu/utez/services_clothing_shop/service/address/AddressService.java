package mx.edu.utez.services_clothing_shop.service.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.ResponseAddressDTO;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address.IAddress;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseAddressDTO> getAddress(BeanAddress address){
        try {
            if(iAddress.existsByIdAddress(address.getIdAddress())){
                BeanAddress foundAddress = iAddress.findByIdAddress(address.getIdAddress());
                ResponseAddressDTO responseDTO = ResponseAddressDTO.toAddressDTO(foundAddress);
                return ResponseEntity.ok(responseDTO);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseAddressDTO get(ResponseAddressDTO addressDTO){
        //validar que se cumpla cl responseDTO
        //llenar un newaadd con la inf dada del dto
        BeanAddress beanAddress = new BeanAddress();
        beanAddress.setAddress(addressDTO.getAddress());
        beanAddress.setReferencesAddress(addressDTO.getReferencesAddress());
        //llenar por completo
        return addressDTO;

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanAddress> postAddress(BeanAddress address){
        try {
            if(address.getPerson() != null){
                System.out.println("id found: " +address.getIdAddress());
                System.out.println("name: "+address.getPerson().getName());
            } else {
                System.out.println( "no esta");
            }
            BeanAddress saveAddress = iAddress.save(address);
            return ResponseEntity.status(200).body(iAddress.saveAndFlush(saveAddress));
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
