package edu.shtoiko.accountservice.model.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountUpdateNameRequest {

    @NotBlank(message = "Account ID cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Account ID must be a positive number")
    private String accountId;

    @NotBlank(message = "Owner ID cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Owner ID must be a positive number")
    private String ownerId;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z ]*$",
        message = "Name must start with an uppercase letter and contain only letters and spaces")
    private String newName;
}
