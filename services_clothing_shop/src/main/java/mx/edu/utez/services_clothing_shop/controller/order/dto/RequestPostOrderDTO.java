package mx.edu.utez.services_clothing_shop.controller.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RequestPostOrderDTO {
    @NotNull
    private UUID idUser;
    @NotNull(message = "order.orderDate.notnull")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    @NotNull
    private UUID idAddress;
    @NotNull
    private UUID idPaymentCard;
    private String orderNumber;

    public RequestPostOrderDTO() {
    }

    public RequestPostOrderDTO(UUID idUser, LocalDate orderDate, UUID idAddress, UUID idPaymentCard) {
        this.idUser = idUser;
        this.orderDate = orderDate;
        this.idAddress = idAddress;
        this.idPaymentCard = idPaymentCard;
    }
}
