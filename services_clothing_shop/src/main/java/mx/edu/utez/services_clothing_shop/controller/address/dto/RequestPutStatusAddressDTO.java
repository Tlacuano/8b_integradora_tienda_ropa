package mx.edu.utez.services_clothing_shop.controller.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestPutStatusAddressDTO {
    @NotNull(message = "address.idAddress.notnull")
    private UUID idAddress;

    @NotBlank(message = "address.status.notblank")
    private String status;

    private UUID idPerson;
}
