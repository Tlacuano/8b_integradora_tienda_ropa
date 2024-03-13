package mx.edu.utez.services_clothing_shop.controller.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.ResponseAddressDTO;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.service.address.AddressService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<ResponseAddressDTO> responseEntity = addressService.getAddress(address);
        CustomResponse<ResponseAddressDTO> customResponse;
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            customResponse = new CustomResponse<>(responseEntity.getBody(), "Success", false, HttpStatus.OK.value());
        } else {
            customResponse = new CustomResponse<>(null, "Error getting address", true, responseEntity.getStatusCode().value());
        }
        return ResponseEntity.status(responseEntity.getStatusCode()).body(customResponse);
    }

    @PostMapping("/post")
    public ResponseEntity<Object> get(@RequestBody ResponseAddressDTO address){
        return new ResponseEntity<>(new CustomResponse<>(true, "ok", false, 201), HttpStatus.OK);
    }

    @PostMapping("/post-address")
    public ResponseEntity<BeanAddress> postAddress(@RequestBody BeanAddress address){
        return addressService.postAddress(address);
    }

    @PutMapping("/put-address")
    public ResponseEntity<BeanAddress> putAddress(@RequestBody BeanAddress address){
        return addressService.putAddress(address);
    }
}
