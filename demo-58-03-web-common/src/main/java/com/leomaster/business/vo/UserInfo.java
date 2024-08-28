package com.leomaster.business.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author suyh
 * @since 2024-08-28
 */
@Data
public class UserInfo {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
