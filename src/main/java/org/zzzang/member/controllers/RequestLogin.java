package org.zzzang.member.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestLogin {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private boolean success = true;
}