package mx.edu.utez.services_clothing_shop.service.seller_information;

import mx.edu.utez.services_clothing_shop.controller.seller_information.dto.ResponseAllSellerInformationDTO;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.model.seller_information.ISellerInformation;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerInformationService {
    private final ISellerInformation iSellerInformation;
    private ErrorDictionary errorDictionary;
    public SellerInformationService(ISellerInformation iSellerInformation, ErrorDictionary errorDictionary) {
        this.iSellerInformation = iSellerInformation;
        this.errorDictionary = errorDictionary;
    }

    @Transactional(readOnly = true)
    public List<ResponseAllSellerInformationDTO> getSellersInformation(){
        List<Object[]> sellersInformationData = iSellerInformation.findEssentialSellerInformationInfo();
        if(sellersInformationData.isEmpty()){
            throw new CustomException(errorDictionary.getErrorMessage("sellerInformation.notfound"));
        }
        return sellersInformationData.stream()
                .map(this::mapToResponseAllSellerInformationDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanSellerInformation> getSellerInformation(BeanSellerInformation sellerInformation){
        try {
            if(iSellerInformation.existsByIdSellerInformation(sellerInformation.getIdSellerInformation())){
                return ResponseEntity.ok(iSellerInformation.findByIdSellerInformation(sellerInformation.getIdSellerInformation()));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSellerInformation> postSellerInformation(BeanSellerInformation sellerInformation){
        try {
            return ResponseEntity.status(200).body(iSellerInformation.save(sellerInformation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSellerInformation> putSellerInformation(BeanSellerInformation sellerInformation){
        try {
            if(iSellerInformation.existsByIdSellerInformation(sellerInformation.getIdSellerInformation())){
                return ResponseEntity.status(200).body(iSellerInformation.save(sellerInformation));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseAllSellerInformationDTO mapToResponseAllSellerInformationDTO(Object[] row){
        ResponseAllSellerInformationDTO responseDTO = new ResponseAllSellerInformationDTO();
        responseDTO.setFullName((String) row[0]);
        responseDTO.setCurp((String) row[1]);
        responseDTO.setPrivacy((boolean) row[2]);
        return responseDTO;
    }

}
