package com.tui.proof.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Address.
 * This class is used to transfer address data between processes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AddressDto", description = "Data Transfer Object for Address.")
public class AddressDto {
    @Schema(description = "Street.", example = "Calle de la Rosa")
    @NotBlank(message = "Street is required!")
    private String street;

    @Schema(description = "Postcode.", example = "28001")
    @NotBlank(message = "Postcode is required!")
    private String postcode;

    @Schema(description = "City.", example = "Madrid")
    @NotBlank(message = "City is required!")
    private String city;

    @Schema(description = "Country.", example = "Spain")
    @NotBlank(message = "Country is required!")
    private String country;
}