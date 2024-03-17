package mx.edu.utez.services_clothing_shop.controller.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.RequestActionByIdDTO;
import mx.edu.utez.services_clothing_shop.controller.address.dto.RequestPostAddressDTO;
import mx.edu.utez.services_clothing_shop.controller.address.dto.ResponseAddressDTO;
import mx.edu.utez.services_clothing_shop.controller.address.dto.ResponsePostAddressDTO;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address_status.BeanAddressStatus;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.service.address.AddressService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venta-ropa/api/addresses")
@CrossOrigin(origins = "*")
public class AddressController {
    private final AddressService addressService;
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @GetMapping("/get-addresses")
    public ResponseEntity<CustomResponse<List<ResponseAddressDTO>>> getAddresses(){
        return new ResponseEntity<>(new CustomResponse<>(addressService.getAddresses(),"All addresses retrieved successfully", false, 200), HttpStatus.OK);
    }

    @PostMapping("/get-address")
    public ResponseEntity<CustomResponse<ResponseAddressDTO>> getAddress(@RequestBody BeanAddress address){
        ResponseEntity<?> responseEntity = addressService.getAddress(address);
        CustomResponse<ResponseAddressDTO> customResponse;
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ResponseAddressDTO responseAddressDTO = (ResponseAddressDTO) responseEntity.getBody();
            customResponse = new CustomResponse<>(responseAddressDTO, "Success", false, HttpStatus.OK.value());
            return ResponseEntity.ok(customResponse);
        } else {
            customResponse = new CustomResponse<>(null, "Error getting address", true, responseEntity.getStatusCode().value());
            return ResponseEntity.status(responseEntity.getStatusCode()).body(customResponse);
        }
    }

    @PostMapping("/post-address")
    public ResponseEntity<Object> postAddress(@Validated @RequestBody RequestPostAddressDTO payload){
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

        BeanAddress savedAddress = addressService.postAddress(newAddress);
        ResponsePostAddressDTO responseDTO = mapToResponseDTO(savedAddress);

        return new ResponseEntity<>(new CustomResponse<>(responseDTO, "Address created successfully", false, 200), HttpStatus.OK);
    }

    @PutMapping("/put-address")
    public ResponseEntity<Object> putAddress(@Validated @RequestBody BeanAddress payload){
        return new ResponseEntity<>(new CustomResponse<>(addressService.putAddress(payload), "Address updated successfully", false, 200), HttpStatus.OK);
    }

    private ResponsePostAddressDTO mapToResponseDTO(BeanAddress savedAddress) {
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

}
