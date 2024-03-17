package mx.edu.utez.services_clothing_shop.service.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.RequestPutAddressDTO;
import mx.edu.utez.services_clothing_shop.controller.address.dto.ResponseAddressDTO;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address.IAddress;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final IAddress iAddress;
    public AddressService(IAddress iAddress){
        this.iAddress = iAddress;
    }

    @Transactional
    public boolean existsByIdAddress(UUID idAddress){
        return iAddress.existsByIdAddress(idAddress);
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

    @Transactional
    public BeanAddress putAddress(RequestPutAddressDTO payload){
        //validar que el idAddress este presente en el payload
        UUID idAddress = payload.getIdAddress();
        if(idAddress == null){
            throw new CustomException("address.idAddress.notnull");
        }
        //validar que el idAddress exista
        Optional<BeanAddress> optionalBeanAddress = iAddress.findById(idAddress);
        if(optionalBeanAddress.isEmpty()){
            throw new CustomException("address.idAddress.notfound");
        }
        //traer el objeto de la base de datos
        BeanAddress existingAddress = optionalBeanAddress.get();
        //actualizar los campos
        existingAddress.setAddress(payload.getAddress());
        existingAddress.setReferencesAddress(payload.getReferencesAddress());
        existingAddress.setPostalCode(payload.getPostalCode());
        existingAddress.setState(payload.getState());
        existingAddress.setStreet(payload.getStreet());
        existingAddress.setNeighborhood(payload.getNeighborhood());
        //guardar el objeto y regresar el objeto guardado
        return iAddress.saveAndFlush(existingAddress);
    }

}
