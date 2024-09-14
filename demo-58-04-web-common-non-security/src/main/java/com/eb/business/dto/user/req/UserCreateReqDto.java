package com.eb.business.dto.user.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Data
@Deprecated
public class UserCreateReqDto {
    @NotBlank
    @Size(max = 64)
    private String username;
    @NotBlank
    @Size(max = 64)
    private String password;
    @NotBlank
    @Size(max = 64)
    private String nickname;
}
