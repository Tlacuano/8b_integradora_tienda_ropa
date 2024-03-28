package mx.edu.utez.services_clothing_shop.controller.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestPutAddressDTO {
    @NotNull(message = "address.idAddress.notnull")
    private UUID idAddress;

    @NotBlank(message = "address.address.notnull", groups = Update.class)
    @Size(min = 10, max = 100, message = "address.address.size", groups = Update.class)
    private String address;

    @Size(min = 5, max = 255, message = "address.referencesAddress.size", groups = Update.class)
    private String referencesAddress;

    @NotBlank(message = "address.postalCode.notnull", groups = Update.class)
    @Size(min = 5, max = 5, message = "address.postalCode.size", groups = Update.class)
    private String postalCode;

    @NotBlank(message = "address.state.notnull", groups = Update.class)
    @Size(max = 100, message = "address.state.size", groups = Update.class)
    private String state;

    @NotBlank(message = "address.street.notnull", groups = Update.class)
    @Size(max = 50, message = "address.street.size", groups = Update.class)
    private String street;

    @NotBlank(message = "address.neighborhood.notnull", groups = Update.class)
    @Size(max = 50, message = "address.neighborhood.size", groups = Update.class)
    private String neighborhood;

}
