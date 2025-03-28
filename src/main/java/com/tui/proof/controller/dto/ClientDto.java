package com.tui.proof.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Client.
 * This class is used to transfer client data between processes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ClientDto", description = "Data Transfer Object for Client.")
public class ClientDto {
    @Schema(description = "First name.", example = "John")
    @NotBlank(message = "First name is required!")
    private String firstName;

    @Schema(description = "Last name.", example = "Doe")
    @NotBlank(message = "Last name is required!")
    private String lastName;

    @Schema(description = "Telephone.", example = "+34 123 456 789")
    @NotBlank(message = "Telephone is required!")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid telephone format!")
    private String telephone;
}
