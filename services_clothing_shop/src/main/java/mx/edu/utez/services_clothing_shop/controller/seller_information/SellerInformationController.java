package mx.edu.utez.services_clothing_shop.controller.seller_information;

import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.service.seller_information.SellerInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venta-ropa/api/sellers-information")
@CrossOrigin(origins = "*")
public class SellerInformationController {
    private final SellerInformationService sellerInformationService;
    public SellerInformationController(SellerInformationService sellerInformationService){
        this.sellerInformationService = sellerInformationService;
    }

    @GetMapping("/get-sellers-information")
    public ResponseEntity<List<BeanSellerInformation>> getSellersInformation(){
        return sellerInformationService.getSellersInformation();
    }

    @PostMapping("/get-seller-information")
    public ResponseEntity<BeanSellerInformation> getSellerInformation(@RequestBody BeanSellerInformation sellerInformation){
        return sellerInformationService.getSellerInformation(sellerInformation);
    }

    @PostMapping("/post-seller-information")
    public ResponseEntity<BeanSellerInformation> postSellerInformation(@RequestBody BeanSellerInformation sellerInformation){
        return sellerInformationService.postSellerInformation(sellerInformation);
    }

    @PutMapping("/put-seller-information")
    public ResponseEntity<BeanSellerInformation> putSellerInformation(@RequestBody BeanSellerInformation sellerInformation){
        return sellerInformationService.putSellerInformation(sellerInformation);
    }

}