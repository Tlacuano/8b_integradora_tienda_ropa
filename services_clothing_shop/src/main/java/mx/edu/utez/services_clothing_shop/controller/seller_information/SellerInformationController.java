package mx.edu.utez.services_clothing_shop.controller.seller_information;

import mx.edu.utez.services_clothing_shop.controller.seller_information.dto.ResponseAllSellerInformationDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.service.seller_information.SellerInformationService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> getSellersInformation(){
        try {
            List<ResponseAllSellerInformationDTO> responseDTOs = sellerInformationService.getSellersInformation();
            return ResponseEntity.ok(new CustomResponse<>(responseDTOs, "Sellers information retrieved successfully", false, HttpStatus.OK.value()));
        } catch (CustomException e) {
         return ResponseEntity.badRequest().body(new CustomResponse<>(null, e.getMessage(), true, HttpStatus.BAD_REQUEST.value()));
        }
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

    @DeleteMapping("delete-seller-information")
    public ResponseEntity<BeanSellerInformation> deleteSellerInformation(@RequestBody BeanSellerInformation sellerInformation){
        return sellerInformationService.deleteSellerInformation(sellerInformation);
    }


    @PostMapping("/block-sell")
    public ResponseEntity<Object> blockSell(@RequestBody RequestActionByEmailDTO requestActionByEmailDTO){
        return new ResponseEntity<>(
                new CustomResponse<>(sellerInformationService.blockSell(requestActionByEmailDTO), "Block sell successfully", false, HttpStatus.OK.value()),
                HttpStatus.OK
        );
    }

    @PostMapping("/unblock-sell")
    public ResponseEntity<Object> unblockSell(@RequestBody RequestActionByEmailDTO requestActionByEmailDTO){
        return new ResponseEntity<>(
                new CustomResponse<>(sellerInformationService.unblockSell(requestActionByEmailDTO), "Unblock sell successfully", false, HttpStatus.OK.value()),
                HttpStatus.OK
        );
    }

}
