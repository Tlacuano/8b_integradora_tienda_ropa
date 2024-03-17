package mx.edu.utez.services_clothing_shop.controller.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.*;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.address_status.BeanAddressStatus;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.service.address.AddressService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
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
        try {
            BeanAddress newAddress = addressService.postAddress(payload);
            ResponsePostAddressDTO responseDTO = addressService.mapToResponseDTO(newAddress);
            return ResponseEntity.ok(new CustomResponse<>(responseDTO, "Address created successfully", false, HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(null, "Error creating address: " + e.getMessage(), true, HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/put-address")
    public ResponseEntity<Object> putAddress(@Validated @RequestBody RequestPutAddressDTO payload){
        try {
            BeanAddress updatedAddress = addressService.putAddress(payload);
            return ResponseEntity.ok(new CustomResponse<>(updatedAddress, "Address updated successfully", false, HttpStatus.OK.value()));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(null, e.getMessage(), true, HttpStatus.BAD_REQUEST.value()));
        }
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
