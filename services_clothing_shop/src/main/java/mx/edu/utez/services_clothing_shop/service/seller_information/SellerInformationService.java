package mx.edu.utez.services_clothing_shop.service.seller_information;

import mx.edu.utez.services_clothing_shop.controller.seller_information.dto.ResponseAllSellerInformationDTO;
import mx.edu.utez.services_clothing_shop.controller.seller_information.dto.SellerInformationGetbyEmailResponseDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.BeanRequestSellProduct;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.IRequestsSellProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.utils.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.model.seller_information.ISellerInformation;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


@Service
public class SellerInformationService {
    private final ISellerInformation iSellerInformation;
    private final IUser iUser;
    private final IRequestsSellProduct iRequestsSellProduct;
    private final IRequestStatus iRequestStatus;
    private final EmailService emailService;
    private final ErrorDictionary errorDictionary;

    public SellerInformationService(ISellerInformation iSellerInformation, IUser iUser, IRequestsSellProduct iRequestsSellProduct, IRequestStatus iRequestStatus, EmailService emailService, ErrorDictionary errorDictionary) {
        this.iSellerInformation = iSellerInformation;
        this.iUser = iUser;
        this.iRequestsSellProduct = iRequestsSellProduct;
        this.iRequestStatus = iRequestStatus;
        this.emailService = emailService;
        this.errorDictionary = errorDictionary;
    }

    @Transactional(readOnly = true)
    public List<ResponseAllSellerInformationDTO> getSellersInformation() {
        List<Object[]> sellersInformationData = iSellerInformation.findEssentialSellerInformationInfo();
        if (sellersInformationData.isEmpty()) {
            throw new CustomException(errorDictionary.getErrorMessage("sellerInformation.notfound"));
        }
        return sellersInformationData.stream().map(this::mapToResponseAllSellerInformationDTO).toList();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanSellerInformation> getSellerInformation(BeanSellerInformation sellerInformation) {
        try {
            if (iSellerInformation.existsByIdSellerInformation(sellerInformation.getIdSellerInformation())) {
                return ResponseEntity.ok(iSellerInformation.findByIdSellerInformation(sellerInformation.getIdSellerInformation()));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSellerInformation> postSellerInformation(BeanSellerInformation sellerInformation) {
        try {
            return ResponseEntity.status(200).body(iSellerInformation.save(sellerInformation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSellerInformation> putSellerInformation(BeanSellerInformation sellerInformation) {
        try {
            if (iSellerInformation.existsByIdSellerInformation(sellerInformation.getIdSellerInformation())) {
                return ResponseEntity.status(200).body(iSellerInformation.save(sellerInformation));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSellerInformation> deleteSellerInformation(BeanSellerInformation sellerInformation) {
        try {
            if (iSellerInformation.existsByIdSellerInformation(sellerInformation.getIdSellerInformation())) {
                iSellerInformation.deleteById(sellerInformation.getIdSellerInformation());
                return ResponseEntity.status(200).build();
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public ResponseAllSellerInformationDTO mapToResponseAllSellerInformationDTO(Object[] row) {
        ResponseAllSellerInformationDTO responseDTO = new ResponseAllSellerInformationDTO();
        responseDTO.setFullName((String) row[0]);
        responseDTO.setCurp((String) row[1]);
        responseDTO.setPrivacy((boolean) row[2]);
        return responseDTO;
    }

    @Transactional
    public boolean blockSell(RequestActionByEmailDTO payload) {
        BeanUser user = iUser.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(errorDictionary.getErrorMessage("user.notfound"));
        }

        user.getPerson().getSellerInformation().setBlockSell(true);
        iSellerInformation.save(user.getPerson().getSellerInformation());


        List<BeanRequestSellProduct> requests = iRequestsSellProduct.findAllByUser(payload.getEmail());
        BeanRequestStatus status = iRequestStatus.findByStatus("Rechazado").get();

        requests.forEach(request -> {
            request.setStatus(status);
            iRequestsSellProduct.save(request);
        });

        emailService.sendEmail(user.getEmail(), "Sanción en tu cuenta", "Recibiste un bloqueo de venta en tu cuenta", payload.getReason(), "Atentamente, " + payload.getAdmin());

        return true;
    }


    @Transactional
    public boolean unblockSell(RequestActionByEmailDTO payload) {
        BeanUser user = iUser.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(errorDictionary.getErrorMessage("user.notfound"));
        }

        user.getPerson().getSellerInformation().setBlockSell(false);
        iSellerInformation.save(user.getPerson().getSellerInformation());

        emailService.sendEmail(user.getEmail(), "Desbloqueo en tu cuenta", "Tu cuenta ya puede vender nuevamente", "", "Atentamente, " + payload.getAdmin());

        return true;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<SellerInformationGetbyEmailResponseDTO> getSellerInformationByEmail(String email) {
        BeanUser user = iUser.findByEmail(email);
        if (user != null && user.getPerson() != null) {
            BeanSellerInformation sellerInfo = user.getPerson().getSellerInformation();
            if (sellerInfo != null) {
                SellerInformationGetbyEmailResponseDTO responseDTO = new SellerInformationGetbyEmailResponseDTO(
                        sellerInfo.getTaxIdentificationNumber(),
                        sellerInfo.getSecondaryPhoneNumber(),
                        sellerInfo.getImageIdentification(),
                        sellerInfo.getCurp()
                );
                return ResponseEntity.ok(responseDTO);
            }
        }
        throw new CustomException("Seller information not found for email: " + email);
    }

}
