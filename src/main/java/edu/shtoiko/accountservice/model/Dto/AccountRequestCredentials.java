package edu.shtoiko.accountservice.model.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AccountRequestCredentials {

    @NotBlank(message = "Account ID cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Account ID must be a positive number")
    private String accountId;

    @NotBlank(message = "Owner ID cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Owner ID must be a positive number")
    private String ownerId;
}
