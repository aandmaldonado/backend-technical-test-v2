package com.tui.proof.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Order.
 * This class is used to transfer order data between processes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OrderResponseDto", description = "Data Transfer Object for Order.")
public class OrderResponseDto {
    @Schema(description = "Order number (server side generated).", example = "123456")
    private Long number;

    @Schema(description = "Client information.", example = "ClientDto Object")
    private ClientDto client;

    @Schema(description = "Delivery address.", example = "AddressDto Object")
    private AddressDto deliveryAddress;

    @Schema(description = "Number of pilotes.", example = "5")
    private Integer pilotes;

    @Schema(description = "Order total (server side generated, consider 1.33€ as price for a single pilotes).",
            example = "6.65")
    private Double orderTotal;

}
