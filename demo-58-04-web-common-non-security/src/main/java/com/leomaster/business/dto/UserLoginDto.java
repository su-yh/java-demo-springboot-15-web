package com.leomaster.business.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Data
public class UserLoginDto {
    @NotBlank
    @Size(max = 64)
    private String username;
    @NotBlank
    @Size(max = 64)
    private String password;
}