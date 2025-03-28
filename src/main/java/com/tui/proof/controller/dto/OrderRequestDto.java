package com.tui.proof.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
@Schema(name = "OrderRequestDto", description = "Data Transfer Object for Order.")
public class OrderRequestDto {
    @Schema(description = "Client information.", example = "ClientDto Object")
    @Valid
    private ClientDto client;

    @Schema(description = "Delivery address.", example = "AddressDto Object")
    @Valid
    private AddressDto deliveryAddress;

    @Schema(description = "Number of pilotes.", example = "5")
    @NotBlank(message = "Number of pilotes is required!")
    @Pattern(regexp = "5|10|15", message = "Number of pilotes must be 5, 10, or 15!")
    private String pilotes;
}
