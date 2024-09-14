package com.eb.system.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Data
public class UserLoginReqDto {
    @NotBlank
    @Size(max = 64)
    private String username;
    @NotBlank
    @Size(max = 64)
    private String password;
    @NotNull
    private Integer code;
}
