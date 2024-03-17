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
    @NotBlank(message = "La dirección es requerida")
    @Size(max = 100, message = "La longitud de la dirección debe ser menor o igual a 100 caracteres")
    private String address;

    @Size(max = 255, message = "La longitud de las referencias de la dirección debe ser menor o igual a 255 caracteres")
    private String referencesAddress;

    @NotBlank(message = "El código postal es requerido")
    @Size(max = 5, message = "La longitud del código postal debe ser de 5 caracteres")
    private String postalCode;

    @NotBlank(message = "El estado es requerido")
    @Size(max = 100, message = "La longitud del estado debe ser menor o igual a 100 caracteres")
    private String state;

    @NotBlank(message = "La calle es requerida")
    @Size(max = 50, message = "La longitud de la calle debe ser menor o igual a 50 caracteres")
    private String street;

    @NotBlank(message = "El vecindario es requerido")
    @Size(max = 50, message = "La longitud del vecindario debe ser menor o igual a 50 caracteres")
    private String neighborhood;

    @NotNull(message = "El ID de la persona es requerido")
    private UUID personId;

    @NotNull(message = "El ID del estado es requerido")
    private UUID statusId;

}
