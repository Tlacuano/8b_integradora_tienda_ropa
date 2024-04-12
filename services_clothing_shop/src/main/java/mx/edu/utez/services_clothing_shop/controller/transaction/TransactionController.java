package mx.edu.utez.services_clothing_shop.controller.transaction;

import com.stripe.exception.StripeException;
import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponseShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.controller.transaction.dto.RequestTransactionDTO;
import mx.edu.utez.services_clothing_shop.controller.transaction.dto.ResponseTransactionSessionDTO;
import mx.edu.utez.services_clothing_shop.service.shopping_cart.ShoppingCartServices;
import mx.edu.utez.services_clothing_shop.service.transaction.TransactionService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venta-ropa/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    private final TransactionService transactionService;
    private final ShoppingCartServices shoppingCartServices;

    public TransactionController(TransactionService transactionService, ShoppingCartServices shoppingCartServices) {
        this.transactionService = transactionService;
        this.shoppingCartServices = shoppingCartServices;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<CustomResponse<Object>> createCheckoutSession(@RequestBody RequestTransactionDTO requestTransactionDTO) throws StripeException {
        List<ResponseShoppingCartDTO> shoppingCart = shoppingCartServices.findShoppingCartsByUserEmail(requestTransactionDTO.getEmail());
        ResponseTransactionSessionDTO responseTransactionSessionDTO = new ResponseTransactionSessionDTO(transactionService.createCheckoutSession(shoppingCart, requestTransactionDTO.getEmail(), requestTransactionDTO.getIdAddress(), requestTransactionDTO.getIdPaymentCard()));
        return ResponseEntity.ok(new CustomResponse<>(responseTransactionSessionDTO, "Sesión de pago creada", false, 200));
    }

    @PostMapping("/webhook")
    public ResponseEntity<CustomResponse<Object>> fulfillOrder(@RequestHeader("Stripe-Signature") String stripeSignature, @RequestBody String payload) throws StripeException {
        transactionService.fulfillOrder(stripeSignature, payload);
        return ResponseEntity.ok(new CustomResponse<>(null, "Webhook received", false, 200));
    }
}
