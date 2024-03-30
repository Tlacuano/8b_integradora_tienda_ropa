package mx.edu.utez.services_clothing_shop.controller.captcha;

import mx.edu.utez.services_clothing_shop.controller.captcha.dto.CaptchaResponse;
import mx.edu.utez.services_clothing_shop.controller.captcha.dto.RequestSolutionDTO;
import mx.edu.utez.services_clothing_shop.service.captcha.CaptchaService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/venta-ropa/api/captcha")
public class CaptchaController {
    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @PostMapping("/validate")
    public CaptchaResponse validateCaptcha(@RequestBody RequestSolutionDTO solution) {
        return captchaService.validateCaptcha(solution.getSolution());
    }
}
