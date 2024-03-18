package mx.edu.utez.services_clothing_shop.service.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.*;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address.IAddress;
import mx.edu.utez.services_clothing_shop.model.address_status.BeanAddressStatus;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final IAddress iAddress;
    private ErrorDictionary errorDictionary;
    public AddressService(IAddress iAddress, ErrorDictionary errorDictionary){
        this.iAddress = iAddress;
        this.errorDictionary = errorDictionary;
    }

    @Transactional(readOnly = true)
    public List<ResponseAllAddressDTO> getAddresses() {
        List<Object[]> addressesData = iAddress.findEssentialAddressInfo();
        if (addressesData.isEmpty()) {
            throw new CustomException(errorDictionary.getErrorMessage("address.notfound"));
        }
        return addressesData.stream()
                .map(this::mapToResponseAllDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BeanAddress getAddress(UUID idAddress){
        Optional<BeanAddress> optionalBeanAddress = iAddress.findById(idAddress);
        //si el id presente no esta en la bd mandar error dictionary address.idAddress.notfound
        if(optionalBeanAddress.isEmpty()){
            throw new CustomException(errorDictionary.getErrorMessage("address.idAddress.notfound"));
        }
        return optionalBeanAddress.get();
    }

    @Transactional
    public BeanAddress postAddress(RequestPostAddressDTO payload){
        BeanAddress newAddress = new BeanAddress();
        newAddress.setAddress(payload.getAddress());
        newAddress.setReferencesAddress(payload.getReferencesAddress());
        newAddress.setPostalCode(payload.getPostalCode());
        newAddress.setState(payload.getState());
        newAddress.setStreet(payload.getStreet());
        newAddress.setNeighborhood(payload.getNeighborhood());
        BeanPerson person = new BeanPerson();
        person.setIdPerson(payload.getPersonId());
        newAddress.setPerson(person);
        BeanAddressStatus status = new BeanAddressStatus();
        status.setIdStatus(payload.getStatusId());
        newAddress.setStatus(status);

        return iAddress.saveAndFlush(newAddress);
    }

    @Transactional
    public BeanAddress putAddress(RequestPutAddressDTO payload){
        //validar que el idAddress exista
        Optional<BeanAddress> optionalBeanAddress = iAddress.findById(payload.getIdAddress());
        if(optionalBeanAddress.isEmpty()){
            throw new CustomException(errorDictionary.getErrorMessage("address.idAddress.notfound"));
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

    public ResponsePostAddressDTO mapToResponseDTO(BeanAddress savedAddress) {
        ResponsePostAddressDTO responseDTO = new ResponsePostAddressDTO();
        responseDTO.setIdAddress(savedAddress.getIdAddress());
        responseDTO.setAddress(savedAddress.getAddress());
        responseDTO.setReferencesAddress(savedAddress.getReferencesAddress());
        responseDTO.setPostalCode(savedAddress.getPostalCode());
        responseDTO.setState(savedAddress.getState());
        responseDTO.setStreet(savedAddress.getStreet());
        responseDTO.setNeighborhood(savedAddress.getNeighborhood());
        responseDTO.setPersonId(savedAddress.getPerson().getIdPerson());
        responseDTO.setStatusId(savedAddress.getStatus().getIdStatus());
        return responseDTO;
    }

    public ResponseAllAddressDTO mapToResponseAllDTO(Object[] row) {
        ResponseAllAddressDTO responseDTO = new ResponseAllAddressDTO();
        responseDTO.setAddress((String) row[0]);
        responseDTO.setReferencesAddress((String) row[1]);
        responseDTO.setPostalCode((String) row[2]);
        responseDTO.setState((String) row[3]);
        responseDTO.setStreet((String) row[4]);
        responseDTO.setNeighborhood((String) row[5]);
        responseDTO.setStatusID((UUID) row[6]);
        return responseDTO;
    }

}
