package mx.edu.utez.services_clothing_shop.controller.address.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseStatusDTO {
    private UUID statusID;
    private String status;
}
