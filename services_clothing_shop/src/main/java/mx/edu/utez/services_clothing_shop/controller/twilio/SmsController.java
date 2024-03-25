package mx.edu.utez.services_clothing_shop.controller.twilio;

import mx.edu.utez.services_clothing_shop.controller.twilio.dto.sendSmsDTO;
import mx.edu.utez.services_clothing_shop.service.twilio.SmsService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(path = "venta-ropa/api/")
public class SmsController {
    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send-sms")
    public ResponseEntity<Object> sendSms(@Validated @RequestBody sendSmsDTO sendSmsDTO) {
        return new ResponseEntity<>(
                new CustomResponse<>(smsService.sendSms(sendSmsDTO), "Mensaje enviado", false, 200),
                HttpStatus.OK
        );
    }
}
