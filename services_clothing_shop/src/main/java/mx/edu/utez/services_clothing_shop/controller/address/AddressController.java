package mx.edu.utez.services_clothing_shop.controller.address;

import mx.edu.utez.services_clothing_shop.controller.address.dto.ResponseAddressDTO;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.service.address.AddressService;
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
    public ResponseEntity<List<ResponseAddressDTO>> getAddressese(){
        ResponseEntity<List<ResponseAddressDTO>> responseEntity = addressService.getAddresses();
        List<ResponseAddressDTO> responseAddresses = responseEntity.getBody();
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseAddresses);
    }

    @PostMapping("/get-address")
    public ResponseEntity<BeanAddress> getAddress(@RequestBody BeanAddress address){
        return addressService.getAddress(address);
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
