package mx.edu.utez.services_clothing_shop.controller.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestPostAddressDTO {
    @NotBlank(message = "address.address.notnull")
    @Size(min = 10, max = 100, message = "address.address.size")
    private String address;

    @Size(min = 5, max = 255, message = "address.referencesAddress.size")
    private String referencesAddress;

    @NotBlank(message = "address.postalCode.notnull")
    @Size(min = 5, max = 5, message = "address.postalCode.size")
    private String postalCode;

    @NotBlank(message = "address.state.notnull")
    @Size(max = 100, message = "address.state.size")
    private String state;

    @NotBlank(message = "address.street.notnull")
    @Size(max = 50, message = "address.street.size")
    private String street;

    @NotBlank(message = "address.neighborhood.notnull")
    @Size(max = 50, message = "address.neighborhood.size")
    private String neighborhood;

    @NotNull(message = "address.personId.notnull")
    private UUID personId;

    @NotNull(message = "address.statusId.notnull")
    private UUID statusId;

}
