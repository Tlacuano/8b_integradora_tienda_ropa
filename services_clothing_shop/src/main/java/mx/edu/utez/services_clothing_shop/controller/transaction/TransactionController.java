package mx.edu.utez.services_clothing_shop.controller.transaction;

import com.stripe.exception.StripeException;
import mx.edu.utez.services_clothing_shop.controller.transaction.dto.RequestTransactionDTO;
import mx.edu.utez.services_clothing_shop.controller.transaction.dto.ResponseTransactionSessionDTO;
import mx.edu.utez.services_clothing_shop.service.transaction.TransactionService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("venta-ropa/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<CustomResponse<Object>> createCheckoutSession(@RequestBody RequestTransactionDTO requestTransactionDTO) {
        try {
            ResponseTransactionSessionDTO responseTransactionSessionDTO = new ResponseTransactionSessionDTO(transactionService.createCheckoutSession(requestTransactionDTO));
            return ResponseEntity.ok(new CustomResponse<>(responseTransactionSessionDTO, "Sesión de pago creada", false, 200));
        } catch (StripeException e) {
            return ResponseEntity.ok(new CustomResponse<>(null, "Error al crear la sesión de pago: " + e.getMessage(), true, 500));
        }
    }
}
