package mx.edu.utez.services_clothing_shop.service.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.*;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address.IAddress;
import mx.edu.utez.services_clothing_shop.model.address_status.BeanAddressStatus;
import mx.edu.utez.services_clothing_shop.model.address_status.IAddressStatus;
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
    private final IAddressStatus iAddressStatus;
    public AddressService(IAddress iAddress, IAddressStatus iAddressStatus){
        this.iAddress = iAddress;
        this.iAddressStatus = iAddressStatus;
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
    public List<ResponseGetAddressesByEmailDTO> getAddressesByEmail(String email) {
        List<BeanAddress> addresses = iAddress.findAllByUserEmail(email);
        if (addresses.isEmpty()) {
            throw new CustomException("addresses.notfound");
        }
        return addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ResponseGetAddressesByEmailDTO convertToDTO(BeanAddress address) {
        ResponseGetAddressesByEmailDTO dto = new ResponseGetAddressesByEmailDTO();
        dto.setIdAddress(address.getIdAddress());
        dto.setAddress(address.getAddress());
        dto.setReferencesAddress(address.getReferencesAddress());
        dto.setPostalCode(address.getPostalCode());
        dto.setState(address.getState());
        dto.setStreet(address.getStreet());
        dto.setStatus(address.getStatus().getStatus());
        dto.setNeighborhood(address.getNeighborhood());
        dto.setIdPerson(address.getPerson().getIdPerson());
        return dto;
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

        BeanPerson person = iAddress.findPersonByUserEmail(payload.getEmail())
                .orElseThrow(() -> new CustomException("person.email.notfound"));
        newAddress.setPerson(person);

        BeanAddressStatus status = iAddressStatus.findByStatus("Habilitada")
                .orElseThrow(() -> new CustomException("status.notfound"));
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
        return iAddress.saveAndFlush(existingAddress);
    }

    @Transactional
    public ResponseAllAddressDTO updateAddressStatus(RequestPutStatusAddressDTO payload) {
        BeanAddress addressToUpdate = iAddress.findById(payload.getIdAddress())
                .orElseThrow(() -> new CustomException("address.notfound"));

        if ("Predeterminada".equals(payload.getStatus())) {
            List<BeanAddress> currentDefaultAddresses = iAddress.findByStatusAndPersonId("Predeterminada", payload.getIdPerson());
            for (BeanAddress currentDefaultAddress : currentDefaultAddresses) {
                if (!currentDefaultAddress.getIdAddress().equals(payload.getIdAddress())) {
                    BeanAddressStatus defaultStatus = iAddressStatus.findByStatus("Habilitada")
                            .orElseThrow(() -> new CustomException("status.notfound"));
                    currentDefaultAddress.setStatus(defaultStatus);
                    iAddress.save(currentDefaultAddress);
                }
            }
        }

        BeanAddressStatus newStatus = iAddressStatus.findByStatus(payload.getStatus())
                .orElseThrow(() -> new CustomException("status.notfound"));
        addressToUpdate.setStatus(newStatus);
        BeanAddress updatedAddress = iAddress.save(addressToUpdate);

        return convertToDTOStatus(updatedAddress);
    }

    private ResponseAllAddressDTO convertToDTOStatus(BeanAddress address) {
        ResponseAllAddressDTO dto = new ResponseAllAddressDTO();
        dto.setIdAddress(address.getIdAddress());
        dto.setAddress(address.getAddress());
        dto.setReferencesAddress(address.getReferencesAddress());
        dto.setPostalCode(address.getPostalCode());
        dto.setState(address.getState());
        dto.setStreet(address.getStreet());
        dto.setNeighborhood(address.getNeighborhood());
        dto.setStatus(address.getStatus().getStatus());
        return dto;
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
        return responseDTO;
    }

}
