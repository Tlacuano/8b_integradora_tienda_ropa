package mx.edu.utez.services_clothing_shop.service.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.*;
import mx.edu.utez.services_clothing_shop.utils.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address.IAddress;
import mx.edu.utez.services_clothing_shop.model.address_status.BeanAddressStatus;
import mx.edu.utez.services_clothing_shop.model.address_status.IAddressStatus;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.person.IPerson;
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
    private final IAddressStatus iAddressStatus;
    private final IPerson iPerson;
    private ErrorDictionary errorDictionary;
    public AddressService(IAddress iAddress, IAddressStatus iAddressStatus, IPerson iPerson, ErrorDictionary errorDictionary){
        this.iAddress = iAddress;
        this.iAddressStatus = iAddressStatus;
        this.iPerson = iPerson;
        this.errorDictionary = errorDictionary;
    }

    @Transactional
    public List<ResponseAllAddressDTO> getAddresses() {
        List<Object[]> addressesData = iAddress.findEssentialAddressInfo();
        if (addressesData.isEmpty()) {
            throw new CustomException("addresses.notfound");
        }
        return addressesData.stream()
                .map(this::mapToResponseAllDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BeanAddress getAddress(UUID idAddress){
        Optional<BeanAddress> optionalBeanAddress = iAddress.findById(idAddress);
        if(optionalBeanAddress.isEmpty()){
            throw new CustomException("address.idAddress.notfound");
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
        BeanPerson person = iPerson.findById(payload.getPersonId()).orElse(null);
        newAddress.setPerson(person);
        BeanAddressStatus status = iAddressStatus.findById(payload.getStatusId()).orElse(null);
        newAddress.setStatus(status);

        return iAddress.saveAndFlush(newAddress);
    }

    @Transactional
    public BeanAddress putAddress(RequestPutAddressDTO payload){
        //validar que el idAddress exista
        Optional<BeanAddress> optionalBeanAddress = iAddress.findById(payload.getIdAddress());
        if(optionalBeanAddress.isEmpty()){
            throw new CustomException("address.idAddress.notfound");
        }
        //traer el objeto de la base de datos
        BeanAddress existingAddress = optionalBeanAddress.get();
        //actualizar los campos solo si se proporcionan en el payload
        if (payload.getAddress() != null) {
            existingAddress.setAddress(payload.getAddress());
        }
        if (payload.getReferencesAddress() != null) {
            existingAddress.setReferencesAddress(payload.getReferencesAddress());
        }
        if (payload.getPostalCode() != null) {
            existingAddress.setPostalCode(payload.getPostalCode());
        }
        if (payload.getState() != null) {
            existingAddress.setState(payload.getState());
        }
        if (payload.getStreet() != null) {
            existingAddress.setStreet(payload.getStreet());
        }
        if (payload.getNeighborhood() != null) {
            existingAddress.setNeighborhood(payload.getNeighborhood());
        }
        //guardar el objeto y regresar el objeto guardado
        return iAddress.saveAndFlush(existingAddress);
    }

    @Transactional
    public ResponseAllAddressDTO updateAddressStatus(RequestPutStatusAddressDTO payload) {
        UUID idAddress = payload.getIdAddress();
        UUID idStatus = payload.getStatusId();
        BeanAddress address = iAddress.findById(idAddress)
                .orElseThrow(() -> new CustomException("address.notfound"));

        BeanAddressStatus status = iAddressStatus.findById(idStatus)
                .orElseThrow(() -> new CustomException("status.notfound"));

        address.setStatus(status);
        iAddress.save(address);
        return mapToResponseAllDTO(new Object[]{address.getAddress(), address.getReferencesAddress(), address.getPostalCode(), address.getState(), address.getStreet(), address.getNeighborhood(), address.getStatus().getIdStatus()});
    }

    @Transactional
    public ResponseAllAddressDTO deleteAddress(RequestActionByIdDTO payload){
        UUID idAddress = payload.getIdAddress();
        BeanAddress address = iAddress.findById(idAddress)
                .orElseThrow(() -> new CustomException("address.idAddress.notfound"));
        iAddress.deleteById(idAddress);
        return mapToResponseAllDTO(new Object[]{address.getAddress(), address.getReferencesAddress(), address.getPostalCode(), address.getState(), address.getStreet(), address.getNeighborhood(), address.getStatus().getIdStatus()});
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

        BeanAddressStatus status = savedAddress.getStatus();
        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO();
        responseStatusDTO.setStatusID(status.getIdStatus());
        responseStatusDTO.setStatus(status.getStatus());
        responseDTO.setStatus(responseStatusDTO);
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
        UUID statusId = (UUID) row[6];
        BeanAddressStatus status = iAddressStatus.findById(statusId).get();
        ResponseStatusDTO statusDTO = new ResponseStatusDTO();
        statusDTO.setStatusID(status.getIdStatus());
        statusDTO.setStatus(status.getStatus());
        responseDTO.setStatus(statusDTO);
        return responseDTO;
    }

}
